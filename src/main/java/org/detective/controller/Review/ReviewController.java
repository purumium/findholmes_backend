package org.detective.controller.Review;

import org.detective.dto.ReviewDTO;
import org.detective.entity.Client;
import org.detective.entity.Detective;
import org.detective.entity.Review;
import org.detective.entity.User;
import org.detective.repository.ClientRepository;
import org.detective.repository.DetectiveRepository;
import org.detective.repository.ReviewRepository;
import org.detective.repository.UserRepository;
import org.detective.services.Review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

            Review review = new Review();
            review.setClient(client);
            review.setDetective(detective);
            review.setRating(reviewDTO.getRating());
            review.setContent(reviewDTO.getContent());
            reviewRepository.save(review);

            return ResponseEntity.ok("Registration successful!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateReview(@RequestBody ReviewDTO reviewDTO) {
        Optional<Review> review = reviewRepository.findById(reviewDTO.getId());

        if (review.isPresent()) {
            Review foundReview = review.get();
            // review 객체에 대한 작업 수행
            foundReview.setContent(reviewDTO.getContent());
            foundReview.setRating(reviewDTO.getRating());
            reviewRepository.save(foundReview);
            return ResponseEntity.ok("update successful!");
        } else {
            return ResponseEntity.status(500).body("cant find");
        }
    }

    @GetMapping("/get/{id}")
    public List<ReviewDTO> getReview(@PathVariable Long id) {
        Detective detective = detectiveRepository.findByDetectiveId(id);
        return reviewService.getReviewsByDetective(detective);
    }
}

