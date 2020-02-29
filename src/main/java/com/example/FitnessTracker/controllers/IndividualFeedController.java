package com.example.FitnessTracker.controllers;

import com.example.FitnessTracker.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@RestController
public class IndividualFeedController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    SummaryController summaryController;


    //---------------------------------------------------------------
    // Method:  getPosts
    // Purpose: To return all posts in a user's individual feed.
    // Inputs:  userID
    // Output:  arraylist of posts
    //---------------------------------------------------------------
    @GetMapping("/individualfeed/get")
    public ArrayList<PostWithComments> getPosts(@RequestParam int userID) {

        // userID must be >= 1
        if(userID < 1) {
            return null;
        }

        ArrayList<PostWithComments> posts_with_comments = new ArrayList<>();

        // get all posts for specified user
        ArrayList<Post> posts = postRepository.findAllByUserID(userID);

        // get all comments for the posts and attach them
        for(Post post : posts) {
            // get all comments for a post
            ArrayList<Comment> post_comments = commentRepository.findAllByPostID(post.getPostID());
            ArrayList<CommentDTO> post_comments_dto = new ArrayList<>();
            // for each post comment, create a CommentDTO
            for(Comment comment : post_comments) {
                User user = userRepository.findById(comment.getUserID()).get();
                UserDTO userDTO = new UserDTO(user.getUserID(), user.getUsername());
                post_comments_dto.add(new CommentDTO(userDTO, comment.getPostID(), comment.getMessage()));
            }
            // add to post_with_comment
            posts_with_comments.add(new PostWithComments(post, post_comments_dto));
        }

        // return only posts for the specified user
        return posts_with_comments;
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

        if(postID < 1) {
            return -2;
        }

        Post post;

        try {
            post = postRepository.findById(postID).get();
        } catch(NoSuchElementException e) {
            return -1;
        }

        // post exists
        // increment the number of its likes
        post.setLikes(post.getLikes()+1);
        // update the post entry in the database with the new number of likes
        postRepository.save(post);
        // get the new number of likes
        return post.getLikes();
    }

    //---------------------------------------------------------------
    // Method:  createPostComment
    // Purpose: To add a comment to the specified post.
    // Inputs:  postID and new comment
    // Output:  int
    //---------------------------------------------------------------
    @PostMapping("/individualfeed/comment-post")
    public int createPostComment(@RequestBody Comment comment) {
        if(comment.getPostID() < 0 || comment.getUserID() < 0) {
            return -1;
        }

        commentRepository.save(comment);

        return 0;
    }

    //---------------------------------------------------------------
    // Method:  createPostComment
    // Purpose: To delete a post from an individual feed if the
    //          userID passed in matches that of the post.
    // Inputs:  postID and userID
    // Output:  int (1 for successful delete, 0 for failure to delete)
    //---------------------------------------------------------------
    @DeleteMapping("/individualfeed/delete-post")
    public int deletePost(@RequestParam int postID, @RequestParam int userID) {

        int is_deleted = 0;

        Post post;

        try {
            post = postRepository.findById(postID).get();
        } catch(NoSuchElementException e) {
            return -1;
        }

        // if the post exists and the userID matches that of the userID passed in
        if(post.getActivity().getUserID() == userID) {
            // delete the post
            int activityID = postRepository.findById(postID).get().getActivity().getActivityID();
            summaryController.removeSummary(activityRepository.findById(activityID).get());
            postRepository.delete(post);
            activityRepository.deleteById(activityID);
            is_deleted = 1;
        }

        return is_deleted;
    }
}
