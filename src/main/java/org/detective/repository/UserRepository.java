package org.detective.repository;

import org.detective.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 기본적인 CRUD 작업은 JpaRepository가 제공하므로 추가적인 메소드는 필요에 따라 정의할 수 있습니다.

    // 예를 들어, 사용자 이름으로 사용자 찾기
    User findByUserName(String userName);
    User findByEmail(String email);
}
