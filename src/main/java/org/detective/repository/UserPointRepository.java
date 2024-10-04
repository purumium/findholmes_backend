package org.detective.repository;

import org.detective.entity.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPointRepository extends JpaRepository<UserPoint, Long> {
    // 포인트 관련된 로직이 필요하면 여기에 추가
    List<UserPoint> findByUserUserId(Long userId);
}
