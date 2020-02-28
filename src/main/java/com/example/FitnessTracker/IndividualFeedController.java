package com.example.FitnessTracker;

import org.hibernate.dialect.Database;
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
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    private DatabaseUtility databaseUtility = new DatabaseUtility();

    //---------------------------------------------------------------
    // Method:  getPosts
    // Purpose: To return all posts in a user's individual feed.
    // Inputs:  userID
    // Output:  arraylist of posts
    //---------------------------------------------------------------
    @GetMapping("/individualfeed/get")
    public ArrayList<PostWithComments> getPosts(@RequestParam int userID) {

        ArrayList<PostWithComments> posts_with_comments = new ArrayList<>();

        // get all posts
        ArrayList<Post> posts = (ArrayList<Post>)postRepository.findAll();
        
        // remove posts of other users
        posts.removeIf(post -> post.getActivity().getUserID() != userID);

        // get all comments
        ArrayList<Comment> comments = (ArrayList<Comment>)commentRepository.findAll();

        // attach comments to each post
        for(Post post : posts) {
            ArrayList<CommentDTO> post_comments = new ArrayList<>();
            // for each comment
            for(Comment comment : comments) {
                // if comment matches post, keep it
                if(comment.getPostID() == post.getPostID()) {
                    // get UserDTO for the comment
                    User user = userRepository.findById(comment.getUserID()).get();
                    UserDTO userDTO = new UserDTO(user.getUserID(), user.getUsername());
                    post_comments.add(new CommentDTO(userDTO, comment.getPostID(), comment.getMessage()));
                }
            }

            posts_with_comments.add(new PostWithComments(post, post_comments));
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

        int likes = -1;

        Post post = postRepository.findById(postID).get();

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

    //---------------------------------------------------------------
    // Method:  createPostComment
    // Purpose: To add a comment to the specified post.
    // Inputs:  postID and new comment
    // Output:  void
    //---------------------------------------------------------------
    @PostMapping("/individualfeed/comment-post")
    public void createPostComment(@RequestBody Comment comment) {

        commentRepository.save(comment);

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

        Post post = postRepository.findById(postID).get();

        // if the post exists and the userID matches that of the userID passed in
        if((post != null) && (post.getActivity().getUserID() == userID)) {
            // delete the post
            postRepository.delete(post);
            is_deleted = 1;
        }

        return is_deleted;
    }
}
