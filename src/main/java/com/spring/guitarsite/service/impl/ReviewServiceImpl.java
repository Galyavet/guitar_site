package com.spring.guitarsite.service.impl;

import com.spring.guitarsite.dto.ReviewFormDto;
import com.spring.guitarsite.model.Guitar;
import com.spring.guitarsite.model.Review;
import com.spring.guitarsite.model.User;
import com.spring.guitarsite.repository.GuitarRepository;
import com.spring.guitarsite.repository.ReviewRepository;
import com.spring.guitarsite.repository.UserRepository;
import com.spring.guitarsite.service.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final GuitarRepository guitarRepository;

    @Override
    @Transactional
    public void addReview(Long guitarId, User user, ReviewFormDto reviewFormDto) {
        Guitar guitar = guitarRepository.findById(guitarId)
                .orElseThrow(() -> new EntityNotFoundException("Guitar not found"));

        Review review = new Review();
        review.setGuitar(guitar);
        review.setUser(user);
        review.setRating(reviewFormDto.getRating());
        review.setComment(reviewFormDto.getComment());

        reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsForGuitar(Long guitarId) {
        return reviewRepository.findByGuitarId(guitarId);
    }
}

