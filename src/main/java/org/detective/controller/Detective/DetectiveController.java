package org.detective.controller.Detective;

import jakarta.transaction.Transactional;
import org.detective.dto.DetectiveApprovalDTO;
import org.detective.dto.DetectiveDTO;
import org.detective.dto.DetectiveSimpleDTO;
import org.detective.entity.*;
import org.detective.repository.*;
import org.detective.services.Speciality.SpecialityService;
import org.detective.services.admin.DetectiveApprovalService;
import org.detective.services.detective.DetectiveService;
import org.detective.services.member.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

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
    private DetectiveApprovalRepository detectiveApprovalRepository;

    @Autowired
    private SpecialityService specialityService;

    @Autowired
    private DetectiveApprovalService detectiveApprovalService;

    @Autowired
    private UserService userService;


    // 특정 userId로 탐정 정보 조회
    @GetMapping("/getDetectiveDetail")
    public DetectiveDTO getDetectiveByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = "";

        if (authentication != null && authentication.getPrincipal() != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                email = userDetails.getUsername(); // UserDetails에서 이메일 가져오기
            } else if (principal instanceof OAuth2User) {
                OAuth2User oauthUser = (OAuth2User) principal;
                email = oauthUser.getAttribute("email"); // OAuth2User에서 이메일 가져오기
            } else {
                System.err.println("Authentication principal is not an instance of UserDetails or OAuth2User");
            }


            User user = userRepository.findByEmail(email);


            Detective detective = detectiveRepository.findByUser(user);
            if (detective != null) {
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
                detectivedto.setCompany(detective.getCompany());
                detectivedto.setDescription(detective.getDescription());
                detectivedto.setAdditionalCertifications(detective.getAdditionalCertifications());

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
                detectivedto.setUserName(user.getUserName());
                detectivedto.setEmail(user.getEmail());


                return detectivedto;
            }
        }else{
            DetectiveDTO detectivedto = new DetectiveDTO();
            return detectivedto;

        }
    }

    //@직접 의뢰요청 시 탐정의 정보를 불러오는 메서드
    @GetMapping("/info")
    public DetectiveSimpleDTO getDetectiveInfo(@RequestParam("detectiveId") Long detectiveId) {
        return detectiveService.getDetectiveInfo(detectiveId);
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

            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                email = userDetails.getUsername(); // UserDetails에서 이메일 가져오기
            } else if (principal instanceof OAuth2User) {
                OAuth2User oauthUser = (OAuth2User) principal;
                email = oauthUser.getAttribute("email"); // OAuth2User에서 이메일 가져오기
            } else {
                System.err.println("Authentication principal is not an instance of UserDetails or OAuth2User");
            }

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

    @Transactional
    @PostMapping("/reregister")
    public ResponseEntity<String> reRegisterDetective(@RequestBody DetectiveDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = "";
        if (authentication != null && authentication.getPrincipal() != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                email = userDetails.getUsername(); // UserDetails에서 이메일 가져오기
            } else if (principal instanceof OAuth2User) {
                OAuth2User oauthUser = (OAuth2User) principal;
                email = oauthUser.getAttribute("email"); // OAuth2User에서 이메일 가져오기
            } else {
                System.err.println("Authentication principal is not an instance of UserDetails or OAuth2User");
            }

            User user = userRepository.findByEmail(email);
            Detective detective = detectiveRepository.findByUser(user);

            deleteFile(detective.getBusinessRegistration(),"delete");
            deleteFile(detective.getDetectiveLicense(),"delete");
            if(detective.getProfilePicture()!=null){
                deleteFile(detective.getProfilePicture(),"delete");
            }
            if(detective.getAdditionalCertifications()!=null){
                deleteFile(detective.getAdditionalCertifications(),"delete");
            }
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
                detective.setAdditionalCertifications(request.getAdditionalCertifications());

                detective.setCompany(request.getCompany());
                detective.setDescription(request.getDescription());

                Detective savedDetective = detectiveRepository.save(detective);


//
//                DetectiveApproval detectiveApproval = new DetectiveApproval();
//                detectiveApproval.setDetective(savedDetective);
//                detectiveApproval.setApprovalStatus(ApprovalStatus.PENDING);
//                detectiveApproval.setRejReason("");
//                detectiveApprovalService.save(detectiveApproval);
                Long id = savedDetective.getDetectiveId();
                // savedDetective의 ID를 사용하여 모든 DetectiveSpeciality 삭제
                System.out.println(id+"detectiveid");
                detectiveSpecialityRepository.deleteByDetectiveId(id);


                List<Long> specialties = request.getSpecialties();
                int size = specialties.size(); // 리스트의 크기

                for(int i = 0;i<size;i++){
                    DetectiveSpeciality detectiveSpeciality = new DetectiveSpeciality();
                    detectiveSpeciality.setDetective(savedDetective); //detective 객체 할당
                    detectiveSpeciality.setSpeciality(null);
                    detectiveSpecialityRepository.save(detectiveSpeciality);
                    detectiveSpeciality.setDetective(savedDetective); //detective 객체 할당
                    detectiveSpeciality.setSpeciality(specialityService.getSpecialityById(specialties.get(i)));
                    detectiveSpecialityRepository.save(detectiveSpeciality);
                }

                DetectiveApproval detectiveApproval = detectiveApprovalRepository.findByDetective(savedDetective);
                detectiveApproval.setApprovalStatus(ApprovalStatus.PENDING);
                detectiveApproval.setRejReason("");
                detectiveApprovalRepository.save(detectiveApproval);

//                DetectiveApproval detectiveApproval = new DetectiveApproval();
//                detectiveApproval.setDetective(savedDetective);
//                detectiveApproval.setApprovalStatus(ApprovalStatus.PENDING);
//                detectiveApproval.setRejReason("");
//                detectiveApprovalService.save(detectiveApproval);

                return ResponseEntity.ok("탐정 등록이 완료되었습니다.");
            } catch (Exception e) {
                return ResponseEntity.status(500).body("탐정 등록에 실패했습니다: " + e.getMessage());
            }
        }
        else{
            return ResponseEntity.status(500).body("탐정 등록에 실패했습니다: ");
        }

    }

    @PostMapping("/register")
    public ResponseEntity<String> registerDetective(@RequestBody DetectiveDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = "";
        if (authentication != null && authentication.getPrincipal() != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                email = userDetails.getUsername(); // UserDetails에서 이메일 가져오기
            } else if (principal instanceof OAuth2User) {
                OAuth2User oauthUser = (OAuth2User) principal;
                email = oauthUser.getAttribute("email"); // OAuth2User에서 이메일 가져오기
            } else {
                System.err.println("Authentication principal is not an instance of UserDetails or OAuth2User");
            }

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
                detective.setAdditionalCertifications(request.getAdditionalCertifications());

                detective.setCompany(request.getCompany());
                detective.setDescription(request.getDescription());

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

                return ResponseEntity.ok("탐정 등록이 완료되었습니다.");
            } catch (Exception e) {
                return ResponseEntity.status(500).body("탐정 등록에 실패했습니다: " + e.getMessage());
            }
        }
        else{
            return ResponseEntity.status(500).body("탐정 등록에 실패했습니다: ");
        }
    }

    @Transactional // 이 어노테이션을 추가
    @PostMapping("/update")
    public ResponseEntity<String> updateDetective(@RequestBody DetectiveDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = "";
        if (authentication != null && authentication.getPrincipal() != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                email = userDetails.getUsername(); // UserDetails에서 이메일 가져오기
            } else if (principal instanceof OAuth2User) {
                OAuth2User oauthUser = (OAuth2User) principal;
                email = oauthUser.getAttribute("email"); // OAuth2User에서 이메일 가져오기
            } else {
                System.err.println("Authentication principal is not an instance of UserDetails or OAuth2User");
            }

            User user = userRepository.findByEmail(email);
            Detective detective = detectiveRepository.findByUser(user);
            System.out.println(detective+"update test");
            System.out.println(request+"update test2");

            detectiveService.updateDetective(user,detective,request);
            return ResponseEntity.ok("탐정 등록이 완료되었습니다.");
        }
        return ResponseEntity.ok("탐정 등록이 완료되었습니다.");
    }

    @PostMapping("/files")
    public ResponseEntity<Map<String, String>> uploadFiles(
            @RequestParam(value = "businessRegistration", required = false) MultipartFile businessRegistration,
            @RequestParam(value = "detectiveLicense",required = false) MultipartFile detectiveLicense,
            @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture,
            @RequestParam(value = "additionalCertification", required = false) MultipartFile additionalCertification) {

        Map<String, String> filePaths = new HashMap<>();

        try {
            // 각 파일을 저장하고 경로 반환
            String businessRegistrationPath = businessRegistration != null? saveFile(businessRegistration, "business_registration") : null;
            String detectiveLicensePath = detectiveLicense != null? saveFile(detectiveLicense, "detective_license") : null;
            String profilePicturePath = profilePicture != null ? saveFile(profilePicture, "profile_picture") : null;
            String additionalCertificationPath = additionalCertification != null ? saveFile(additionalCertification, "additional_certification") : null;

            if (businessRegistrationPath != null) {
                filePaths.put("businessRegistrationPath", businessRegistrationPath);
            }
            if (detectiveLicensePath != null) {
                filePaths.put("detectiveLicensePath", detectiveLicensePath);
            }
            if (profilePicturePath != null) {
                filePaths.put("profilePicturePath", profilePicturePath);
            }
            if (additionalCertificationPath != null) {
                filePaths.put("additionalCertificationPath", additionalCertificationPath);
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

    @PostMapping("/updateFile")
    public ResponseEntity<String> deleteFile(@RequestParam("filePath") String filePath, @RequestParam("state") String state) {
        System.out.println(filePath+"pathtest");
        if(state.equals("delete")){
            try {
                Path path = Paths.get(filePath);
                System.out.println(path+"pathtest2");
                if (Files.exists(path)) {
                    Files.delete(path);  // 파일 삭제
                    return ResponseEntity.ok("File deleted successfully.");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found.");
                }
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the file.");
            }
        }else{
            return ResponseEntity.ok("test");
        }

    }

    @GetMapping("/pwCheck")
    public Boolean passwordCheck(@RequestParam String password){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = "";
        if (authentication != null && authentication.getPrincipal() != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                email = userDetails.getUsername(); // UserDetails에서 이메일 가져오기
            } else if (principal instanceof OAuth2User) {
                OAuth2User oauthUser = (OAuth2User) principal;
                email = oauthUser.getAttribute("email"); // OAuth2User에서 이메일 가져오기
            } else {
                System.err.println("Authentication principal is not an instance of UserDetails or OAuth2User");
            }
        }
        User user = userRepository.findByEmail(email);

        if(userService.checkPw(password,user.getPassword())){
            return true;
        }else{
            return false;
        }
    }

    @GetMapping("/checkreject")
    public DetectiveApprovalDTO getApprovalStatusRejectByDetectiveId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = "";
        if (authentication != null && authentication.getPrincipal() != null) {Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                email = userDetails.getUsername(); // UserDetails에서 이메일 가져오기
            } else if (principal instanceof OAuth2User) {
                OAuth2User oauthUser = (OAuth2User) principal;
                email = oauthUser.getAttribute("email"); // OAuth2User에서 이메일 가져오기
            } else {
                System.err.println("Authentication principal is not an instance of UserDetails or OAuth2User");
            }
        }
        User user = userRepository.findByEmail(email);
        Detective detective = detectiveRepository.findByUser(user);
        DetectiveApproval approval = detectiveApprovalRepository.findByDetectiveApproval_DetectiveId(detective.getDetectiveId());

        if (approval != null) {
            DetectiveApprovalDTO dto = new DetectiveApprovalDTO();
            dto.setId(approval.getId());
            dto.setDetectiveId(approval.getDetective().getDetectiveId());
            dto.setApprovalStatus(approval.getApprovalStatus().toString());
            dto.setRejReason(approval.getRejReason());
            dto.setCreatedAt(approval.getCreateAt());
            dto.setConfirmedAt(approval.getConfirmedAt());

            return dto;
        } else {
            return null; // 또는 처리할 로직 추가
        }
    }


    // 탐졍 ID로 정보 가지고 오기
    @GetMapping("/{detectiveId}")
    public DetectiveDTO getDetecveById(@PathVariable Long detectiveId) {

        System.out.println("탐정 아이디 : " + detectiveId);
        Detective detective = detectiveRepository.findById(detectiveId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "탐정을 찾을 수 없습니다."));

        DetectiveDTO detectivedto = new DetectiveDTO();

        // DTO 매핑
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
        detectivedto.setCompany(detective.getCompany());
        detectivedto.setDescription(detective.getDescription());
        detectivedto.setAdditionalCertifications(detective.getAdditionalCertifications());

        // 탐정의 전문 분야 정보 추가
        List<String> specialties = detective.getSpecialties().stream()
                .map(s -> s.getSpeciality().getSpecialityName())
                .collect(Collectors.toList());
        detectivedto.setSpecialtiesName(specialties);

        return detectivedto;
    }





}