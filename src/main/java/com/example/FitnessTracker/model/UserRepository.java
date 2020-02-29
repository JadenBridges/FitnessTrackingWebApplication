package com.example.FitnessTracker.model;

import com.example.FitnessTracker.model.User;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
public interface UserRepository extends CrudRepository<User, Integer> {}
