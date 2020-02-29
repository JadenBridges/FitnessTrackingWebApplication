package com.example.FitnessTracker.model;

import java.util.ArrayList;

public class PostWithComments {

    private Post post;
    private ArrayList<CommentDTO> comments;

    public PostWithComments(Post _post, ArrayList<CommentDTO> _comments) {
        post = _post;
        comments = _comments;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public ArrayList<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentDTO> comments) {
        this.comments = comments;
    }
}
