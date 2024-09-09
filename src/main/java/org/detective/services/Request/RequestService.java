package org.detective.services.Request;

import org.detective.dto.RequestDTO;
import org.detective.entity.Client;
import org.detective.entity.Request;
import org.detective.entity.Speciality;
import org.detective.entity.User;
import org.detective.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final SpecialityRepository specialityRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final DetectiveRepository detectiveRepository;
//    private final AssignmentRequestRepository assignmentRequestRepository;

    public RequestService(RequestRepository requestRepository, SpecialityRepository specialityRepository, ClientRepository clientRepository, UserRepository userRepository,
                          DetectiveRepository detectiveRepository/*,AssignmentRequestRepository assignmentRequestRepository*/) {
        this.requestRepository = requestRepository;
        this.specialityRepository = specialityRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.detectiveRepository = detectiveRepository;
//        this.assignmentRequestRepository = assignmentRequestRepository;
    }


    public void createRequest(RequestDTO requestDTO) {
        System.err.println("Service : createRequest 실행");
        User user = userRepository.findByEmail(requestDTO.getEmail());
        Long userId = user.getUserId();
        if (userId == null) { throw new RuntimeException("User not found"); }

        Client client = clientRepository.findByUser(user);
        if (client == null) { throw new RuntimeException("Client not found"); }

        Optional<Speciality> optionalSpecialty = specialityRepository.findById(requestDTO.getSpeciality());

        Speciality speciality = optionalSpecialty.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Specialty not found with ID")
        );


        Request request = new Request(client, requestDTO.getLocation(), requestDTO.getGender(), speciality, requestDTO.getDescription());

        System.out.println("Service request entity : "+ request.toString());
        Request requestId = requestRepository.save(request);
        System.out.println(requestId.getRequestId());

    }

}
