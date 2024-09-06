package org.detective.controller.Member;

import lombok.Data;
import org.detective.entity.Specialty;
import org.detective.entity.User;
import org.detective.repository.SpecialtyRepository;
import org.detective.repository.UserRepository;
import org.detective.services.member.UserService;
import org.detective.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private SpecialtyRepository specialtyRepository;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("Registration successful!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
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
//            String token = "토큰입니다";
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

