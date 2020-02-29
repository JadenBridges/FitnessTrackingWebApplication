package com.example.FitnessTracker.model;

import com.example.FitnessTracker.model.Summary;
import org.springframework.data.repository.CrudRepository;

public interface SummaryRepository extends CrudRepository<Summary, Integer> {
    Summary findByUserID(int userid);
}
