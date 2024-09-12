package org.detective.services.Estimate;

import org.detective.dto.EstimateDTO;
import org.detective.entity.Client;
import org.detective.entity.Estimate;
import org.detective.entity.User;
import org.detective.repository.ClientRepository;
import org.detective.repository.DetectiveRepository;
import org.detective.repository.EstimateRepository;
import org.detective.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstimateService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final EstimateRepository estimateRepository;

    public EstimateService(UserRepository userRepository, DetectiveRepository detectiveRepository, ClientRepository clientRepository, EstimateRepository estimateRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.estimateRepository = estimateRepository;
    }

    public List<EstimateDTO> getReceivedEstimate(String email) {
        User user = userRepository.findByEmail(email);
        Client client = clientRepository.findByUser(user);

        List<Estimate> estimates = estimateRepository.findByClient(client);

        List<EstimateDTO> estimateList = new ArrayList<>();
        for (Estimate estimate : estimates) {
            estimateList.add(new EstimateDTO(estimate.getEstimateId(),
                                            estimate.getRequest().getRequestId(),
                                            estimate.getRequest().getTitle(),
                                            estimate.getCreateAt(),
                                            estimate.getRequest().getSpeciality().getSpecialityName()));
        }
        System.err.println("Estimate list: " + estimateList);
        return estimateList;
    }
}
