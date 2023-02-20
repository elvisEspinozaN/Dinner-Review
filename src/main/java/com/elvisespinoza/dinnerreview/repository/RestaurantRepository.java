package com.elvisespinoza.dinnerreview.repository;

import com.elvisespinoza.dinnerreview.model.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    // Should a restaurant with the same name and with the same zip code already exist, I will see a failure.
    Optional<Restaurant> findRestaurantByNameAndZipcode(String name, String zipcode);
    /*
    I want to fetch restaurants that match a given zip code
    and that also have at least one user-submitted score for a given allergy.
    I want to see them sorted in descending order.
     */
    List<Restaurant> findRestaurantByZipcodeAndPeanutScoreNotNullOrderByPeanutScore(String zipcode);
    List<Restaurant> findRestaurantByZipcodeAndEggScoreNotNullOrderByEggScore(String zipcode);
    List<Restaurant> findRestaurantByZipcodeAndDairyScoreNotNullOrderByDairyScore(String zipcode);
}
