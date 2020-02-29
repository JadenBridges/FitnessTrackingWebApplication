package com.example.FitnessTracker;

import com.example.FitnessTracker.model.Comment;import com.example.FitnessTracker.model.CommentDTO;

import java.util.ArrayList;

public class PostWithComments {

    private Comment.Post post;
    private ArrayList<CommentDTO> comments;

    public PostWithComments(Comment.Post _post, ArrayList<CommentDTO> _comments) {
        post = _post;
        comments = _comments;
    }

    public Comment.Post getPost() {
        return post;
    }

    public void setPost(Comment.Post post) {
        this.post = post;
    }

    public ArrayList<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentDTO> comments) {
        this.comments = comments;
    }
}
