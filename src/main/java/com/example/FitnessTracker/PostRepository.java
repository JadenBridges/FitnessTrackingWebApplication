package com.example.FitnessTracker;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface PostRepository extends CrudRepository<Post, Integer> {

    Post findByActivityID(int activityid);
    ArrayList<Post> findAllByUserID(int userid);
}
