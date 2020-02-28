package com.example.FitnessTracker;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "Post.findByActivityID",
        query = "select p from Post p where p.activity.activityID = ?1")
@NamedQuery(name = "Post.findAllByUserID",
        query = "select p from Post p where p.activity.userID = ?1")

public class Post {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer postID;

    @OneToOne
    @JoinColumn(name="activityid")
    private Activity activity;

    private int likes;

    public Post(){}

    public Post(Activity activity, int likes){
        this.activity = activity;
        this.likes = likes;
    }

    public Integer getPostID() {
        return postID;
    }

    public void setPostID(Integer postID) {
        this.postID = postID;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
