package org.detective.services;

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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("나도몰라");
        User user = userRepository.findByUsername(username);
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


//        System.out.println(getAuthorities(user.getRole()));


        System.out.print(user.getUsername()+"이게 맞아?");
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getAuthorities(user.getRole()) // 역할을 권한으로 변환하여 전달
        );
    }

    // 역할을 GrantedAuthority로 변환
    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Arrays.stream(new String[]{role})
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}

