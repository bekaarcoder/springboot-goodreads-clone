package com.blitzstriker.goodreads.repositories;

import com.blitzstriker.goodreads.entity.Rating;
import com.blitzstriker.goodreads.entity.RatingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, RatingId> {
}
