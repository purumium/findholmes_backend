package org.detective.services.Review;

import org.detective.dto.MyReviewListDTO;
import org.detective.dto.ReviewDTO;
import org.detective.dto.ReviewListDTO;
import org.detective.entity.*;
import org.detective.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private DetectiveRepository detectiveRepository;
    @Autowired
    private EstimateRepository estimateRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public List<ReviewListDTO> getReviewsByDetective(Detective detective) {
        List<Review> reviews =  reviewRepository.findAllByDetective(detective);
        List<ReviewListDTO> reviewDTOS = new ArrayList<>();
        for (int i = 0; i < reviews.size(); i++) {
            Review review = reviews.get(i);
            // review 객체에 대해 원하는 작업 수행
            ReviewListDTO reviewDTO = new ReviewListDTO(0L,0L,0.0,"", LocalDateTime.now());
            reviewDTO.setId(review.getId());
            reviewDTO.setDetectiveId(review.getDetective().getDetectiveId());
            reviewDTO.setRating(review.getRating());
            reviewDTO.setContent(review.getContent());
            reviewDTO.setUpdatedAt(review.getUpdatedAt());

            reviewDTOS.add(reviewDTO);
        }
        return reviewDTOS;
    }

    @Transactional
    public Boolean checkReviewByEstimate(Long estimateId){
       Estimate estimate = estimateRepository.findById(estimateId)
            .orElseThrow(() -> new RuntimeException("Estimate not found"));

        Optional<Review> review = reviewRepository.findByEstimate(estimate);

        return review.isPresent();
    }

    @Transactional
    public List<MyReviewListDTO> getReviewsByClient(Long userId) {
        Client client = clientRepository.findByUser_userId(userId);
        List <Review> reviews = reviewRepository.findAllByClient(client);
        List<MyReviewListDTO> reviewDTOs = reviews.stream()
                .map(review -> new MyReviewListDTO(
                        review.getId(),
                        review.getContent(),
                        review.getRating(),
                        review.getClient().getClientId(),
                        review.getUpdatedAt(),  // LocalDateTime 필드
                        review.getEstimate().getTitle(),
                        review.getDetective().getUser().getUserName()
                ))
                .toList();
        return reviewDTOs;
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        // 리뷰 삭제
        Double deletedRating = review.getRating();
        Detective detective = review.getDetective();

        reviewRepository.deleteById(reviewId);
        // 리뷰 수 -1
        detective.decreaseReviewCount();
        if (detective.getReviewCount() > 0) {
            double updatedAverageRating = ((detective.getAverageRating() * (detective.getReviewCount() + 1)) - deletedRating) / detective.getReviewCount();
            detective.setAverageRating(updatedAverageRating);
        } else {
            // 리뷰가 없는 경우 평점을 0으로 설정
            detective.setAverageRating(0.0);
        }

        detectiveRepository.save(detective);
    }

    @Transactional
    public Estimate getEstimateInfo(Long estimateId) {
        return estimateRepository.findById(estimateId).orElseThrow(() -> new RuntimeException("Estimate not found"));
    }

}
