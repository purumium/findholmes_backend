package org.detective.controller.Member;

import lombok.Data;
import org.detective.dto.EmailDTO;
import org.detective.dto.UserDTO;
import org.detective.entity.Client;
import org.detective.entity.User;

import org.detective.repository.ClientRepository;
import org.detective.repository.SpecialityRepository;

import org.detective.repository.SpecialityRepository;
import org.detective.repository.UserRepository;

import org.detective.services.email.EmailService;
import org.detective.services.member.UserService;
import org.detective.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("Registration successful!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        System.out.println(user+"update test");
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = "";
            if (authentication != null && authentication.getPrincipal() != null) {
                Object principal = authentication.getPrincipal();
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                email = userDetails.getUsername();
            }
            User user2 = userRepository.findByEmail(email);
            userService.updateUser(user,user2);

            return ResponseEntity.ok("Registration successful!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
        }
    }

    @GetMapping("/pwCheck")
    public Boolean passwordCheck(@RequestParam String password){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = "";
        if (authentication != null && authentication.getPrincipal() != null) {
            Object principal = authentication.getPrincipal();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            email = userDetails.getUsername();
        }
        User user = userRepository.findByEmail(email);

        if(userService.checkPw(password,user.getPassword())){
            return true;
        }else{
            return false;
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("일단 성공");
        System.out.println(loginRequest.getId());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPassword()));
            System.out.println("여기까지 옴?"+authentication);
            String token = jwtUtil.generateToken(authentication);
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body("asdsadasdsad");
        }
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean emailExists = userService.existsByEmail(email);
        return ResponseEntity.ok(!emailExists); // 이메일이 존재하지 않으면 true 반환
    }

    @GetMapping("/userinfo")
    public User getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = "";
        if (authentication != null && authentication.getPrincipal() != null) {
            Object principal = authentication.getPrincipal();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println(" ------ userinfo : " + userDetails.getUsername());

            email = userDetails.getUsername();
        }
        User user = userRepository.findByEmail(email);

        return user;
    }

    @GetMapping("/user/allinfo")
    public UserDTO getUserAllInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = "";

        if (authentication != null && authentication.getPrincipal() != null) {
            Object principal = authentication.getPrincipal();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            email = userDetails.getUsername();
        }
        User userInfo = userRepository.findByEmail(email);
        Client client = clientRepository.findByUser_userId(userInfo.getUserId());

        UserDTO user = new UserDTO(
                userInfo.getUserId(),
                userInfo.getUserName(),
                userInfo.getEmail(),
                userInfo.getRole(),
                client.getCurrentPoints()
        );

        return user;
    }


}

@Data
class LoginRequest {
    private String id;
    private String password;

}

@Data
class LoginResponse {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    // Getters
}