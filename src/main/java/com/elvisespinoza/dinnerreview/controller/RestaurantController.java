package com.elvisespinoza.dinnerreview.controller;

import com.elvisespinoza.dinnerreview.model.Restaurant;
import com.elvisespinoza.dinnerreview.repository.RestaurantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;
import java.util.regex.Pattern;

@RequestMapping(value = "/restaurants")
@RestController
public class RestaurantController {
    private final String zipCodeRegex = "\\d{5}"; // use in regular expression operations without the need to compile it first
    private final RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping
    public Iterable<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable Long id) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        if(!optionalRestaurant.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant was not found.");
        }

        return optionalRestaurant.get();
    }

    @GetMapping("/search")
    public Iterable<Restaurant> searchRestaurants(@RequestParam String zipcode, @RequestParam String item) {
        verifyZipcode(zipcode); // valid zipcode
        Iterable<Restaurant> restaurants = Collections.emptyList(); // prevent any NullPointerException

        if(item.equalsIgnoreCase("peanut")) {
            restaurants = restaurantRepository.findRestaurantByZipcodeAndPeanutScoreNotNullOrderByPeanutScore(zipcode);
        } else if (item.equalsIgnoreCase("dairy")) {
            restaurants = restaurantRepository.findRestaurantByZipcodeAndDairyScoreNotNullOrderByDairyScore(zipcode);
        } else if (item.equalsIgnoreCase("egg")) {
            restaurants = restaurantRepository.findRestaurantByZipcodeAndEggScoreNotNullOrderByEggScore(zipcode);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An invalid item was given.");
        }

        return restaurants;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
        verifyNewRestaurant(restaurant);
        return restaurantRepository.save(restaurant);
    }

    private void verifyNewRestaurant(Restaurant restaurant) {
        if(restaurant.getName() == null || restaurant.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Restaurant name must not be empty.");
        }
        verifyZipcode(restaurant.getZipcode());
        Optional<Restaurant> optionalRestaurant =
                restaurantRepository.findRestaurantByNameAndZipcode(restaurant.getName(), restaurant.getZipcode());

        if(optionalRestaurant.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Restaurant with the name " + restaurant.getName()
                            + " and the zip code " + restaurant.getZipcode() + " already exists.");
        }
    }

    private void verifyZipcode(String zipcode) { // checking to see if the zipcode is a valid five-digit number
        Pattern zipcodePattern = Pattern.compile(zipCodeRegex); // Pattern object to see if it matches

        if(!zipcodePattern.matcher(zipcode).matches()) { // if not valid, throw the following exception
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid zip code.");
        }
    }
}
