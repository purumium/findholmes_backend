package org.detective.controller.Client;


import org.detective.dto.DetectiveDTO;
import org.detective.services.detective.DetectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private DetectiveService detectiveService;

    @GetMapping("/finddetective")
    public ResponseEntity<List<DetectiveDTO>> getAllDetectives() {
        List<DetectiveDTO> detectives = detectiveService.findAllDetectives();
        return ResponseEntity.ok(detectives);
    }
}
