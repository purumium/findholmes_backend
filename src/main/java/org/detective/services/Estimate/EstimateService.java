package org.detective.services.Estimate;

import org.detective.RequestStatus;
import org.detective.dto.EstimateDetailDTO;
import org.detective.dto.EstimateFormDTO;
import org.detective.dto.EstimateListDTO;
import org.detective.entity.*;
import org.detective.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstimateService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final EstimateRepository estimateRepository;
    private final AssignmentRequestRepository assignmentRequestRepository;
    private final RequestRepository requestRepository;
    private final DetectiveRepository detectiveRepository;

    public EstimateService(UserRepository userRepository, DetectiveRepository detectiveRepository, ClientRepository clientRepository, EstimateRepository estimateRepository, AssignmentRequestRepository assignmentRequestRepository, RequestRepository requestRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.estimateRepository = estimateRepository;
        this.assignmentRequestRepository = assignmentRequestRepository;
        this.requestRepository = requestRepository;
        this.detectiveRepository = detectiveRepository;
    }

    public void createEstimate(EstimateFormDTO estimateFormDTO) {
        Request request = requestRepository.findByRequestId(estimateFormDTO.getRequestId());
        Client client = clientRepository.findByUser(request.getClient().getUser());
        Detective detective = detectiveRepository.findByUser(userRepository.findByEmail(estimateFormDTO.getEmail()));

        System.err.println("Reply Service실행");
        System.out.println(client +"\n"+ request +"\n"+  detective);
        estimateRepository.save(new Estimate(client, request, detective, estimateFormDTO.getTitle(), estimateFormDTO.getDescription(), estimateFormDTO.getPrice()));
        AssignmentRequest assignmentRequest = assignmentRequestRepository.findByRequestAndDetective(request,detective);
        System.err.println("@@@@@@@@@@@@@@@@@\nassignmentRequest : "+assignmentRequest+"\n@@@@@@@@@@@@@@@@@");
        assignmentRequest.setRequestStatus(RequestStatus.ANSWERED);
        assignmentRequestRepository.save(assignmentRequest);
        System.out.println(assignmentRequest);
    }

    public List<EstimateListDTO> getEstimateList(Long userId) {
        Detective detective = detectiveRepository.findByUser_UserId(userId);
        List<Estimate> estimateLists = estimateRepository.findByDetective(detective);
        List<EstimateListDTO> estimateListDTOS = new ArrayList<>();

        for (Estimate estimate : estimateLists) {
            estimateListDTOS.add(new EstimateListDTO(
                    estimate.getRequest().getRequestId(),
                    estimate.getEstimateId(),
                    estimate.getRequest().getTitle(),
                    estimate.getTitle(),
                    estimate.getRequest().getLocation(),
                    estimate.getRequest().getSpeciality().getSpecialityName(),
                    estimate.getRequest().getCreatedAt(),
                    estimate.getCreateAt(),
                    estimate.getDetective().getUser().getUserName()
            ));
        }
        return estimateListDTOS;
    }

    public List<EstimateDetailDTO> getEstimateDetail(Long requestId, Long userId) {

        List<Estimate> estimates = estimateRepository.findByRequest(requestRepository.findByRequestId(requestId));

        if (userId != null) {
            estimates = estimates.stream()
                    .filter(estimate -> estimate.getDetective().getUser().getUserId().equals(userId))
                    .collect(Collectors.toList());
        }
        List<EstimateDetailDTO> estimateDetailDTOS = new ArrayList<>();
        for (Estimate estimate : estimates) {
            estimateDetailDTOS.add(new EstimateDetailDTO(estimate.getEstimateId(), estimate.getDetective().getDetectiveId(),estimate.getDetective().getUser().getUserName(),
                    estimate.getDescription(), estimate.getPrice(), estimate.getTitle(), estimate.getCreateAt()));
        }
        return estimateDetailDTOS;
    }
}
