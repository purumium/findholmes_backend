package org.detective.repository;

import org.detective.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // 추가적으로 필요한 쿼리 메서드를 정의할 수 있습니다.
=======
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
>>>>>>> jpa
}
