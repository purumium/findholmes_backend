package org.detective.controller.Detective;

import org.detective.dto.DetectiveDTO;
import org.detective.entity.*;
import org.detective.repository.DetectiveRepository;
import org.detective.repository.DetectiveSpecialityRepository;
import org.detective.repository.SpecialityRepository;
import org.detective.repository.UserRepository;
import org.detective.services.Speciality.SpecialityService;
import org.detective.services.admin.DetectiveApprovalService;
import org.detective.services.detective.DetectiveService;
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
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/detective")
public class DetectiveController {

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private DetectiveRepository detectiveRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DetectiveService detectiveService;

    @Autowired
    private DetectiveSpecialityRepository detectiveSpecialityRepository;

    @Autowired
    private SpecialityService specialityService;

    @Autowired
    private DetectiveApprovalService detectiveApprovalService;



    // 특정 userId로 탐정 정보 조회
    @GetMapping("/getDetectiveDetail")
    public DetectiveDTO getDetectiveByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = "";
        if (authentication != null && authentication.getPrincipal() != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            email = userDetails.getUsername();
            User user = userRepository.findByEmail(email);


            Detective detective = detectiveRepository.findByUser(user);

            System.out.println("select detective info"+detective);

            DetectiveDTO detectivedto = new DetectiveDTO();

            //dto 매핑
            Long points = detective.getCurrentPoints();
            detectivedto.setCurrentPoints(points.doubleValue());

            detectivedto.setBusinessRegistration(detective.getBusinessRegistration());
            detectivedto.setDetectiveLicense(detective.getDetectiveLicense());
            detectivedto.setProfilePicture(detective.getProfilePicture());
            detectivedto.setIntroduction(detective.getIntroduction());
            detectivedto.setLocation(detective.getLocation());
            detectivedto.setDetectiveGender(detective.getDetectiveGender());
            detectivedto.setResolvedCases(detective.getResolvedCases());
            detectivedto.setApprovalStatus(detective.getApprovalStatus().toString());
            detectivedto.setUserName(detective.getUser().getUserName());
            detectivedto.setEmail(detective.getUser().getEmail());
            detectivedto.setPhoneNumber(detective.getUser().getPhoneNumber());
            detectivedto.setCreatedAt(detective.getUser().getCreatedAt());

            System.out.println("detectiveDTO"+detective.getSpecialties());

//            Long detectiveId = detective.getDetectiveId();
            List<DetectiveSpeciality> specialities = detectiveSpecialityRepository.findByDetective_DetectiveId(detective.getDetectiveId());
            System.out.println("aaaaaaaaaaaaaa speciality"+specialities);

            List<Long> slist = specialities.stream()
                    .map(DetectiveSpeciality::getId)  // 각 DetectiveSpeciality의 ID를 추출
                    .collect(Collectors.toList());

            System.out.println(specialityService.getSpecialitiesByDetectiveSpecialityIds(slist));

            List<String> slist2 = specialityService.getSpecialitiesByDetectiveSpecialityIds(slist).stream()
                    .map(Speciality::getSpecialityName)  // 각 DetectiveSpeciality의 ID를 추출
                    .collect(Collectors.toList());

            detectivedto.setSpecialtiesName(slist2);

            return detectivedto;

        }else{
            DetectiveDTO detectivedto = new DetectiveDTO();
            return detectivedto;

        }


    }

    @GetMapping("/specialties")
    public List<Speciality> getAllSpecialties() {
        System.out.println("탐정 전문 가져오기"+specialityService.getAllSpecialties());
        return specialityService.getAllSpecialties();
    }

    @GetMapping("/checkregister")
    public String checkDetectiveRegister() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = "";
        if (authentication != null && authentication.getPrincipal() != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            email = userDetails.getUsername();
            User user = userRepository.findByEmail(email);
            Detective detective = detectiveRepository.findByUser(user);
            if (detective == null) {
                // 처리: 예를 들어, 예외를 던지거나 기본값을 반환할 수 있습니다.
                return "NO";
            }else{
                return detective.getApprovalStatus().toString();
            }
        }else{
            return "error";
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerDetective(@RequestBody DetectiveDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = "";
        if (authentication != null && authentication.getPrincipal() != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            email = userDetails.getUsername();
            User user = userRepository.findByEmail(email);
            Detective detective = new Detective();
            try {
                detective.setUser(user);
                detective.setIntroduction(request.getIntroduction());

                detective.setLocation(request.getLocation());
                detective.setCurrentPoints(0L);
                detective.setDetectiveGender(request.getDetectiveGender());
                // Long 객체를 long 기본형으로 캐스팅
                long resolvedCases = request.getResolvedCases();
                detective.setResolvedCases(resolvedCases);
                detective.setBusinessRegistration(request.getBusinessRegistration());
                detective.setProfilePicture(request.getProfilePicture());
                detective.setDetectiveLicense(request.getDetectiveLicense());
                detective.setApprovalStatus(ApprovalStatus.PENDING);

                Detective savedDetective = detectiveRepository.save(detective);
                Long id = savedDetective.getDetectiveId();
                List<Long> specialties = request.getSpecialties();
                int size = specialties.size(); // 리스트의 크기

                for(int i = 0;i<size;i++){
                    DetectiveSpeciality detectiveSpeciality = new DetectiveSpeciality();
                    detectiveSpeciality.setDetective(savedDetective); //detective 객체 할당
                    System.out.println(specialityService.getSpecialityById(specialties.get(i)));
                    detectiveSpeciality.setSpeciality(specialityService.getSpecialityById(specialties.get(i)));
                    detectiveSpecialityRepository.save(detectiveSpeciality);
                }

                DetectiveApproval detectiveApproval = new DetectiveApproval();
                detectiveApproval.setDetective(savedDetective);
                detectiveApproval.setApprovalStatus(ApprovalStatus.PENDING);
                detectiveApproval.setRejReason("");
                detectiveApprovalService.save(detectiveApproval);

//            Detective savedDetective = detectiveRepository.save(detective);


                // 2. Specialty 엔티티들을 데이터베이스에서 조회
//            Set<Specialty> specialties = new HashSet<>(specialtyRepository.findAllById(request.getSpecialties()));
//            List<Speciality> specialties = new ArrayList<>(specialityRepository.findAllById(request.getSpecialties()));
//            // 3. Detective 객체에 specialties 설정
//            detective.set(specialties);
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
        else{
            return ResponseEntity.status(500).body("탐정 등록에 실패했습니다: ");
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