package org.detective.controller.DeletePlz;

import org.detective.entity.ApprovalStatus;
import org.detective.entity.Detective;
import org.detective.entity.User;
import org.detective.repository.DetectiveRepository;
import org.detective.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/plz")
public class DeletePlzController {
    private final UserRepository userRepository;
    private final DetectiveRepository detectiveRepository;

    public DeletePlzController(UserRepository userRepository, DetectiveRepository detectiveRepository) {
        this.userRepository = userRepository;
        this.detectiveRepository = detectiveRepository;
    }

    @PostMapping("detective")
    public void registerDetective() {
        Random random = new Random();
        List<String> locations = new ArrayList<>();

        locations.add("서울특별시");
        locations.add("경기도(인천)");
        locations.add("강원도");
        locations.add("충청북도");
        locations.add("충청남도");
        locations.add("전라북도");
        locations.add("전라남도(광주)");
        locations.add("경상북도(대구)");
        locations.add("경상남도(부산,울산)");
        locations.add("제주특별자치도");

        List<String> gender = new ArrayList<>();

        gender.add("MALE");
        gender.add("FEMALE");

        for (int i=20; i<70; i++) {
            Detective detective = detectiveRepository.findByUser_UserId(Long.valueOf(i));
            if (detective != null) {
                detective.setCompany("탐정사무소"+(i-19));
                detective.setIntroduction("안녕하세요 탐정"+(i-19)+"입니다.");
                detective.setDetectiveLicense(detective.getDetectiveLicense()+".png");
                detective.setBusinessRegistration(detective.getBusinessRegistration()+".png");
                detective.setProfilePicture(detective.getProfilePicture()+".png");
                detectiveRepository.save(detective);
            }

        }
    }
}
