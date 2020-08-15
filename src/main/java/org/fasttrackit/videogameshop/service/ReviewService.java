package org.fasttrackit.videogameshop.service;


import org.fasttrackit.videogameshop.domain.Review;
import org.fasttrackit.videogameshop.persistance.ReviewRepository;
import org.fasttrackit.videogameshop.transfer.review.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Page<ReviewResponse> getReviews(long productId, Pageable pageable) {
        final Page<Review> page = reviewRepository.findByProductId(productId, pageable);

        List<ReviewResponse> reviewDtos = new ArrayList<>()

        for (Review review : page.getContent()) {
            ReviewResponse reviewResponse = mapReviewResponse(review);

            reviewDtos.add(reviewResponse);
        }
        return new PageImpl<>(reviewDtos, pageable, page.getTotalElements());
    }

    private ReviewResponse mapReviewResponse(Review review) {
        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.setId(review.getId());
        reviewResponse.setContent(review.getContent());

        return reviewResponse;
    }
}
