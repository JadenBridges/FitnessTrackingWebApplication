package com.example.FitnessTracker;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
    ArrayList<Comment> findAllByPostID(int postid);
}
