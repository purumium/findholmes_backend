package org.detective.repository;

import org.detective.entity.Client;
import org.detective.entity.Request;
import org.detective.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // 추가적으로 필요한 쿼리 메서드를 정의할 수 있습니다.

    Client findByUser(User user);
    Optional<Client> findOptionalByUser(User user);

    Client findByUser_userId(Long userId);



}



