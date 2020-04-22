package com.example.FitnessTracker.controllers;

import com.example.FitnessTracker.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class GroupFeedController
{
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupUserLinkRepository groupUserLinkRepository;
    @Autowired
    private SummaryController summaryController;

    private int gulid = 0;
    private int gID = 0;

    //---------------------------------------------------------------
    // Method:  getPosts
    // Purpose: To return all posts in a group feed.
    // Inputs:  groupID
    // Output:  arraylist of posts
    //---------------------------------------------------------------
    @GetMapping("/groupfeed/get")
    public ArrayList<PostWithComments> getPosts(@RequestParam(name="groupID") int groupID) {
        // get all posts, groups, and links
        ArrayList<Post> posts = (ArrayList<Post>)postRepository.findAll();
        ArrayList<GroupUserLink> links = (ArrayList<GroupUserLink>)groupUserLinkRepository.findAll();

        ArrayList<PostWithComments> posts_with_comments = new ArrayList<>();

        // get the correct group
        if (!groupRepository.findById(groupID).isPresent()){
            return null;
        }

        _Group correctGroup = groupRepository.findById(groupID).get();

        ArrayList<User> groupUsers = new ArrayList<User>();

        // get users from group
        // find where group ids are in linking table and add to groupUsers
        for(GroupUserLink link : links)
        {
            if(correctGroup.getGroupID() == link.getGroupID()){
                groupUsers.add(userRepository.findById(link.getUserID()).get());
            }
        }
        if (groupUsers == null){
            return null;
        }

        boolean partOfGroup;
        ArrayList<Post> correctPosts = new ArrayList<Post>();

        // remove posts that are not associated with the users of the group
        // for each post, find where user ids are equal
        for (Post post : posts) {
            partOfGroup = false;
            for(User user : groupUsers) {
                if (post.getActivity().getUserID() == user.getUserID()) {
                    correctPosts.add(post);
                    break;
                }
            }
        }

        // get all comments
        ArrayList<Comment> comments = (ArrayList<Comment>)commentRepository.findAll();

        // attach comments to each post
        for(Post post : correctPosts) {
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

        // return only posts for the specified group
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
    @PutMapping("/groupfeed/like-post")
    public int updatePostLikes(@RequestParam(name="postID") int postID) {
        int likes = -1;

        if (postRepository.findById(postID).isPresent()) {
            Post post = postRepository.findById(postID).get();
            // increment the number of its likes
            post.setLikes(post.getLikes() + 1);
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
    // Inputs:  new comment
    // Output:  int (1 for successful delete, 0 for failure to delete)
    //---------------------------------------------------------------
    @PostMapping("/groupfeed/comment-post")
    public int createPostComment(@RequestBody Comment comment) {
        if(comment.getPostID() < 0 || comment.getUserID() < 0)
        {
            return -1;
        }
        commentRepository.save(comment);

        return 0;
    }

    //---------------------------------------------------------------
    // Method:  deletePost
    // Purpose: To delete a post from a group feed if the
    //          userID passed in matches that of the post.
    // Inputs:  postID and userID
    // Output:  int (1 for successful delete, 0 for failure to delete)
    //---------------------------------------------------------------
    @DeleteMapping("/groupfeed/delete-post")
    public int deletePost(@RequestParam(name="postID") int postID, @RequestParam(name="userID") int userID) {
        int is_deleted = 0;

        if(postRepository.findById(postID).isPresent()) {
            Post post = postRepository.findById(postID).get();
            // if the post exists and the userID matches that of the userID passed in
            if (post.getActivity().getUserID() == userID) {
                // delete the post
                int activityID = postRepository.findById(postID).get().getActivity().getActivityID();
                summaryController.removeSummary(activityRepository.findById(activityID).get());
                postRepository.delete(post);
                activityRepository.deleteById(activityID);
                is_deleted = 1;
            }
        }

        return is_deleted;
    }

    //---------------------------------------------------------------
    // Method:  addUserToGroup
    // Purpose: To add a specific user to a specific group
    // Inputs:  userID and groupID
    // Output:  int (1 for successful delete, 0 for failure to delete)
    //---------------------------------------------------------------
    @PostMapping("/groupfeed/adduser")
    public int addUserToGroup(@RequestParam(name="userID") int userID, @RequestParam(name="groupID") int groupID) {
        if (!groupRepository.findById(groupID).isPresent())
        {
            return -1;
        }
        // add user to group
        GroupUserLink gul = new GroupUserLink();
        gul.setGroupID(groupID);
        gul.setUserID(userID);
        groupUserLinkRepository.save(gul);
        return gul.getLinkID();
    }

    //---------------------------------------------------------------
    // Method:  removeUserFromGroup
    // Purpose: To remove a specific user from a specific group
    // Inputs:  userID and groupID
    // Output:  boolean (true for successful delete, false for failure to delete)
    //---------------------------------------------------------------
    @PutMapping("/groupfeed/removeuser")
    public int removeUserFromGroup(@RequestParam(name="userID") int userID, @RequestParam(name="groupID") int groupID) {
        int userDeleted = 0;

        ArrayList<GroupUserLink> links = (ArrayList<GroupUserLink>)groupUserLinkRepository.findAll();

        for(GroupUserLink link : links){
            if (link.getGroupID() == groupID && link.getUserID() == userID){
                userDeleted = 1;
                groupUserLinkRepository.delete(link);
                break;
            }
        }

        return userDeleted;
    }

    //---------------------------------------------------------------
    // Method:  createGroup
    // Purpose: To create a group for users to join
    // Inputs:  userID
    // Output:  groupID
    //---------------------------------------------------------------
    @PostMapping("/groupfeed/create")
    public int createGroup(@RequestParam(name="userID") int userID) {
        if (!userRepository.findById(userID).isPresent()){
            return -1;
        }
        _Group group = new _Group();
        group.setOwner(userID);
        //group.setGroupID(gID);
        groupRepository.save(group);
        addUserToGroup(userID, group.getGroupID());

        return group.getGroupID();
    }

    //---------------------------------------------------------------
    // Method:  getGroups
    // Purpose: To create a group for users to join
    // Inputs:  userID
    // Output:  groupID
    //---------------------------------------------------------------
    @GetMapping("/groupfeed/getgroups")
    public ArrayList<_Group> getGroups(@RequestParam(name="userID") int userID) {
        if (!userRepository.findById(userID).isPresent()){
            return null;
        }
        ArrayList<GroupUserLink> links = (ArrayList<GroupUserLink>)groupUserLinkRepository.findAll();
        ArrayList<_Group> userGroups = new ArrayList<_Group>();
        for(GroupUserLink link : links){
            if (link.getUserID() == userID){
                userGroups.add(groupRepository.findById(link.getGroupID()).get());
            }
        }
        return userGroups;
    }
}