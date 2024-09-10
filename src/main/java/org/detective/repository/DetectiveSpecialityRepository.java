package org.detective.repository;


import org.detective.entity.DetectiveSpeciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetectiveSpecialityRepository extends JpaRepository<DetectiveSpeciality, Long> {
    List<DetectiveSpeciality> findByDetective_DetectiveId(Long detectiveId);
}