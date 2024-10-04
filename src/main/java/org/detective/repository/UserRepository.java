package org.detective.repository;

import org.detective.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 예를 들어, 사용자 이름으로 사용자 찾기

    User findByUserName(String userName);

    User findByEmail(String email);

    Optional<User> findOptionalByEmail(String email);

    @Query(value = "SELECT TRUNC(created_at), role, COUNT(*) " +
            "FROM USERS " +
            "GROUP BY TRUNC(created_at), role " +
            "ORDER BY TRUNC(created_at) ASC",
            nativeQuery = true)
    List<Object[]> countByRoleAndCreatedAt();

    User findByUserId(Long i);
}