package com.elvisespinoza.dinnerreview.controller;

import com.elvisespinoza.dinnerreview.model.Restaurant;
import com.elvisespinoza.dinnerreview.model.Review;
import com.elvisespinoza.dinnerreview.model.ReviewStatus;
import com.elvisespinoza.dinnerreview.model.User;
import com.elvisespinoza.dinnerreview.repository.RestaurantRepository;
import com.elvisespinoza.dinnerreview.repository.ReviewRepository;
import com.elvisespinoza.dinnerreview.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequestMapping("/reviews")
@RestController
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public ReviewController(ReviewRepository reviewRepository,
                            UserRepository userRepository,
                            RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public void addUserReview(@RequestBody Review review) {
        verifyUserReview(review); // make sure review is valid
        Optional<Restaurant> optionalRestaurant =
                restaurantRepository.findById(review.getRestaurant()); // make sure restaurant is found
        if(!optionalRestaurant.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Restaurant was not found.");
        }
        review.setReviewStatus(ReviewStatus.CONSIDERING); // when found, set Restaurant as "CONSIDERING"
        reviewRepository.save(review);
    }

    private void verifyUserReview(Review review) {
        if(review.getSubmitted() == null || review.getRestaurant() == null
        || (review.getPeanutScore() == null && review.getDairyScore() == null && review.getEggScore() == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Check to see none of the fields are left empty.");
        }

        Optional<User> optionalUser = userRepository.findUserByDisplayName(review.getSubmitted());
        if(!optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "User does not exist.");
        }
    }
}
