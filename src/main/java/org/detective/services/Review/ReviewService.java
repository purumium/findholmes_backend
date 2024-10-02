package org.detective.services.Review;

import org.detective.dto.ReviewDTO;
import org.detective.entity.Detective;
import org.detective.entity.Review;
import org.detective.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<ReviewDTO> getReviewsByDetective(Detective detective) {
        List<Review> reviews =  reviewRepository.findAllByDetective(detective);
        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        for (int i = 0; i < reviews.size(); i++) {
            Review review = reviews.get(i);
            // review 객체에 대해 원하는 작업 수행
            ReviewDTO reviewDTO = new ReviewDTO(0L,0L,0,"");
            reviewDTO.setId(review.getId());
            reviewDTO.setRating(review.getRating());
            reviewDTO.setContent(review.getContent());

            reviewDTOS.add(reviewDTO);

            System.out.println("Review ID: " + review.getId());
            System.out.println("Rating: " + review.getRating());
        }
        return reviewDTOS;
    }
}
