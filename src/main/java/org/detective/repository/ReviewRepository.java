package org.detective.repository;

import org.detective.entity.Client;
import org.detective.entity.Detective;
import org.detective.entity.Estimate;
import org.detective.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Detective를 기준으로 Review 목록 조회
    List<Review> findAllByDetective(Detective detective);
    Optional<Review> findByEstimate(Estimate estimate);
    List<Review> findAllByClient(Client client);
}
