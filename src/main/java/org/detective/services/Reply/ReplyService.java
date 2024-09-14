package org.detective.services.Reply;

import org.detective.RequestStatus;
import org.detective.dto.EstimateDTO;
import org.detective.dto.EstimateDetailDTO;
import org.detective.dto.ReplyDTO;
import org.detective.entity.*;
import org.detective.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ReplyService {

    private final ClientRepository clientRepository;
    private final RequestRepository requestRepository;
    private final DetectiveRepository detectiveRepository;
    private final EstimateRepository estimateRepository;
    private final UserRepository userRepository;
    private final AssignmentRequestRepository assignmentRequestRepository;

    public ReplyService(ClientRepository clientRepository, RequestRepository requestRepository, DetectiveRepository detectiveRepository, EstimateRepository estimateRepository, UserRepository userRepository, AssignmentRequestRepository assignmentRequestRepository) {
        this.clientRepository = clientRepository;
        this.requestRepository = requestRepository;
        this.detectiveRepository = detectiveRepository;
        this.estimateRepository = estimateRepository;
        this.userRepository = userRepository;
        this.assignmentRequestRepository = assignmentRequestRepository;
    }
    public void createEstimate(ReplyDTO replyDTO) {
        Request request = requestRepository.findByRequestId(replyDTO.getRequestId());
        Client client = clientRepository.findByUser(request.getClient().getUser());
        Detective detective = detectiveRepository.findByUser(userRepository.findByEmail(replyDTO.getEmail()));

        System.err.println("Reply Service실행");
        System.out.println(client +"\n"+ request +"\n"+  detective);
        estimateRepository.save(new Estimate(client, request, detective, replyDTO.getTitle(), replyDTO.getDescription(), replyDTO.getPrice()));
        AssignmentRequest assignmentRequest = assignmentRequestRepository.findByRequestAndDetective(request,detective);
        System.err.println("@@@@@@@@@@@@@@@@@\nassignmentRequest : "+assignmentRequest+"\n@@@@@@@@@@@@@@@@@");
        assignmentRequest.setRequestStatus(RequestStatus.ANSWERED);
        assignmentRequestRepository.save(assignmentRequest);
        System.out.println(assignmentRequest);
    }

    public List<EstimateDetailDTO> getEstimateDetail(Long requestId) {
        List<Estimate> estimates = estimateRepository.findByRequest(requestRepository.findByRequestId(requestId));
        List<EstimateDetailDTO> estimateDetailDTOS = new ArrayList<>();
        for (Estimate estimate : estimates) {
            estimateDetailDTOS.add(new EstimateDetailDTO(estimate.getEstimateId(), estimate.getDetective().getDetectiveId(),estimate.getDetective().getUser().getUserName(),estimate.getDescription(), estimate.getPrice()));
        }
        return estimateDetailDTOS;
    }

    public List<EstimateDTO> getEstimateList(String email) {
        Detective detective = detectiveRepository.findByUser(userRepository.findByEmail(email));
        List<Estimate> estimates = estimateRepository.findByDetective(detective);
        List<EstimateDTO> estimateDTOS = new ArrayList<>();
        for (Estimate estimate : estimates) {
            estimateDTOS.add(new EstimateDTO(estimate.getEstimateId(),estimate.getRequest().getRequestId(),estimate.getTitle(),estimate.getCreateAt(),estimate.getRequest().getSpeciality().getSpecialityName()));
        }
        estimateDTOS.sort(Comparator.comparing(EstimateDTO -> EstimateDTO.getRequestId()));
        return estimateDTOS;
    }
}
