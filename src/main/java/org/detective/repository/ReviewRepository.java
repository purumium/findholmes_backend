package org.detective.repository;

import org.detective.entity.Detective;
import org.detective.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Detective를 기준으로 Review 목록 조회
    List<Review> findAllByDetective(Detective detective);
}
