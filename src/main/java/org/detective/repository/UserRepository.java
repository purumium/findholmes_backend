package org.detective.repository;

import org.detective.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 예를 들어, 사용자 이름으로 사용자 찾기

    User findByUserName(String userName);

    User findByEmail(String email);
}