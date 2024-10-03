package org.detective.controller.Review;

import org.detective.dto.MyReviewListDTO;
import org.detective.dto.ReviewDTO;
import org.detective.dto.ReviewListDTO;
import org.detective.entity.*;
import org.detective.repository.*;
import org.detective.services.Review.ReviewService;
import org.detective.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private DetectiveRepository detectiveRepository;

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private EstimateRepository estimateRepository;

    @PostMapping("/write")
    public ResponseEntity<String> writeReview(@RequestBody ReviewDTO reviewDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = "";
            if (authentication != null && authentication.getPrincipal() != null) {
                Object principal = authentication.getPrincipal();
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                email = userDetails.getUsername();
            }
            User user = userRepository.findByEmail(email);
            Client client = clientRepository.findByUser(user);
            Detective detective = detectiveRepository.findByDetectiveId(reviewDTO.getDetectiveId());
            Estimate estimate = estimateRepository.findByEstimateId(reviewDTO.getEstimateId());
            Review review = new Review();
            review.setClient(client);
            review.setDetective(detective);
            review.setRating(reviewDTO.getRating());
            review.setContent(reviewDTO.getContent());
            review.setEstimate(estimate);
            reviewRepository.save(review);
            detective.increaseReviewCount(); // 리뷰 개수 증가
            double updatedAverageRating = ((detective.getAverageRating() * detective.getReviewCount()) + review.getRating()) / detective.getReviewCount();

            // 탐정의 리뷰 개수와 평점 업데이트
            detective.setAverageRating(updatedAverageRating);
            detectiveRepository.save(detective);

            return ResponseEntity.ok("Registration successful!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateReview(@RequestBody ReviewDTO reviewDTO) {
        Optional<Review> review = reviewRepository.findById(reviewDTO.getId());
        if (review.isPresent()) {
            Detective detective = review.get().getDetective();
            int oldRating = review.get().getRating();
            Review foundReview = review.get();
            // review 객체에 대한 작업 수행
            foundReview.setContent(reviewDTO.getContent());
            foundReview.setRating(reviewDTO.getRating());
            reviewRepository.save(foundReview);

            // 탐정의 평점 업데이트
            double updatedAverageRating = ((detective.getAverageRating() * detective.getReviewCount()) - oldRating + foundReview.getRating()) / detective.getReviewCount();
            detective.setAverageRating(updatedAverageRating);
            detectiveRepository.save(detective);
            return ResponseEntity.ok("update successful!");
        } else {
            return ResponseEntity.status(500).body("cant find");
        }
    }

    // 탐정Id에 해당하는 리뷰 찾기
    @GetMapping("/get/detective/{id}")
    public List<ReviewListDTO> getReview(@PathVariable Long id) {
        Detective detective = detectiveRepository.findByDetectiveId(id);
        return reviewService.getReviewsByDetective(detective);
    }

    // 답변서Id에 해당하는 리뷰 찾기
    @GetMapping("/get/estimate/{estimateId}")
    public Boolean getReviewByEstimate(@PathVariable Long estimateId) {
        return reviewService.checkReviewByEstimate(estimateId);
    }

    // userId(clientId)에 해당하는 리뷰 찾기
    @GetMapping("/get/myReview")
    public List<MyReviewListDTO> getMyReview(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getUserId();
        return reviewService.getReviewsByClient(userId);
    }

    // 리뷰 삭제
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<Review> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    // 리뷰 id 해당하는 리뷰 찾기
    @GetMapping("/get/{reviewId}")
    public ReviewDTO getReviewDetail(@PathVariable Long reviewId) {
       Review review = reviewRepository.findById(reviewId)
               .orElseThrow(() -> new RuntimeException("review not found"));
        return new ReviewDTO(
                review.getId(),
                review.getDetective().getDetectiveId(),  // 탐정 ID
                review.getRating(),
                review.getContent(),
                review.getEstimate().getEstimateId()
        );
    }
}

