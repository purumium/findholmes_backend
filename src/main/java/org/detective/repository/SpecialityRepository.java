package org.detective.repository;

import org.detective.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
    List<Speciality> findAll();
    List<Speciality> findBySpecialityIdIn(List<Long> specialityIds);
    @Query("SELECT s FROM Speciality s JOIN DetectiveSpeciality d ON s.specialityId = d.speciality.specialityId WHERE d.detective.detectiveId = :detectiveId")
    List<Speciality> findByDetectiveId(Long detectiveId);
}