package com.example.FitnessTracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class FitnessTrackerApplicationTests {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ActivityRepository activityRepository;
	@Autowired
	private PostRepository postRepository;

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
	void testGetIndividualFeedPosts_happyPath() {

        // Arrange

        // Act

        // Assert

	}

	@Test
	void testGetIndividualFeedPosts_sadPath1() {

		// Arrange

		// Act

		// Assert

	}

	@Test
	void testGetIndividualFeedPosts_sadPath2() {

		// Arrange

		// Act

		// Assert

	}

    // PUT | /individualfeed/like-post | updatePostLikes(int)

    @Test
    void testUpdatePostLikes_happyPath() {

        // Arrange

        // Act

        // Assert

    }

    @Test
    void testUpdatePostLikes_sadPath1() {

        // Arrange

        // Act

        // Assert

    }

    @Test
    void testUpdatePostLikes_sadPath2() {

        // Arrange

        // Act

        // Assert

    }

    // POST | /individualfeed/comment-post | createPostComment(Comment)

    @Test
    void testCreatePostComment_happyPath() {

        // Arrange

        // Act

        // Assert

    }

    @Test
    void testCreatePostComment_sadPath1() {

        // Arrange

        // Act

        // Assert

    }

    @Test
    void testCreatePostComment_sadPath2() {

        // Arrange

        // Act

        // Assert

    }

    // DELETE | /individualfeed/delete-post | deletePost(int,int)

    @Test
    void testDeletePost_happyPath() {

        // Arrange

        // Act

        // Assert

    }

    @Test
    void testDeletePost_sadPath1() {

        // Arrange

        // Act

        // Assert

    }

    @Test
    void testDeletePost_sadPath2() {

        // Arrange

        // Act

        // Assert

    }

}
