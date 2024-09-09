package org.detective.controller;

import org.detective.entity.User;
import org.detective.repository.UserRepository;
import org.detective.services.member.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class testController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/test2")
    public String test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            Object principal = authentication.getPrincipal();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println("is this right?");
            System.out.println(userDetails.getUsername());
        }

        return "성공";
    }

    @GetMapping("/getdetail")
    public User getDetail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = "";
        if (authentication != null && authentication.getPrincipal() != null) {
            Object principal = authentication.getPrincipal();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println(userDetails.getUsername());
            email = userDetails.getUsername();
        }
        User user = userRepository.findByEmail(email);

        return user;
    }
}