package com.elvisespinoza.dinnerreview.repository;

import com.elvisespinoza.dinnerreview.model.Review;
import com.elvisespinoza.dinnerreview.model.ReviewStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    // I want to get the list of all dining reviews that are pending approval.
    List<Review> findReviewsByRestaurantAndReviewStatus(Long restaurantId, ReviewStatus reviewStatus);
    // I want to fetch the set of all approved dining reviews belonging to this restaurant.
    List<Review> findReviewsByReviewStatus(ReviewStatus reviewStatus);
}
