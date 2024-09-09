package org.detective.controller.Detective;

import org.detective.dto.DetectiveDTO;
import org.detective.entity.ApprovalStatus;
import org.detective.entity.Detective;
import org.detective.entity.Speciality;
import org.detective.entity.User;
import org.detective.repository.DetectiveRepository;
import org.detective.repository.SpecialtyRepository;
import org.detective.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/detective")
public class DetectiveController {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private DetectiveRepository detectiveRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/specialties")
    public List<Speciality> getAllSpecialties() {
        System.out.println(specialtyRepository.findAll());
        return specialtyRepository.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerDetective(@RequestBody DetectiveDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = "";
        if (authentication != null && authentication.getPrincipal() != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            email = userDetails.getUsername();
        }
        User user = userRepository.findByEmail(email);
        Detective detective = new Detective();
        detective.setUser(user);
        //detective.setCurrentPoints(0L);  //기본으로 0으로 디폴트 세팅

        try {
            detective.setIntroduction(request.getIntroduction());
            detective.setApprovalStatus(ApprovalStatus.PENDING);
            detective.setLocation(request.getLocation());
            detective.setDetectiveGender(request.getDetectiveGender());
            detective.setResolvedCases(request.getResolvedCases());
            detective.setBusinessRegistration(request.getBusinessRegistration());
            detective.setProfilePicture(request.getProfilePicture());
            detective.setDetectiveLicense(request.getDetectiveLicense());
//            detective.setSpecialties();

            // 2. Specialty 엔티티들을 데이터베이스에서 조회
//            Set<Specialty> specialties = new HashSet<>(specialtyRepository.findAllById(specialtiesIds));
//
//            // 3. Detective 객체에 specialties 설정
//            detective.setSpecialties(specialties);
//            System.out.println(request.getSpecialties());
            System.out.println(request);

//            // 전문 분야 처리
//            List<Specialty> specialties = specialtyRepository.findAllById(request.getSelectedSpecialties());
//            detective.setSpecialties(specialties);
//
//            // 탐정 저장
//            detectiveRepository.save(detective);

            return ResponseEntity.ok("탐정 등록이 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("탐정 등록에 실패했습니다: " + e.getMessage());
        }
    }

    @PostMapping("/files")
    public ResponseEntity<Map<String, String>> uploadFiles(
            @RequestParam("businessRegistration") MultipartFile businessRegistration,
            @RequestParam("detectiveLicense") MultipartFile detectiveLicense,
            @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture) {

        Map<String, String> filePaths = new HashMap<>();

        try {
            // 각 파일을 저장하고 경로 반환
            String businessRegistrationPath = saveFile(businessRegistration, "business_registration");
            String detectiveLicensePath = saveFile(detectiveLicense, "detective_license");
            String profilePicturePath = profilePicture != null ? saveFile(profilePicture, "profile_picture") : null;

            filePaths.put("businessRegistrationPath", businessRegistrationPath);
            filePaths.put("detectiveLicensePath", detectiveLicensePath);
            if (profilePicturePath != null) {
                filePaths.put("profilePicturePath", profilePicturePath);
            }

            return ResponseEntity.ok(filePaths);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private String saveFile(MultipartFile file, String folder) throws IOException {
        String uploadDir = "uploads/" + folder;
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(file.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        return uploadDir + "/" + fileName;  // 저장된 파일 경로 반환
    }


}