package com.example.FitnessTracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class GroupTests {

    private final GroupFeedController groupFeedController;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupUserLinkRepository groupUserLinkRepository;
    private final ActivityRepository activityRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public GroupTests(GroupFeedController gfc, GroupRepository gr, UserRepository ur,
                      GroupUserLinkRepository gulr, ActivityRepository ar, PostRepository pr,
                      CommentRepository cr){
        groupFeedController = gfc;
        groupRepository = gr;
        userRepository = ur;
        groupUserLinkRepository = gulr;
        activityRepository = ar;
        postRepository = pr;
        commentRepository = cr;
    }

    @Test
    void testGetPostsHappy(){
        User u1 = new User("Test1", "pwd");
        userRepository.save(u1);
        User u2 = new User("Test2", "pwd");
        userRepository.save(u2);
        User u3 = new User("Test3", "pwd");
        userRepository.save(u3);

        _Group g = new _Group(u1.getUserID());
        groupRepository.save(g);

        GroupUserLink gul = new GroupUserLink(g.getGroupID(), u1.getUserID());
        groupUserLinkRepository.save(gul);

        Activity a1 = new Activity(u1.getUserID(), "t1", "d1", 2.3, 4, 23, 12);
        activityRepository.save(a1);
        Activity a2 = new Activity(u2.getUserID(), "t2", "d2", 2.3, 4, 23, 12);
        activityRepository.save(a2);
        Activity a3 = new Activity(u3.getUserID(), "t3", "d3", 2.3, 4, 23, 12);
        activityRepository.save(a3);

        Post p1 = new Post(a1, 1);
        postRepository.save(p1);
        Post p2 = new Post(a2, 2);
        postRepository.save(p2);
        Post p3 = new Post(a3, 3);
        postRepository.save(p3);

        PostWithComments pwc1 = new PostWithComments(p1, null);
        PostWithComments pwc2 = new PostWithComments(p2, null);
        PostWithComments pwc3 = new PostWithComments(p3, null);

        ArrayList<PostWithComments> posts = groupFeedController.getPosts(g.getGroupID());
        for (PostWithComments pwc : posts){
            Assertions.assertEquals(pwc.getPost().getActivity().getUserID(), a1.getUserID());
            Assertions.assertEquals(pwc.getPost().getActivity().getActivityID(), a1.getActivityID());
            Assertions.assertEquals(pwc.getPost().getActivity().getDistance(), a1.getDistance());
            Assertions.assertEquals(pwc.getPost().getActivity().getHours(), a1.getHours());
            Assertions.assertEquals(pwc.getPost().getActivity().getMinutes(), a1.getMinutes());
            Assertions.assertEquals(pwc.getPost().getActivity().getSeconds(), a1.getSeconds());
            Assertions.assertEquals(pwc.getPost().getActivity().getDescription(), a1.getDescription());
            Assertions.assertEquals(pwc.getPost().getActivity().getTitle(), a1.getTitle());
            Assertions.assertEquals(pwc.getPost().getLikes(), 1);
        }
    }

    @Test
    void testGetPostsSad_1(){
        User u1 = new User("Test1", "pwd");
        userRepository.save(u1);
        User u2 = new User("Test2", "pwd");
        userRepository.save(u2);
        User u3 = new User("Test3", "pwd");
        userRepository.save(u3);

        _Group g = new _Group(u1.getUserID());
        groupRepository.save(g);

        GroupUserLink gul = new GroupUserLink(g.getGroupID(), u1.getUserID());
        groupUserLinkRepository.save(gul);

        Activity a1 = new Activity(u1.getUserID(), "t1", "d1", 2.3, 4, 23, 12);
        activityRepository.save(a1);
        Activity a2 = new Activity(u2.getUserID(), "t2", "d2", 2.3, 4, 23, 12);
        activityRepository.save(a2);
        Activity a3 = new Activity(u3.getUserID(), "t3", "d3", 2.3, 4, 23, 12);
        activityRepository.save(a3);

        Post p1 = new Post(a1, 1);
        postRepository.save(p1);
        Post p2 = new Post(a2, 2);
        postRepository.save(p2);
        Post p3 = new Post(a3, 3);
        postRepository.save(p3);

        PostWithComments pwc1 = new PostWithComments(p1, null);
        PostWithComments pwc2 = new PostWithComments(p2, null);
        PostWithComments pwc3 = new PostWithComments(p3, null);

        ArrayList<PostWithComments> posts = groupFeedController.getPosts(g.getGroupID()+1);
        Assertions.assertNull(posts);
    }

    @Test
    void testGetPostsSad_2(){
        User u1 = new User("Test1", "pwd");
        userRepository.save(u1);
        User u2 = new User("Test2", "pwd");
        userRepository.save(u2);
        User u3 = new User("Test3", "pwd");
        userRepository.save(u3);

        _Group g = new _Group(u1.getUserID());
        groupRepository.save(g);

        Activity a1 = new Activity(u1.getUserID(), "t1", "d1", 2.3, 4, 23, 12);
        activityRepository.save(a1);
        Activity a2 = new Activity(u2.getUserID(), "t2", "d2", 2.3, 4, 23, 12);
        activityRepository.save(a2);
        Activity a3 = new Activity(u3.getUserID(), "t3", "d3", 2.3, 4, 23, 12);
        activityRepository.save(a3);

        Post p1 = new Post(a1, 1);
        postRepository.save(p1);
        Post p2 = new Post(a2, 2);
        postRepository.save(p2);
        Post p3 = new Post(a3, 3);
        postRepository.save(p3);

        PostWithComments pwc1 = new PostWithComments(p1, null);
        PostWithComments pwc2 = new PostWithComments(p2, null);
        PostWithComments pwc3 = new PostWithComments(p3, null);

        ArrayList<PostWithComments> posts = groupFeedController.getPosts(g.getGroupID());
        Assertions.assertTrue(posts.isEmpty());
    }

    @Test
    void testUpdatePostLikesHappy(){
        User u1 = new User("Test1", "pwd");
        userRepository.save(u1);
        Activity a1 = new Activity(u1.getUserID(), "t1", "d1", 2.3, 4, 23, 12);
        activityRepository.save(a1);
        Post p1 = new Post(a1, 1);
        postRepository.save(p1);

        Assertions.assertEquals(groupFeedController.updatePostLikes(p1.getPostID()), 2);
    }

    @Test
    void testUpdatePostLikesSad_1(){
        User u1 = new User("Test1", "pwd");
        userRepository.save(u1);
        Activity a1 = new Activity(u1.getUserID(), "t1", "d1", 2.3, 4, 23, 12);
        activityRepository.save(a1);
        Post p1 = new Post(a1, 1);
        postRepository.save(p1);

        Assertions.assertEquals(groupFeedController.updatePostLikes(237), -1);
    }

    @Test
    void testUpdatePostLikesSad_2(){
        User u1 = new User("Test1", "pwd");
        userRepository.save(u1);
        Activity a1 = new Activity(u1.getUserID(), "t1", "d1", 2.3, 4, 23, 12);
        activityRepository.save(a1);
        Post p1 = new Post(a1, 1);
        postRepository.save(p1);

        Assertions.assertEquals(groupFeedController.updatePostLikes(p1.getPostID()+1), -1);
    }

    @Test
    void testCreatePostCommentHappy(){
        Comment c1 = new Comment(1, 1, "Hello World!");
        commentRepository.save(c1);

        Assertions.assertEquals(groupFeedController.createPostComment(commentRepository.findById(c1.getCommentID()).get()), 0);
    }

    @Test
    void testCreatePostCommentSad_1(){
        Comment c1 = new Comment(-1, 1, "Hello World!");
        commentRepository.save(c1);

        Assertions.assertEquals(groupFeedController.createPostComment(commentRepository.findById(c1.getCommentID()).get()), -1);
    }

    @Test
    void testCreatePostCommentSad_2(){
        Comment c1 = new Comment(1, -1, "Hello World!");
        commentRepository.save(c1);

        Assertions.assertEquals(groupFeedController.createPostComment(commentRepository.findById(c1.getCommentID()).get()), -1);
    }

    @Test
    void testDeletePostHappy(){
        User u1 = new User("Test1", "pwd");
        userRepository.save(u1);
        Activity a1 = new Activity(u1.getUserID(), "t1", "d1", 2.3, 4, 23, 12);
        activityRepository.save(a1);
        Post p1 = new Post(a1, 1);
        postRepository.save(p1);

        Assertions.assertEquals(groupFeedController.deletePost(p1.getPostID(), u1.getUserID()), 1);
    }

    @Test
    void testDeletePostSad_1(){
        User u1 = new User("Test1", "pwd");
        userRepository.save(u1);
        Activity a1 = new Activity(u1.getUserID(), "t1", "d1", 2.3, 4, 23, 12);
        activityRepository.save(a1);
        Post p1 = new Post(a1, 1);
        postRepository.save(p1);

        Assertions.assertEquals(groupFeedController.deletePost(-253, u1.getUserID()), 0);
    }

    @Test
    void testDeletePostSad_2(){
        User u1 = new User("Test1", "pwd");
        userRepository.save(u1);
        Activity a1 = new Activity(u1.getUserID(), "t1", "d1", 2.3, 4, 23, 12);
        activityRepository.save(a1);
        Post p1 = new Post(a1, 1);
        postRepository.save(p1);

        Assertions.assertEquals(groupFeedController.deletePost(p1.getPostID(), -456), 0);
    }

    @Test
    void testAddUserToGroupHappy(){
        User u1 = new User("Test1", "pwd");
        userRepository.save(u1);
        User u2 = new User("Test2", "pwd");
        userRepository.save(u2);
        User u3 = new User("Test3", "pwd");
        userRepository.save(u3);

        _Group g = new _Group(u1.getUserID());
        groupRepository.save(g);

        int gul_id = groupFeedController.addUserToGroup(u2.getUserID(), g.getGroupID());
        Assertions.assertTrue(groupUserLinkRepository.findById(gul_id).isPresent());
    }

    @Test
    void testAddUserToGroupSad_1(){
        User u1 = new User("Test1", "pwd");
        userRepository.save(u1);
        User u2 = new User("Test2", "pwd");
        userRepository.save(u2);
        User u3 = new User("Test3", "pwd");
        userRepository.save(u3);

        _Group g1 = new _Group(u1.getUserID());
        groupRepository.save(g1);

        _Group g2 = new _Group(u2.getUserID());
        groupRepository.save(g2);

        groupFeedController.addUserToGroup(u2.getUserID(), g1.getGroupID());
        int gul_id = groupFeedController.addUserToGroup(u3.getUserID(), g2.getGroupID());
        Assertions.assertFalse(groupUserLinkRepository.findById(gul_id+1).isPresent());
    }

    @Test
    void testAddUserToGroupSad_2(){
        User u1 = new User("Test1", "pwd");
        userRepository.save(u1);
        User u2 = new User("Test2", "pwd");
        userRepository.save(u2);
        User u3 = new User("Test3", "pwd");
        userRepository.save(u3);

        _Group g1 = new _Group(u1.getUserID());
        groupRepository.save(g1);

        int gul_id = groupFeedController.addUserToGroup(u2.getUserID(), g1.getGroupID() + 1);
        Assertions.assertEquals(gul_id, -1);
    }

    @Test
    void testRemoveUserFromGroupHappy(){
        User u1 = new User("Test1", "pwd");
        userRepository.save(u1);
        User u2 = new User("Test2", "pwd");
        userRepository.save(u2);
        User u3 = new User("Test3", "pwd");
        userRepository.save(u3);

        _Group g1 = new _Group(u1.getUserID());
        groupRepository.save(g1);

        groupFeedController.addUserToGroup(u2.getUserID(), g1.getGroupID());
        int retval = groupFeedController.removeUserFromGroup(u2.getUserID(), g1.getGroupID());
        Assertions.assertEquals(retval, 1);
    }

    @Test
    void testRemoveUserFromGroupSad_1(){
        User u1 = new User("Test1", "pwd");
        userRepository.save(u1);
        User u2 = new User("Test2", "pwd");
        userRepository.save(u2);
        User u3 = new User("Test3", "pwd");
        userRepository.save(u3);

        _Group g1 = new _Group(u1.getUserID());
        groupRepository.save(g1);

        groupFeedController.addUserToGroup(u3.getUserID(), g1.getGroupID());
        int retval = groupFeedController.removeUserFromGroup(u3.getUserID()+1, g1.getGroupID());
        Assertions.assertEquals(retval, 0);
    }

    @Test
    void testRemoveUserFromGroupSad_2(){
        User u1 = new User("Test1", "pwd");
        userRepository.save(u1);
        User u2 = new User("Test2", "pwd");
        userRepository.save(u2);
        User u3 = new User("Test3", "pwd");
        userRepository.save(u3);

        _Group g1 = new _Group(u1.getUserID());
        groupRepository.save(g1);

        groupFeedController.addUserToGroup(u3.getUserID(), g1.getGroupID());
        int retval = groupFeedController.removeUserFromGroup(u3.getUserID(), g1.getGroupID()+1);
        Assertions.assertEquals(retval, 0);
    }

    @Test
    void testCreateGroupHappy(){
        User u1 = new User("Test1", "pwd");
        userRepository.save(u1);
        User u2 = new User("Test2", "pwd");
        userRepository.save(u2);
        User u3 = new User("Test3", "pwd");
        userRepository.save(u3);

        int groupID = groupFeedController.createGroup(u1.getUserID());
        Assertions.assertTrue(groupRepository.findById(groupID).isPresent());
    }

    @Test
    void testCreateGroupSad_1(){
        User u1 = new User("Test1", "pwd");
        userRepository.save(u1);
        User u2 = new User("Test2", "pwd");
        userRepository.save(u2);
        User u3 = new User("Test3", "pwd");
        userRepository.save(u3);

        int groupID = groupFeedController.createGroup(u1.getUserID());
        Assertions.assertFalse(groupRepository.findById(groupID + 1).isPresent());
    }

    @Test
    void testCreateGroupSad_2(){
        User u1 = new User("Test1", "pwd");
        userRepository.save(u1);

        int groupID = groupFeedController.createGroup(u1.getUserID() + 1);
        Assertions.assertEquals(groupID, -1);
    }

}
