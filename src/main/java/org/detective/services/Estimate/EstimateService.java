package org.detective.services.Estimate;

import org.detective.RequestStatus;
import org.detective.dto.EstimateDetailDTO;
import org.detective.dto.EstimateFormDTO;
import org.detective.dto.EstimateListDTO;
import org.detective.dto.NotificationDTO;
import org.detective.entity.*;
import org.detective.repository.*;
import org.detective.services.Notify.NotificationService;
import org.detective.services.detective.DetectiveService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstimateService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final EstimateRepository estimateRepository;
    private final AssignmentRequestRepository assignmentRequestRepository;
    private final RequestRepository requestRepository;
    private final DetectiveRepository detectiveRepository;
    private final DetectiveService detectiveService;
    private final NotificationService notificationService;

    public EstimateService(UserRepository userRepository, DetectiveRepository detectiveRepository, ClientRepository clientRepository, EstimateRepository estimateRepository, AssignmentRequestRepository assignmentRequestRepository, RequestRepository requestRepository, DetectiveService detectiveService, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.estimateRepository = estimateRepository;
        this.assignmentRequestRepository = assignmentRequestRepository;
        this.requestRepository = requestRepository;
        this.detectiveRepository = detectiveRepository;
        this.detectiveService = detectiveService;
        this.notificationService = notificationService;
    }


    public void createEstimate(EstimateFormDTO estimateFormDTO) {
        Request request = requestRepository.findByRequestId(estimateFormDTO.getRequestId());
        Client client = clientRepository.findByUser(request.getClient().getUser());
        Detective detective = detectiveRepository.findByUser(userRepository.findByEmail(estimateFormDTO.getEmail()));

        estimateRepository.save(new Estimate(client, request, detective, estimateFormDTO.getTitle(), estimateFormDTO.getDescription(), estimateFormDTO.getPrice()));

        AssignmentRequest assignmentRequest = assignmentRequestRepository.findByRequestAndDetective(request, detective);
        assignmentRequest.setRequestStatus(RequestStatus.ANSWERED);
        assignmentRequestRepository.save(assignmentRequest);

        Long newPoint = detective.getCurrentPoints() - 10L;
        detective.setCurrentPoints(newPoint);
        detectiveRepository.save(detective);
        System.err.println(client.getUser().getUserId());
        NotificationDTO notificationDTO = new NotificationDTO(detective.getDetectiveId(), client.getClientId(), request.getTitle(), detective.getUser().getUserName(),"/estimatelist/"+request.getRequestId());
        notificationService.notifyEstimate(notificationDTO);
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

    public EstimateListDTO getEstimateDetail(Long userId, Long requestId) {

        Detective detective = detectiveRepository.findByUser_UserId(userId);
        Estimate estimateDetail = estimateRepository.findByDetectiveAndRequest_requestId(detective, requestId);

        EstimateListDTO estimateListDTOS = new EstimateListDTO(estimateDetail.getRequest().getRequestId(),
                estimateDetail.getEstimateId(),
                estimateDetail.getRequest().getTitle(),
                estimateDetail.getTitle(),
                estimateDetail.getRequest().getLocation(),
                estimateDetail.getRequest().getSpeciality().getSpecialityName(),
                estimateDetail.getDescription(),
                estimateDetail.getPrice(),
                estimateDetail.getRequest().getCreatedAt(),
                estimateDetail.getCreateAt(),
                estimateDetail.getDetective().getUser().getUserName());

        return estimateListDTOS;
    }

    public List<EstimateDetailDTO> getReceiveEstimateList(Long requestId) {
        List<Estimate> estimates = estimateRepository.findByRequest_requestId(requestId);
        List<EstimateDetailDTO> estimateDetailDTOS = new ArrayList<>();

        for (Estimate estimate : estimates) {
            estimateDetailDTOS.add(new EstimateDetailDTO(
                    estimate.getEstimateId(),
                    estimate.getDetective().getDetectiveId(),
                    estimate.getDetective().getUser().getUserName(),
                    estimate.getDescription(),
                    estimate.getPrice(),
                    estimate.getTitle(),
                    estimate.getCreateAt(),
                    estimate.getDetective().getDetectiveGender(),
                    estimate.getDetective().getSpecialties().stream()
                            .map(s -> s.getSpeciality().getSpecialityName())
                            .collect(Collectors.toList()),
                    estimate.getDetective().getLocation(),
                    estimate.getDetective().getProfilePicture()
            ));
        }
        System.out.println("@@@@@고객의 탐정답변서 리스트 상세보기@@@@@"+estimateDetailDTOS);
        return estimateDetailDTOS;
    }
}

