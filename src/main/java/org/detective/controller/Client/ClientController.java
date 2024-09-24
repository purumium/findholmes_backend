package org.detective.controller.Client;


import lombok.Data;
import org.detective.dto.DetectiveDTO;
import org.detective.entity.Detective;
import org.detective.entity.DetectiveSpeciality;
import org.detective.entity.Speciality;
import org.detective.repository.DetectiveRepository;
import org.detective.repository.DetectiveSpecialityRepository;
import org.detective.services.Speciality.SpecialityService;
import org.detective.services.detective.DetectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private DetectiveService detectiveService;

    @Autowired
    private DetectiveRepository detectiveRepository;

    @Autowired
    private DetectiveSpecialityRepository detectiveSpecialityRepository;

    @Autowired
    private SpecialityService specialityService;

    @PostMapping("/finddetectives")
    public List<DetectiveDTO> findDetectives(@RequestBody SearchRequest request) {
        System.out.println("\naaaaaaaa"+request.getLocation()+"\n"+request.getSpecialityId());
        List<Detective> detectives = new ArrayList<>();
        if(request.getLocation().equals("")){
            Long id = (long) request.getSpecialityId();
            detectives = detectiveRepository.findBySpecialityId(id);
        }
        else if(request.getSpecialityId()==0){
            detectives = detectiveRepository.findByLocation(request.getLocation());
        }else{
            // location과 specialityIds를 기반으로 탐정 조회
            Long id = (long) request.getSpecialityId();
            detectives = detectiveRepository.findByLocationAndSpecialityId(request.getLocation(), id);
        }

        // 리스트의 길이 확인
        int count = detectives.size();
        List<DetectiveDTO> detectivesDTO = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            DetectiveDTO dto = new DetectiveDTO(); // 새로운 DTO 객체 생성

            // DTO 매핑
            Long points = detectives.get(i).getCurrentPoints();
            dto.setCurrentPoints(points.doubleValue());
            dto.setBusinessRegistration(detectives.get(i).getBusinessRegistration());
            dto.setDetectiveLicense(detectives.get(i).getDetectiveLicense());
            dto.setProfilePicture(detectives.get(i).getProfilePicture());
            dto.setIntroduction(detectives.get(i).getIntroduction());
            dto.setLocation(detectives.get(i).getLocation());
            dto.setDetectiveGender(detectives.get(i).getDetectiveGender());
            dto.setResolvedCases(detectives.get(i).getResolvedCases());
            dto.setApprovalStatus(detectives.get(i).getApprovalStatus().toString());
            dto.setUserName(detectives.get(i).getUser().getUserName());
            dto.setEmail(detectives.get(i).getUser().getEmail());
            dto.setPhoneNumber(detectives.get(i).getUser().getPhoneNumber());
            dto.setCreatedAt(detectives.get(i).getUser().getCreatedAt());
            dto.setCompany(detectives.get(i).getCompany());
            dto.setDescription(detectives.get(i).getDescription());
            dto.setAdditionalCertifications(detectives.get(i).getAdditionalCertifications());
            dto.setUserId(detectives.get(i).getDetectiveId());

            // Specialities 매핑
            List<DetectiveSpeciality> specialities = detectiveSpecialityRepository.findByDetective_DetectiveId(detectives.get(i).getDetectiveId());
            List<Long> slist = specialities.stream()
                    .map(DetectiveSpeciality::getId)  // 각 DetectiveSpeciality의 ID를 추출
                    .collect(Collectors.toList());

            List<String> slist2 = specialityService.getSpecialitiesByDetectiveSpecialityIds(slist).stream()
                    .map(Speciality::getSpecialityName)  // 각 DetectiveSpeciality의 이름을 추출
                    .collect(Collectors.toList());

            dto.setSpecialtiesName(slist2);

            // DTO 리스트에 추가
            detectivesDTO.add(dto);
        }

        return detectivesDTO;
    }
}

@Data
class SearchRequest {

    private String location;
    private int specialityId;

    // Getters and Setters 생략
}
