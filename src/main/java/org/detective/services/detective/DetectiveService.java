package org.detective.services.detective;

//import org.detective.repository.DetectiveRepository;
import org.detective.dto.DetectiveDTO;
import org.detective.entity.Detective;
import org.detective.repository.DetectiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.detective.entity.DetectiveSpeciality;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DetectiveService {

    @Autowired
    private DetectiveRepository detectiveRepository;

    public Detective getDetectiveById(Long detectiveId) {
        return detectiveRepository.findById(detectiveId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Detective not found"));
    }

    public Detective getDetectiveByUserId(Long userId) {
        // userId로 Detective를 조회
        Optional<Detective> detectiveOptional = detectiveRepository.findByUserUserId(userId);

        // 결과가 있으면 반환하고, 없으면 예외를 던질 수 있습니다
        return detectiveOptional.orElseThrow(() -> new RuntimeException("Detective not found for userId: " + userId));
    }

    public List<Detective> findDetectivesByLocationAndSpecialities(String location, List<Long> specialityIds) {
        return detectiveRepository.findByLocationAndSpecialities(location, specialityIds);
    }


    public List<DetectiveDTO> findAllDetectives() {
        List<Detective> detectives = detectiveRepository.findAll();
        return detectives.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private DetectiveDTO convertToDTO(Detective detective) {
        DetectiveDTO dto = new DetectiveDTO();
        dto.setUserId(detective.getUser().getUserId());
        dto.setCurrentPoints(detective.getCurrentPoints().doubleValue());
        dto.setBusinessRegistration(detective.getBusinessRegistration());
        dto.setDetectiveLicense(detective.getDetectiveLicense());
        dto.setProfilePicture(detective.getProfilePicture());
        dto.setIntroduction(detective.getIntroduction());
        dto.setLocation(detective.getLocation());
        dto.setDetectiveGender(detective.getDetectiveGender());
        long resolvedCases = detective.getResolvedCases();
        detective.setResolvedCases(resolvedCases);
        dto.setResolvedCases(resolvedCases);
        dto.setApprovalStatus(detective.getApprovalStatus().name());

        List<Long> specialties = detective.getSpecialties().stream()
                .map(DetectiveSpeciality::getId)
                .collect(Collectors.toList());
        dto.setSpecialties(specialties);

        return dto;
    }
}
