package org.detective.services.Estimate;

import org.detective.dto.AssignedRequestDTO;
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
public class EstimateService {

    private final AssignmentRequestRepository assignmentRequestRepository;
    private final DetectiveRepository detectiveRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;

    public EstimateService(AssignmentRequestRepository assignmentRequestRepository, DetectiveRepository detectiveRepository, UserRepository userRepository, RequestRepository requestRepository) {
        this.assignmentRequestRepository = assignmentRequestRepository;
        this.detectiveRepository = detectiveRepository;
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
    }
    public List<AssignedRequestDTO> getAssignedRequest(String email) {
        System.err.println("Estimate Service");
        User user = userRepository.findByEmail(email);
        Detective detective = detectiveRepository.findByUser(user);
        List<AssignmentRequest> assignmentRequest = assignmentRequestRepository.findByDetective(detective);
        List<AssignedRequestDTO> requestsList = new ArrayList<>();

        for (AssignmentRequest assigned : assignmentRequest) {
            requestsList.add(new AssignedRequestDTO(assigned.getAssignmentRequestId(),
                                                    assigned.getDetective().getDetectiveId(),
                                                    assigned.getRequest().getClient().getClientId(),
                                                    assigned.getRequest().getClient().getUser().getUserId(),
                                                    assigned.getRequest().getRequestId(),
                                                    assigned.getRequest().getClient().getUser().getUserName(),
                                                    assigned.getRequest().getDescription(),
                                                    assigned.getRequest().getLocation(),
                                                    assigned.getRequest().getCreatedAt(),
                                                    assigned.getSpeciality()));
        }
        return requestsList;
    }
}
