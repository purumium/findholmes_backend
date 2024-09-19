package org.detective.services.Request;

import org.detective.dto.*;
import org.detective.entity.*;
import org.detective.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final SpecialityRepository specialityRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final DetectiveRepository detectiveRepository;
    private final AssignmentRequestRepository assignmentRequestRepository;

    public RequestService(RequestRepository requestRepository, SpecialityRepository specialityRepository, ClientRepository clientRepository, UserRepository userRepository,
                          DetectiveRepository detectiveRepository,AssignmentRequestRepository assignmentRequestRepository) {
        this.requestRepository = requestRepository;
        this.specialityRepository = specialityRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.detectiveRepository = detectiveRepository;
        this.assignmentRequestRepository = assignmentRequestRepository;
    }

    /*
    고객이 보낸 의뢰를 생성하는 메서드
    */
    public void createRequest(RequestFormDTO requestFormDTO) {
        System.err.println("Service : createRequest 실행");
        User user = userRepository.findByEmail(requestFormDTO.getEmail());
        Long userId = user.getUserId();
        if (userId == null) { throw new RuntimeException("User not found"); }

        Client client = clientRepository.findByUser(user);
        if (client == null) { throw new RuntimeException("Client not found"); }

        Optional<Speciality> optionalSpecialty = specialityRepository.findById(requestFormDTO.getSpeciality());

        Speciality speciality = optionalSpecialty.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Specialty not found with ID")
        );


        Request request = new Request(client, requestFormDTO.getLocation(), requestFormDTO.getGender(), speciality, requestFormDTO.getTitle(), requestFormDTO.getDescription());

        System.out.println("Service request entity : "+ request.toString());
        requestRepository.save(request);

        List<Detective> detectives = detectiveRepository.getDetectiveRandom();
        
        int cnt = 0;
        System.err.println("Service detectives entity : "+ detectives.toString());
        System.err.println("Service detectives entity length : "+ detectives.size());
        for (Detective detective : detectives) {
            System.err.println(++cnt + "번째 삽입");
            AssignmentRequest assignmentRequest = new AssignmentRequest(request, detective, speciality);
            assignmentRequestRepository.save(assignmentRequest);
        }

    }

    public List<RequestListDTO> getAllRequests(Long userId) {
        //User user = userRepository.findByEmail(email);
        Client client = clientRepository.findByUser_userId(userId);

        List<Request> requests = requestRepository.findByClient(client);

        List<RequestListDTO> requestList = new ArrayList<>();
        for (Request request : requests) {
            requestList.add(new RequestListDTO(
                    request.getRequestId(),
                    request.getTitle(),
                    request.getCreatedAt(),
                    request.getSpeciality().getSpecialityName(),
                    assignmentRequestRepository.countStatusByRequest(request) > 0? true: false));
        }
        System.err.println("Request list by Client: " + requestList);
        return requestList;
    }

    public RequestDetailDTO getRequestDetail(Long requestId) {
        Request request = requestRepository.findByRequestId(requestId);

        return new RequestDetailDTO(
                request.getRequestId(),
                request.getSpeciality().getSpecialityName(),
                request.getLocation(),
                request.getTitle(),
                request.getDescription(),
                request.getDetectiveGender(),
                request.getCreatedAt(),
                assignmentRequestRepository.countStatusByRequest(request) > 0? true: false);

    }

    public List<RequestReceiveDTO> getRequestsReceive(Long userId) {
        Detective detective = detectiveRepository.findByUser_UserId(userId);
        List<AssignmentRequest> assignmentRequests = assignmentRequestRepository.findByDetective(detective);
        List<RequestReceiveDTO> requestReceiveDTOList = new ArrayList<>();
        for (AssignmentRequest assignmentRequest : assignmentRequests) {
            requestReceiveDTOList.add(new RequestReceiveDTO(
                    assignmentRequest.getAssignmentRequestId(),
                    assignmentRequest.getRequest().getRequestId(),
                    assignmentRequest.getRequest().getTitle(),
                    assignmentRequest.getRequest().getLocation(),
                    assignmentRequest.getRequest().getCreatedAt(),
                    assignmentRequest.getSpeciality(),
                    assignmentRequest.getRequestStatus().name()));
        }
        requestReceiveDTOList.sort(Comparator.comparing(RequestReceiveDTO -> RequestReceiveDTO.getRequestId()));

        return requestReceiveDTOList;

    }

}
