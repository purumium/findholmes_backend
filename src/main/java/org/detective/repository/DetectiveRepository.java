package org.detective.repository;

import org.detective.entity.Detective;
import org.detective.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetectiveRepository extends JpaRepository<Detective, Long> {
    // 추가적으로 필요한 쿼리 메서드를 정의할 수 있습니다.
    List<Detective> findAll();

    @Query(value = "SELECT d.* FROM detectives d JOIN detectives_specialities s ON d.detective_id = s.detective_id where s.speciality_id=:specialityId and d.location=:location", nativeQuery = true)
    List<Detective> getDetectiveLS(Long specialityId, String location);

    @Query(value = "SELECT d.* FROM detectives d JOIN detectives_specialities s ON d.detective_id = s.detective_id where s.speciality_id=:specialityId and d.detective_gender=:gender and d.\"LOCATION\"=:location", nativeQuery = true)
    List<Detective> getDetectiveLSG(Long specialityId, String gender, String location);

    Detective findByUser(User user);
    Detective findByUser_UserId(Long userId);
    Detective findByDetectiveId(Long detectiveId);

    Optional<Detective> findOptionalByUser(User user);

    Optional<Detective> findByUserUserId(Long userId);

//    List<Detective> findByLocationAndSpecialtiesId(String location, Long specialityId);

    @Query("SELECT d FROM Detective d JOIN d.specialties ds WHERE d.location = :location AND ds.speciality.specialityId = :specialityId")
    List<Detective> findByLocationAndSpecialityId(@Param("location") String location, @Param("specialityId") Long specialityId);

    @Query("SELECT d FROM Detective d JOIN d.specialties ds WHERE ds.speciality.specialityId = :specialityId")
    List<Detective> findBySpecialityId(@Param("specialityId") Long specialityId);

    @Query("SELECT d FROM Detective d JOIN d.specialties ds WHERE d.location = :location")
    List<Detective> findByLocation(@Param("location") String location);

    @Modifying
    @Query("UPDATE Detective d SET d.currentPoints=(d.currentPoints-1000) where d.detectiveId=:detectiveId")
    void setCurrentPoints(Long detectiveId);

}

