package org.detective.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.detective.entity.Client;
import org.detective.entity.User;
import org.detective.repository.ClientRepository;
import org.detective.repository.UserRepository;
import org.detective.services.CustomUserDetailsService;
import org.detective.util.CustomAuthenticationProvider;
import org.detective.util.JwtAuthenticationFilter;
import org.detective.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                          .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/member/**","/speciality/**","/request/**","/receive/**","/detective/**","/reply/**","/chatroom/**","/email/**","/estimate/**","/review/**","/oauth2/**","/uploads/").permitAll()
                        .requestMatchers("/test/**").hasRole("USER")
                        .requestMatchers("/admin/**").permitAll()
                        .requestMatchers("/client/**").permitAll()
                        .requestMatchers("/notification/**").permitAll()//
                        .anyRequest().authenticated()
                )
//                .oauth2Login(oauth2 -> oauth2
//                        .successHandler(this::handleOAuth2LoginSuccess) // 커스텀 성공 핸들러 설정
//                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // JWT 필터 추가


        return http.build();
    }

    private void handleOAuth2LoginSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        // 사용자 정보 가져오기
        OAuth2User user = (OAuth2User) authentication.getPrincipal();

        String email = user.getAttribute("email");  // 이메일 정보 가져오기
        String name = user.getAttribute("name");  // 이름 정보 가져오기

        // 사용자 정보를 로그에 출력
        System.out.println("User Email: " + email);
        System.out.println("User Name: " + name);

        // 사용자 정보가 없으면 가입 처리 (Optional)
        User user2 = userRepository.findByEmail(email);
        if (user2 == null) {
            // OAuth2 요청에서 state 값 추출
            String role = httpServletRequest.getParameter("role");
            System.out.println("rrr test"+role);

            if(role.equals("ROLE_USER")){
                // 신규 사용자라면 자동 가입 처리
                user2 = new User();
                user2.setEmail(email);
                user2.setUserName(name);
                user2.setPassword("changeitlater");
                user2.setPhoneNumber("010-0000-0000");
                user2.setRole(role);
                userRepository.save(user2);
                Long id = user2.getUserId();
                Client client = new Client();
                client.setClientId(id);
                client.setUser(user2);
                clientRepository.save(client);

            }else if(role.equals("ROLE_DETECTIVE")){
                user2 = new User();
                user2.setEmail(email);
                user2.setUserName(name);
                user2.setPassword("changeitlater");
                user2.setPhoneNumber("010-0000-0000");
                user2.setRole(role);
                userRepository.save(user2);
            }

        }


        UserDetails userDetails = userDetailsService.loadUserByUsername(user2.getEmail());

        // JWT 토큰 생성
        String token = jwtUtil.generateToken2(user2);  // 사용자 정보 기반으로 JWT 생성
        System.out.println("token2"+token);

        // 토큰을 포함하여 클라이언트로 리다이렉트
        try {
            httpServletResponse.sendRedirect("http://localhost:8000/?token=" + token);
        } catch (IOException e) {
            e.printStackTrace();  // 예외 처리
        }
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/uploads/**");
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

