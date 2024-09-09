package org.detective.repository;

import org.detective.entity.Client;
import org.detective.entity.Detective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetectiveRepository extends JpaRepository<Detective, Long> {
    // 추가적으로 필요한 쿼리 메서드를 정의할 수 있습니다.

    @Query(value = "SELECT * FROM Detectives d WHERE d.speciality_id = :specialityId ORDER BY DBMS_RANDOM.VALUE FETCH FIRST 5 ROWS ONLY", nativeQuery = true)
    List<Detective> getDetectiveBySpecialtyId(Long specialtyId);
}
