package com.example.FitnessTracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ActivityTests {

	private final ActivityController activityController;
	private final UserRepository userRepository;
	private final ActivityRepository activityRepository;
	private final PostRepository postRepository;

	@Autowired
	public ActivityTests(ActivityController ac, UserRepository ur, ActivityRepository ar, PostRepository pr){
		activityController = ac;
		userRepository = ur;
		activityRepository = ar;
		postRepository = pr;
	}

	@Test
	void testCreateActivity1() {
		User u = new User("test", "pass");
		userRepository.save(u);

		Activity testActivity = new Activity(u.getUserID(), "MyTitle", "MyDescription", 3.1, 0, 16, 5);
		activityController.createActivity(testActivity);

		Assertions.assertEquals(testActivity.getUserID(), activityRepository.findById(testActivity.getActivityID()).get().getUserID());
		Assertions.assertEquals(testActivity.getTitle(), activityRepository.findById(testActivity.getActivityID()).get().getTitle());
		Assertions.assertEquals(testActivity.getDescription(), activityRepository.findById(testActivity.getActivityID()).get().getDescription());
		Assertions.assertEquals(testActivity.getDistance(), activityRepository.findById(testActivity.getActivityID()).get().getDistance());
		Assertions.assertEquals(testActivity.getHours(), activityRepository.findById(testActivity.getActivityID()).get().getHours());
		Assertions.assertEquals(testActivity.getMinutes(), activityRepository.findById(testActivity.getActivityID()).get().getMinutes());
		Assertions.assertEquals(testActivity.getSeconds(), activityRepository.findById(testActivity.getActivityID()).get().getSeconds());

		Post post = postRepository.findByActivityID(testActivity.getActivityID());
		Assertions.assertEquals(post.getActivity().getUserID(), testActivity.getUserID());
		Assertions.assertEquals(post.getActivity().getTitle(), testActivity.getTitle());
		Assertions.assertEquals(post.getActivity().getDescription(), testActivity.getDescription());
		Assertions.assertEquals(post.getActivity().getDistance(), testActivity.getDistance());
		Assertions.assertEquals(post.getActivity().getHours(), testActivity.getHours());
		Assertions.assertEquals(post.getActivity().getMinutes(), testActivity.getMinutes());
		Assertions.assertEquals(post.getActivity().getSeconds(), testActivity.getSeconds());

		userRepository.delete(u);
		postRepository.delete(post);
		activityRepository.delete(testActivity);
	}

}
