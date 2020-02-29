package com.example.FitnessTracker;

import com.example.FitnessTracker.controllers.IndividualFeedController;
import com.example.FitnessTracker.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class IndividualFeedTests {

    private final IndividualFeedController individualFeedController;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public IndividualFeedTests(IndividualFeedController ifc, UserRepository ur, ActivityRepository ar, PostRepository pr, CommentRepository cr){
        individualFeedController = ifc;
        userRepository = ur;
        activityRepository = ar;
        postRepository = pr;
        commentRepository = cr;
    }


    //-------------------------------------------------------------------------
    // Controller: IndividualFeedController
    // Endpoints:
    //		  VERB | URL                          | METHOD
    //      -------+------------------------------+--------------
    //		   GET | /individualfeed/get          | getPosts(int)
    //		   PUT | /individualfeed/like-post    | updatePostLikes(int)
    //		  POST | /individualfeed/comment-post | createPostComment(Comment)
    //		DELETE | /individualfeed/delete-post  | deletePost(int,int)
    //
    //-------------------------------------------------------------------------

    // GET | /individualfeed/get | getPosts(int)

    @Test
    void testGetIndividualFeedPosts_happyPath1() {

        // Arrange
        User user = new User("test", "pass");
        userRepository.save(user);
        Activity activity = new Activity(user.getUserID(), "MyTitle", "MyDescription", 3.1, 0, 16, 5);
        activityRepository.save(activity);
        Post post = new Post(activity, 10);
        postRepository.save(post);
        Comment comment = new Comment(post.getPostID(), user.getUserID(), "MyCommentMessage");
        commentRepository.save(comment);

        // Act
        ArrayList<PostWithComments> posts_with_comments = individualFeedController.getPosts(user.getUserID());

        // Assert
        Assertions.assertEquals(post.getPostID(), posts_with_comments.get(posts_with_comments.size()-1).getPost().getPostID());
        Assertions.assertEquals(post.getLikes(), posts_with_comments.get(posts_with_comments.size()-1).getPost().getLikes());
        Assertions.assertEquals(post.getActivity().getUserID(), posts_with_comments.get(posts_with_comments.size()-1).getPost().getActivity().getUserID());
        Assertions.assertEquals(post.getActivity().getDistance(), posts_with_comments.get(posts_with_comments.size()-1).getPost().getActivity().getDistance());
        Assertions.assertEquals(post.getActivity().getHours(), posts_with_comments.get(posts_with_comments.size()-1).getPost().getActivity().getHours());
        Assertions.assertEquals(post.getActivity().getMinutes(), posts_with_comments.get(posts_with_comments.size()-1).getPost().getActivity().getMinutes());
        Assertions.assertEquals(post.getActivity().getSeconds(), posts_with_comments.get(posts_with_comments.size()-1).getPost().getActivity().getSeconds());
        Assertions.assertEquals(post.getActivity().getDescription(), posts_with_comments.get(posts_with_comments.size()-1).getPost().getActivity().getDescription());
        Assertions.assertEquals(post.getActivity().getTitle(), posts_with_comments.get(posts_with_comments.size()-1).getPost().getActivity().getTitle());
        Assertions.assertEquals(comment.getPostID(), posts_with_comments.get(0).getComments().get(0).getPostID());
        Assertions.assertEquals(comment.getUserID(), posts_with_comments.get(0).getComments().get(0).getUserDTO().getUserID());
        Assertions.assertEquals(comment.getMessage(), posts_with_comments.get(0).getComments().get(0).getMessage());
    }

    @Test
    void testGetIndividualFeedPosts_happyPath2() {

        // Arrange
        User user = new User("test", "pass");
        userRepository.save(user);
        Activity activity = new Activity(user.getUserID(), "MyTitle", "MyDescription", 3.1, 0, 16, 5);
        activityRepository.save(activity);
        Post post = new Post(activity, 10);
        postRepository.save(post);
        Comment comment = new Comment(post.getPostID(), user.getUserID(), "MyCommentMessage");
        commentRepository.save(comment);

        // Act
        ArrayList<PostWithComments> posts_with_comments = individualFeedController.getPosts(1000);

        // Assert
        Assertions.assertEquals(0, posts_with_comments.size());
    }

    @Test
    void testGetIndividualFeedPosts_sadPath1() {

        // Arrange
        User user = new User("test", "pass");
        userRepository.save(user);
        Activity activity = new Activity(user.getUserID(), "MyTitle", "MyDescription", 3.1, 0, 16, 5);
        activityRepository.save(activity);
        Post post = new Post(activity, 10);
        postRepository.save(post);
        Comment comment = new Comment(post.getPostID(), user.getUserID(), "MyCommentMessage");
        commentRepository.save(comment);

        // Act
        ArrayList<PostWithComments> posts_with_comments = individualFeedController.getPosts(-1);

        // Assert
        Assertions.assertNull(posts_with_comments);
    }

    // PUT | /individualfeed/like-post | updatePostLikes(int)

    @Test
    void testUpdatePostLikes_happyPath() {

        // Arrange
        User user = new User("test", "pass");
        userRepository.save(user);
        Activity activity = new Activity(user.getUserID(), "MyTitle", "MyDescription", 3.1, 0, 16, 5);
        activityRepository.save(activity);
        Post post = new Post(activity, 1);
        postRepository.save(post);

        // Act
        int likes = individualFeedController.updatePostLikes(post.getPostID());

        // Assert
        Assertions.assertEquals(2, likes);

    }

    @Test
    void testUpdatePostLikes_sadPath1() {

        // Arrange
        User user = new User("test", "pass");
        userRepository.save(user);
        Activity activity = new Activity(user.getUserID(), "MyTitle", "MyDescription", 3.1, 0, 16, 5);
        activityRepository.save(activity);
        Post post = new Post(activity, 1);
        postRepository.save(post);

        // Act
        int likes = individualFeedController.updatePostLikes(1000);

        // Assert
        Assertions.assertEquals(-1, likes);

    }

    @Test
    void testUpdatePostLikes_sadPath2() {

        // Arrange
        User user = new User("test", "pass");
        userRepository.save(user);
        Activity activity = new Activity(user.getUserID(), "MyTitle", "MyDescription", 3.1, 0, 16, 5);
        activityRepository.save(activity);
        Post post = new Post(activity, 1);
        postRepository.save(post);

        // Act
        int likes = individualFeedController.updatePostLikes(-1);

        // Assert
        Assertions.assertEquals(-2, likes);

    }

    // POST | /individualfeed/comment-post | createPostComment(Comment)

    @Test
    void testCreatePostComment_happyPath() {

        // Arrange
        User user = new User("test", "pass");
        userRepository.save(user);
        Activity activity = new Activity(user.getUserID(), "MyTitle", "MyDescription", 3.1, 0, 16, 5);
        activityRepository.save(activity);
        Post post = new Post(activity, 1);
        postRepository.save(post);
        Comment comment = new Comment(post.getPostID(), user.getUserID(), "MyCommentMessage");

        // Act
        int result = individualFeedController.createPostComment(comment);

        // Assert
        Assertions.assertEquals(0, result);

    }

    @Test
    void testCreatePostComment_sadPath1() {

        // Arrange
        User user = new User("test", "pass");
        userRepository.save(user);
        Activity activity = new Activity(user.getUserID(), "MyTitle", "MyDescription", 3.1, 0, 16, 5);
        activityRepository.save(activity);
        Post post = new Post(activity, 1);
        postRepository.save(post);
        Comment comment = new Comment(-1, user.getUserID(), "MyCommentMessage");

        // Act
        int result = individualFeedController.createPostComment(comment);

        // Assert
        Assertions.assertEquals(-1, result);

    }

    @Test
    void testCreatePostComment_sadPath2() {

        // Arrange
        User user = new User("test", "pass");
        userRepository.save(user);
        Activity activity = new Activity(user.getUserID(), "MyTitle", "MyDescription", 3.1, 0, 16, 5);
        activityRepository.save(activity);
        Post post = new Post(activity, 1);
        postRepository.save(post);
        Comment comment = new Comment(post.getPostID(), -1, "MyCommentMessage");

        // Act
        int result = individualFeedController.createPostComment(comment);

        // Assert
        Assertions.assertEquals(-1, result);

    }

    // DELETE | /individualfeed/delete-post | deletePost(int,int)

    @Test
    void testDeletePost_happyPath() {

        // Arrange
        User user = new User("test", "pass");
        userRepository.save(user);
        Activity activity = new Activity(user.getUserID(), "MyTitle", "MyDescription", 3.1, 0, 16, 5);
        activityRepository.save(activity);
        Post post = new Post(activity, 1);
        postRepository.save(post);

        // Act
        int result = individualFeedController.deletePost(post.getPostID(), user.getUserID());

        // Assert
        Assertions.assertEquals(1, result);

    }

    @Test
    void testDeletePost_sadPath1() {

        // Arrange
        User user = new User("test", "pass");
        userRepository.save(user);
        Activity activity = new Activity(user.getUserID(), "MyTitle", "MyDescription", 3.1, 0, 16, 5);
        activityRepository.save(activity);
        Post post = new Post(activity, 1);
        postRepository.save(post);

        // Act
        int result = individualFeedController.deletePost(-1, user.getUserID());

        // Assert
        Assertions.assertEquals(-1, result);

    }

    @Test
    void testDeletePost_sadPath2() {

        // Arrange
        User user = new User("test", "pass");
        userRepository.save(user);
        Activity activity = new Activity(user.getUserID(), "MyTitle", "MyDescription", 3.1, 0, 16, 5);
        activityRepository.save(activity);
        Post post = new Post(activity, 1);
        postRepository.save(post);

        // Act
        int result = individualFeedController.deletePost(post.getPostID(), -1);

        // Assert
        Assertions.assertEquals(0, result);

    }

}
