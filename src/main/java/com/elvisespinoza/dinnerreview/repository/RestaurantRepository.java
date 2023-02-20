package com.elvisespinoza.dinnerreview.repository;

import com.elvisespinoza.dinnerreview.model.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    // Should a restaurant with the same name and with the same zip code already exist, I will see a failure.
    Optional<Restaurant> findRestaurantByNameAndZipcode(String name, String zipcode);
}
