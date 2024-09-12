package org.detective.controller.Client;


import lombok.Data;
import org.detective.dto.DetectiveDTO;
import org.detective.entity.Detective;
import org.detective.services.detective.DetectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private DetectiveService detectiveService;

//    @PostMapping("/finddetectives")
//    public List<Detective> findDetectives(@RequestBody SearchRequest request) {
//        // location과 specialityIds를 기반으로 탐정 조회
//        System.out.println("id test"+request.getSpecialityId());
//        System.out.println(detectiveService.findDetectivesByLocationAndSpecialities(request.getLocation(), request.getSpecialityId()));
//        return detectiveService.findDetectivesByLocationAndSpecialities(request.getLocation(), request.getSpecialityId());
//    }
}

@Data
class SearchRequest {

    private String location;
    private int specialityId;

    // Getters and Setters 생략
}
