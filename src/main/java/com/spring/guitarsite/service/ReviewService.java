package com.spring.guitarsite.service;

import com.spring.guitarsite.dto.ReviewFormDto;
import com.spring.guitarsite.model.Review;
import com.spring.guitarsite.model.User;

import java.util.List;

public interface ReviewService {

    void addReview(Long guitarId, User user, ReviewFormDto reviewFormDto);


    List<Review> getReviewsForGuitar(Long guitarId);


}
