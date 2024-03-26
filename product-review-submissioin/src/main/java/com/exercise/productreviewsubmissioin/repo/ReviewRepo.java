package com.exercise.productreviewsubmissioin.repo;

import com.exercise.productreviewsubmissioin.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review, Long> {
}
