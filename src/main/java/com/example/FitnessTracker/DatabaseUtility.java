package com.example.FitnessTracker;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class DatabaseUtility {

    @Autowired
    private PostRepository postRepository;

    //---------------------------------------------------------------
    // Method:  getPostById
    // Purpose: To find and return a post from the database by its
    //          postID.
    // Inputs:  postID
    // Output:  Post
    //---------------------------------------------------------------
    public Post getPostById(int postID) {
        // create a list of all the postIDs we're looking for
        // in this case, only one is the postID passed in
        List<Integer> postIds = Arrays.asList(postID);

        // get Post with the specified ID inside iterable
        Iterable<Post> posts = postRepository.findAllById(postIds);

        Post post = null;

        // pick the one post from the iterable
        for(Post temp_post : posts) {
            post = temp_post;
            break;
        }

        return post;
    }

}
