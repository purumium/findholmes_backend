package org.detective.servies;

import org.detective.entity.User;
import org.detective.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {


//    @Autowired
//    private PasswordEncoder passwordEncoder;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomUserDetailsService(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("나도몰라");
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
        User user = new User();
        user.setUsername("test");
        String rawPassword = "1111"; // 원래 비밀번호
        user.setPassword(passwordEncoder.encode(rawPassword)); // 비밀번호를 인코딩
        System.out.println("비밀번호확인"+user.getPassword());
        user.setRole("1");

        // 역할을 GrantedAuthority로 변환
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());

        System.out.print(user.getUsername()+"이게 맞아?");
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(authority) // List<GrantedAuthority> 형태로 전달
        );
    }
}

