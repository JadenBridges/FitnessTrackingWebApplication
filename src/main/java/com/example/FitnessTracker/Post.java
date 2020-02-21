package com.example.FitnessTracker;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer postID;

    @OneToOne
    @JoinColumn(name="activityid")
    private Activity activity;

    @OneToMany
    @JoinColumn(name="commentid")
    private List<Comment> comments = new ArrayList<>();

    private int likes;
}
