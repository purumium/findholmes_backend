package org.detective.repository;

import org.detective.entity.DetectiveApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetectiveApprovalRepository extends JpaRepository<DetectiveApproval, Long> {
    // JpaRepository는 기본적으로 findAll 메서드를 제공합니다.
    @Query(value = "SELECT * FROM detective_approvals", nativeQuery = true)
    List<DetectiveApproval> findAllApprovals();

    @Query(value="SELECT * FROM detective_approvals WHERE detective_id = :detectiveId", nativeQuery = true)
    DetectiveApproval findByDetectiveApproval_DetectiveId(@Param("detectiveId") Long detectiveId);
}
