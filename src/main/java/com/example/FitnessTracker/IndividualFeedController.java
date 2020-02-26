package com.example.FitnessTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    // Output:  arraylist of posts
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

        // return only posts for the specified user
        return posts;
    }

    //---------------------------------------------------------------
    // Method:  updatePostLikes
    // Purpose: To update the number of likes for a specified post,
    //          specifically by increasing by 1.
    // Inputs:  postID
    // Output:  int representing new number of likes for the post
    //          (-1 if post doesn't exist)
    //---------------------------------------------------------------
    @PutMapping("/individualfeed/like-post")
    public int updatePostLikes(@RequestParam int postID) {

        int likes = -1;

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

        // if the post exists
        if(post != null) {
            // increment the number of its likes
            post.setLikes(post.getLikes()+1);
            // update the post entry in the database with the new number of likes
            postRepository.save(post);
            // get the new number of likes
            likes = post.getLikes();
        }

        return likes;
    }
}
