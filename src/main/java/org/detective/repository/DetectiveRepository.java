package org.detective.repository;

import org.detective.entity.Client;
import org.detective.entity.Detective;
import org.detective.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetectiveRepository extends JpaRepository<Detective, Long> {
    // 추가적으로 필요한 쿼리 메서드를 정의할 수 있습니다.
    List<Detective> findAll();

    @Query(value = "SELECT * FROM (SELECT * FROM detectives ORDER BY DBMS_RANDOM.VALUE) WHERE ROWNUM <= 5", nativeQuery = true)
    List<Detective> getDetectiveRandom();


    Detective findByUser(User user);
    Detective findByDetectiveId(Long detectiveId);
    Optional<Detective> findOptionalByUser(User user);

    Optional<Detective> findByUserUserId(Long userId);

    // location과 specialties의 specialityId가 일치하는 모든 Detective 찾기
    @Query("SELECT d FROM Detective d JOIN d.specialties s WHERE d.location = :location AND s.speciality.specialityId IN :specialityIds")
    List<Detective> findByLocationAndSpecialities(@Param("location") String location, @Param("specialityIds") List<Long> specialityIds);


}

