package org.detective.services;

import org.detective.entity.User;
import org.detective.repository.UserRepository;
import org.detective.util.CustomUserDetails;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {


//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

//    private final PasswordEncoder passwordEncoder;

//    @Autowired
//    public CustomUserDetailsService(@Lazy PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("나도몰라");
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }


        System.out.println("비밀번호확인"+user.getPassword());
//        if(username.equals("admin")){
//            user.setRole("ROLE_ADMIN");
//        }else if(username.equals("detective")){
//            user.setRole("ROLE_DETECTIVE");
//        }else{
//            user.setRole("ROLE_USER");
//        }


        System.out.println("login role test"+getAuthorities(user.getRole()));


        System.out.print(user.getUsername()+"이게 맞아?");
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(),
//                user.getPassword(),
//                getAuthorities(user.getRole()) // 역할을 권한으로 변환하여 전달
//        );
        return new CustomUserDetails(
                user.getEmail(),
                user.getPassword(),
                user.getUserId(),  // User ID를 추가
                getAuthorities(user.getRole())  // 역할을 권한으로 변환하여 전달
        );
    }

    // 역할을 GrantedAuthority로 변환
    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Arrays.stream(new String[]{role})
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}

