package com.example.FitnessTracker;

import java.util.ArrayList;

public class PostWithComments {

    private Post post;
    private ArrayList<Comment> comments;

    public PostWithComments(Post _post, ArrayList<Comment> _comments) {
        post = _post;
        comments = _comments;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
