package org.detective.repository;


import org.detective.entity.DetectiveSpeciality;
import org.detective.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetectiveSpecialityRepository extends JpaRepository<DetectiveSpeciality, Long> {
    List<DetectiveSpeciality> findByDetective_DetectiveId(Long detectiveId);

    @Query("SELECT ds FROM DetectiveSpeciality ds WHERE ds.id IN :ids")
    List<DetectiveSpeciality> findByIdIn(@Param("ids") List<Long> ids);
}