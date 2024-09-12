package org.detective.services.Reply;

import org.detective.dto.EstimateDTO;
import org.detective.dto.EstimateDetailDTO;
import org.detective.dto.ReplyDTO;
import org.detective.entity.Client;
import org.detective.entity.Detective;
import org.detective.entity.Estimate;
import org.detective.entity.Request;
import org.detective.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReplyService {

    private final ClientRepository clientRepository;
    private final RequestRepository requestRepository;
    private final DetectiveRepository detectiveRepository;
    private final EstimateRepository estimateRepository;
    private final UserRepository userRepository;

    public ReplyService(ClientRepository clientRepository, RequestRepository requestRepository, DetectiveRepository detectiveRepository, EstimateRepository estimateRepository, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.requestRepository = requestRepository;
        this.detectiveRepository = detectiveRepository;
        this.estimateRepository = estimateRepository;
        this.userRepository = userRepository;
    }
    public void createEstimate(ReplyDTO replyDTO) {
        Request request = requestRepository.findByRequestId(replyDTO.getRequestId());
        Client client = clientRepository.findByUser(request.getClient().getUser());
        Detective detective = detectiveRepository.findByUser(userRepository.findByEmail(replyDTO.getEmail()));

        System.err.println("Reply Service실행");
        System.out.println(client +"\n"+ request +"\n"+  detective);
        estimateRepository.save(new Estimate(client, request, detective,replyDTO.getDescription(),replyDTO.getPrice()));
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
            estimateDTOS.add(new EstimateDTO(estimate.getEstimateId(),estimate.getRequest().getRequestId(),estimate.getRequest().getTitle(),estimate.getCreateAt(),estimate.getRequest().getSpeciality().getSpecialityName()));
        }
        return estimateDTOS;
    }
}
