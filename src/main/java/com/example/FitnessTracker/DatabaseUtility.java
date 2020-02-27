package com.example.FitnessTracker;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class DatabaseUtility {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ActivityRepository activityRepository;

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

    //---------------------------------------------------------------
    // Method:  getUserById
    // Purpose: To find and return a user from the database by its
    //          userID.
    // Inputs:  userID
    // Output:  User
    //---------------------------------------------------------------
    public User getUserById(int userID) {
        // create a list of all the userIDs we're looking for
        // in this case, only one is the userID passed in
        List<Integer> userIds = Arrays.asList(userID);

        // get User with the specified ID inside iterable
        Iterable<User> users = userRepository.findAllById(userIds);

        User user = null;

        // pick the one user from the iterable
        for(User temp_user : users) {
            user = temp_user;
            break;
        }

        return user;
    }

    //---------------------------------------------------------------
    // Method:  getGroupById
    // Purpose: To find and return a group from the database by its
    //          groupID.
    // Inputs:  groupID
    // Output:  Group
    //---------------------------------------------------------------
    public _Group getGroupById(int groupID) {
        // create a list of all the groupIDs we're looking for
        // in this case, only one is the groupID passed in
        List<Integer> groupIds = Arrays.asList(groupID);

        // get Group with the specified ID inside iterable
        Iterable<_Group> groups = groupRepository.findAllById(groupIds);

        _Group group = null;

        // pick the one group from the iterable
        for(_Group temp_group : groups) {
            group = temp_group;
            break;
        }

        return group;
    }

    //---------------------------------------------------------------
    // Method:  getCommentById
    // Purpose: To find and return a comment from the database by its
    //          commentID.
    // Inputs:  commentID
    // Output:  Comment
    //---------------------------------------------------------------
    public Comment getCommentById(int commentID) {
        // create a list of all the commentIDs we're looking for
        // in this case, only one is the commentID passed in
        List<Integer> commentIds = Arrays.asList(commentID);

        // get Comment with the specified ID inside iterable
        Iterable<Comment> comments = commentRepository.findAllById(commentIds);

        Comment comment = null;

        // pick the one comment from the iterable
        for(Comment temp_comment : comments) {
            comment = temp_comment;
            break;
        }

        return comment;
    }

    //---------------------------------------------------------------
    // Method:  getActivityById
    // Purpose: To find and return a activity from the database by its
    //          activityID.
    // Inputs:  activityID
    // Output:  Activity
    //---------------------------------------------------------------
    public Activity getActivityById(int activityID) {
        // create a list of all the activityIDs we're looking for
        // in this case, only one is the activityID passed in
        List<Integer> activityIds = Arrays.asList(activityID);

        // get Activity with the specified ID inside iterable
        Iterable<Activity> activities = activityRepository.findAllById(activityIds);

        Activity activity = null;

        // pick the one activity from the iterable
        for(Activity temp_activity : activities) {
            activity = temp_activity;
            break;
        }

        return activity;
    }

//    //---------------------------------------------------------------
//    // Method:  checkUserExists
//    // Purpose: To check the database to see if the requested user
//    //          exists.
//    // Inputs:  userID
//    // Output:  int (1 = user exists, 0 = user does not exist)
//    //---------------------------------------------------------------
//    public int checkUserExists(int userID) {
//        // create a list of all the activityIDs we're looking for
//        // in this case, only one is the activityID passed in
//        List<Integer> activityIds = Arrays.asList(activityID);
//
//        // get Activity with the specified ID inside iterable
//        Iterable<Activity> activities = activityRepository.findAllById(activityIds);
//
//        Activity activity = null;
//
//        // pick the one activity from the iterable
//        for(Activity temp_activity : activities) {
//            activity = temp_activity;
//            break;
//        }
//
//        return activity;
//    }
}
