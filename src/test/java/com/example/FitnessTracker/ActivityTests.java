package com.example.FitnessTracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ActivityTests {

	private final ActivityController activityController;
	private final UserRepository userRepository;
	private final ActivityRepository activityRepository;
	private final PostRepository postRepository;
	private final SummaryRepository summaryRepository;

	@Autowired
	public ActivityTests(ActivityController ac, UserRepository ur, ActivityRepository ar, PostRepository pr, SummaryRepository sr){
		activityController = ac;
		userRepository = ur;
		activityRepository = ar;
		postRepository = pr;
		summaryRepository = sr;
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

		userRepository.delete(u);
		postRepository.delete(postRepository.findByActivityID(testActivity.getActivityID()));
		activityRepository.delete(testActivity);
	}

	@Test
	void testCreateActivity2() {
		Activity testActivity = new Activity(-1, "MyTitle", "MyDescription", 3.1, 0, 16, 5);
		activityController.createActivity(testActivity);

		List<Activity> activitiesBefore = (List) activityRepository.findAll();
		activityController.createActivity(testActivity);
		List<Activity> activitiesAfter = (List) activityRepository.findAll();

		//assert activity not added
		Assertions.assertEquals(activitiesBefore.size(), activitiesAfter.size());
	}

	@Test
	void testCreateActivity3() {
		Activity testActivity = new Activity(1, "MyTitle", "MyDescription", -1, 0, 0, 0);
		activityController.createActivity(testActivity);

		List<Activity> activitiesBefore = (List) activityRepository.findAll();
		activityController.createActivity(testActivity);
		List<Activity> activitiesAfter = (List) activityRepository.findAll();

		//assert activity not added
		Assertions.assertEquals(activitiesBefore.size(), activitiesAfter.size());
	}

	@Test
	void testUpdateActivity1() {
		User u = new User("test", "pass");
		userRepository.save(u);

		Activity testActivity = new Activity(u.getUserID(), "MyTitle", "MyDescription", 3.1, 0, 16, 5);
		activityController.createActivity(testActivity);

		testActivity.setTitle("MyNewTitle");
		testActivity.setDescription("MyNewDescription");

		activityController.updateActivity(testActivity.getActivityID(), testActivity);

		Assertions.assertEquals(testActivity.getUserID(), activityRepository.findById(testActivity.getActivityID()).get().getUserID());
		Assertions.assertEquals(testActivity.getTitle(), activityRepository.findById(testActivity.getActivityID()).get().getTitle());
		Assertions.assertEquals(testActivity.getDescription(), activityRepository.findById(testActivity.getActivityID()).get().getDescription());
		Assertions.assertEquals(testActivity.getDistance(), activityRepository.findById(testActivity.getActivityID()).get().getDistance());
		Assertions.assertEquals(testActivity.getHours(), activityRepository.findById(testActivity.getActivityID()).get().getHours());
		Assertions.assertEquals(testActivity.getMinutes(), activityRepository.findById(testActivity.getActivityID()).get().getMinutes());
		Assertions.assertEquals(testActivity.getSeconds(), activityRepository.findById(testActivity.getActivityID()).get().getSeconds());

		userRepository.delete(u);
		postRepository.delete(postRepository.findByActivityID(testActivity.getActivityID()));
		activityRepository.delete(testActivity);
	}

	@Test
	void testUpdateActivity2() {
		User u = new User("test", "pass");
		userRepository.save(u);

		Activity testActivity = new Activity(u.getUserID(), "MyTitle", "MyDescription", 3.1, 0, 16, 5);
		activityController.createActivity(testActivity);

		testActivity.setTitle("MyNewTitle");
		testActivity.setDescription("MyNewDescription");

		Assertions.assertEquals(-1, activityController.updateActivity(-1, testActivity));

		userRepository.delete(u);
		postRepository.delete(postRepository.findByActivityID(testActivity.getActivityID()));
		activityRepository.delete(testActivity);
	}

	@Test
	void testUpdateActivity3() {
		User u = new User("test", "pass");
		userRepository.save(u);

		Activity testActivity = new Activity(u.getUserID(), "MyTitle", "MyDescription", 3.1, 0, 16, 5);
		activityController.createActivity(testActivity);

		testActivity.setDistance(-1);
		testActivity.setHours(-1);

		Assertions.assertEquals(-1, activityController.updateActivity(-1, testActivity));

		userRepository.delete(u);
		postRepository.delete(postRepository.findByActivityID(testActivity.getActivityID()));
		activityRepository.delete(testActivity);
	}

//	@Test
//	void testDeleteActivity1() {
//		User u = new User("test", "pass");
//		userRepository.save(u);
//
//		Activity testActivity = new Activity(u.getUserID(), "MyTitle", "MyDescription", 3.1, 0, 16, 5);
//		activityController.createActivity(testActivity);
//
//		Assertions.assertTrue(activityRepository.findById(testActivity.getActivityID()).isPresent());
//		activityController.deleteActivity(testActivity.getActivityID());
//		Assertions.assertFalse(activityRepository.findById(testActivity.getActivityID()).isPresent());
//
//		userRepository.delete(u);
//		postRepository.delete(postRepository.findByActivityID(testActivity.getActivityID()));
//		activityRepository.delete(testActivity);
//	}
//
//	@Test
//	void testDeleteActivity2() {
//		User u = new User("test", "pass");
//		userRepository.save(u);
//
//		Activity testActivity = new Activity(u.getUserID(), "MyTitle", "MyDescription", 3.1, 0, 16, 5);
//		activityController.createActivity(testActivity);
//
//		Assertions.assertTrue(activityRepository.findById(testActivity.getActivityID()).isPresent());
//		activityController.deleteActivity(testActivity.getActivityID());
//		Assertions.assertFalse(activityRepository.findById(testActivity.getActivityID()).isPresent());
//
//		userRepository.delete(u);
//		postRepository.delete(postRepository.findByActivityID(testActivity.getActivityID()));
//		activityRepository.delete(testActivity);
//	}
}
