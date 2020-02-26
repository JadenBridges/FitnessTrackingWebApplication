package com.example.FitnessTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class IndividualFeedController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ActivityRepository activityRepository;

    //---------------------------------------------------------------
    // Method:  getPosts
    // Purpose: To return all posts in a user's individual feed.
    // Inputs:  userID
    // Output:  array of posts
    //---------------------------------------------------------------
    @GetMapping("/individualfeed/get")
    public ArrayList<Post> getPosts(@RequestParam int userID) {

        // get all posts
        ArrayList<Post> posts = (ArrayList<Post>)postRepository.findAll();

        // remove posts of other users
        for(Post post : posts) {
            if(post.getActivity().getUserID().getUserID() != userID) {
                posts.remove(post);
            }
        }

        return posts;
    }
}
