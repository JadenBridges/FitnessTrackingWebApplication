package com.example.FitnessTracker;

import com.example.FitnessTracker.model.Comment;import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface PostRepository extends CrudRepository<Comment.Post, Integer> {

    Comment.Post findByActivityID(int activityid);
    ArrayList<Comment.Post> findAllByUserID(int userid);
}
