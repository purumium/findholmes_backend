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
            ReviewListDTO reviewDTO = new ReviewListDTO(0L,0L,0,"", LocalDateTime.now());
            reviewDTO.setId(review.getId());
            reviewDTO.setDetectiveId(review.getDetective().getDetectiveId());
            reviewDTO.setRating(review.getRating());
            reviewDTO.setContent(review.getContent());
            reviewDTO.setUpdatedAt(review.getUpdatedAt());

            reviewDTOS.add(reviewDTO);
            System.out.println("Review ID: " + review.getId());
            System.out.println("Rating: " + review.getRating());
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
        int deletedRating = review.getRating();
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
    public void recalculateDetectiveRating(Long detectiveId, int deletedRating) {
        Detective detective = detectiveRepository.findById(detectiveId)
                .orElseThrow(() -> new RuntimeException("Detective not found"));

        int reviewCount = detective.getReviewCount();  // 탐정의 리뷰 총 개수
        double currentAverage = detective.getAverageRating();  // 현재 탐정의 평균 평점

        if (reviewCount == 1) {
            detective.setAverageRating(0.0);  // 리뷰가 하나였으면 삭제 후 평점은 0
            detective.setReviewCount(0);  // 리뷰 개수를 0으로 설정
        } else {
            // 새로운 평점을 계산 (삭제된 리뷰의 평점을 제외)
            double newAverage = (currentAverage * reviewCount - deletedRating) / (reviewCount - 1);
            detective.setAverageRating(newAverage);  // 새로운 평균 평점 저장
            detective.setReviewCount(reviewCount - 1);  // 리뷰 개수 감소
        }
        detectiveRepository.save(detective);  // 탐정 정보 저장
    }

}
