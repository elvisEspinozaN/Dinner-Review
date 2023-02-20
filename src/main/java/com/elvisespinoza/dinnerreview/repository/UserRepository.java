package com.elvisespinoza.dinnerreview.repository;

import com.elvisespinoza.dinnerreview.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    // I want to verify that the user exists, based on the user display name associated with the dining review.
    Optional<User> findUserByDisplayName(String displayName);
}
