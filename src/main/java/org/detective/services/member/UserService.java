package org.detective.services.member;

//import org.detective.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.detective.dto.UserCountDTO;
import org.detective.entity.Client;
import org.detective.entity.User;
import org.detective.repository.ClientRepository;
import org.detective.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registerUser(User user) {
        // 비즈니스 로직을 여기에 추가할 수 있습니다.
        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String role = user.getRole();
        if(role.equals("ROLE_USER")){
            userRepository.save(user);
            Long id = user.getUserId();
            Client client = new Client();
            client.setClientId(id);
            client.setUser(user);
            clientRepository.save(client);
            return true;
        }
        else{
            userRepository.save(user);
            return true;
        }
    }

    public boolean checkPw(String rawPassword, String encodedPassword){
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public boolean updateUser(User user, User user2) {
        // 비즈니스 로직을 여기에 추가할 수 있습니다.
        user2.setUserName(user.getUserName());
        user2.setPhoneNumber(user.getPhoneNumber());
        userRepository.save(user2);
        return true;
    }

    public boolean updateUserPW(User user, User user2) {
        // 비즈니스 로직을 여기에 추가할 수 있습니다.
        // 비밀번호 암호화
        user2.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user2);
        return true;
    }

    public boolean existsByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user != null; // user가 null이 아니면 true, null이면 false 반환
    }

    public List<UserCountDTO> countUsersByCreatedAtAndRole() {
        List<Object[]> results = userRepository.countByRoleAndCreatedAt();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return results.stream()
                .map(result -> new UserCountDTO(
                        result[0].toString(), // 날짜 변환
                        (String) result[1],   // 역할
                        ((Number) result[2]).longValue() // 개수
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new EntityNotFoundException("User not found for id: " + userId);
        }
    }
}
