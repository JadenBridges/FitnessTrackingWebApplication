package com.example.FitnessTracker;

import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Integer> {

    Post findByActivityID(int activityid);
}
