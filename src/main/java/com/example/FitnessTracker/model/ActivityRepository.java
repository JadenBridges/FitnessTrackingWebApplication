package com.example.FitnessTracker.model;

import com.example.FitnessTracker.model.Activity;
import org.springframework.data.repository.CrudRepository;

public interface ActivityRepository extends CrudRepository<Activity, Integer> {
}
