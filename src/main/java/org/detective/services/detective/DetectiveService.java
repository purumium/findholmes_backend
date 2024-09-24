package org.detective.services.detective;

//import org.detective.repository.DetectiveRepository;
import org.detective.dto.DetectiveDTO;
import org.detective.dto.DetectiveSimpleDTO;
import org.detective.entity.Detective;
import org.detective.entity.User;
import org.detective.repository.*;
import org.detective.services.Speciality.SpecialityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    SpecialityService specialityService;

    @Autowired
    DetectiveSpecialityRepository detectiveSpecialityRepository;
    @Autowired
    private SpecialityRepository specialityRepository;


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

//    public DetectiveService(DetectiveRepository detectiveRepository) {
//        this.detectiveRepository = detectiveRepository;
//    }

//    public List<Detective> getDetectivesByLocationAndSpeciality(String location, Long specialityId) {
//        return detectiveRepository.findByLocationAndSpecialityId(location, specialityId);
//    }



    //테스트
    public List<Detective> findAllDetectives2(){
        return detectiveRepository.findAll();
    }

    public DetectiveSimpleDTO getDetectiveInfo(Long detectiveId) {
        Detective detective = detectiveRepository.findByDetectiveId(detectiveId);

        DetectiveSimpleDTO detectiveSimpleDTO = new DetectiveSimpleDTO(detective.getDetectiveId(),
                detective.getUser().getUserName(),
                detective.getDetectiveGender(),
                specialityRepository.findByDetectiveId(detectiveId),
                detective.getLocation(),
                detective.getProfilePicture());
        System.err.println("!@#$% Sepciality : "+detectiveSimpleDTO.getSpecialities().toString()+"!@#$%");
        return detectiveSimpleDTO;
    }

    public List<DetectiveDTO> findAllDetectives() {
        List<Detective> detectives = detectiveRepository.findAll();
        return detectives.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public boolean updateDetective(User user, Detective detective, DetectiveDTO request) {
        // 비즈니스 로직을 여기에 추가할 수 있습니다.
        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserName(request.getUserName());
        user.setPhoneNumber(request.getPhoneNumber());
        userRepository.save(user);
        detective.setProfilePicture(request.getProfilePicture());
        detective.setAdditionalCertifications(request.getAdditionalCertifications());
        detective.setLocation(request.getLocation());
        detective.setDescription(request.getDescription());
        detective.setIntroduction(request.getIntroduction());

        Detective savedDetective = detectiveRepository.save(detective);



        Long id = savedDetective.getDetectiveId();
        // savedDetective의 ID를 사용하여 모든 DetectiveSpeciality 삭제
        detectiveSpecialityRepository.deleteByDetectiveId(id);


        List<Long> specialties = request.getSpecialties();
        int size = specialties.size(); // 리스트의 크기

        for(int i = 0;i<size;i++){
            DetectiveSpeciality detectiveSpeciality = new DetectiveSpeciality();
            detectiveSpeciality.setDetective(savedDetective); //detective 객체 할당
            System.out.println(specialityService.getSpecialityById(specialties.get(i)));
            detectiveSpeciality.setSpeciality(specialityService.getSpecialityById(specialties.get(i)));
            detectiveSpecialityRepository.save(detectiveSpeciality);
        }
        return true;

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
        dto.setCompany(detective.getCompany());
        dto.setDescription(detective.getDescription());
        dto.setAdditionalCertifications(detective.getAdditionalCertifications());

        List<Long> specialties = detective.getSpecialties().stream()
                .map(DetectiveSpeciality::getId)
                .collect(Collectors.toList());
        dto.setSpecialties(specialties);

        return dto;
    }
}
