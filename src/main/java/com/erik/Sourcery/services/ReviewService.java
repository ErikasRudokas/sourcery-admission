package com.erik.Sourcery.services;

import com.erik.Sourcery.domain.entities.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getReviews();

    Review createReview(Review review);
}
