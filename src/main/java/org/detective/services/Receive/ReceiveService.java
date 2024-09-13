package org.detective.services.Receive;

import org.detective.dto.AssignedRequestDTO;
import org.detective.dto.ReceiveRequestDTO;
import org.detective.entity.AssignmentRequest;
import org.detective.entity.Detective;
import org.detective.entity.Request;
import org.detective.entity.User;
import org.detective.repository.AssignmentRequestRepository;
import org.detective.repository.DetectiveRepository;
import org.detective.repository.RequestRepository;
import org.detective.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReceiveService {

    private final AssignmentRequestRepository assignmentRequestRepository;
    private final DetectiveRepository detectiveRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;

    public ReceiveService(AssignmentRequestRepository assignmentRequestRepository, DetectiveRepository detectiveRepository, UserRepository userRepository, RequestRepository requestRepository) {
        this.assignmentRequestRepository = assignmentRequestRepository;
        this.detectiveRepository = detectiveRepository;
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
    }
    public List<ReceiveRequestDTO> getAssignedRequest(String email) {
        System.err.println("Receive Service");
        User user = userRepository.findByEmail(email);
        Detective detective = detectiveRepository.findByUser(user);
        List<AssignmentRequest> assignmentRequest = assignmentRequestRepository.findByDetective(detective);
        List<ReceiveRequestDTO> requestsList = new ArrayList<>();

        for (AssignmentRequest assigned : assignmentRequest) {
            requestsList.add(new ReceiveRequestDTO(assigned.getAssignmentRequestId(),
                                                    assigned.getRequest().getRequestId(),
                                                    assigned.getRequest().getTitle(),
                                                    assigned.getRequest().getLocation(),
                                                    assigned.getRequest().getCreatedAt(),
                                                    assigned.getSpeciality()));
        }
        return requestsList;
    }

    public AssignedRequestDTO getRequestDetail(Long requestId) {
        Request request = requestRepository.findByRequestId(requestId);

        return new AssignedRequestDTO(  request.getRequestId(),
                                        request.getClient().getUser().getUserName(),
                                        request.getTitle(),
                                        request.getDescription(),
                                        request.getLocation(),
                                        request.getCreatedAt(),
                                        request.getSpeciality().getSpecialityName());

    }
}
