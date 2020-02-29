package com.example.FitnessTracker.model;

import javax.persistence.*;

@Entity
@NamedQuery(name = "Comment.findAllByPostID",
        query = "select c from Comment c where c.postID = ?1")
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer commentID;

    private int postID;

    private int userID;

    private String message;

    public Comment() {}

    public Comment(int _postID, int _userID, String _message) {
        postID = _postID;
        userID = _userID;
        message = _message;
    }

    public Integer getCommentID() {
        return commentID;
    }

    public void setCommentID(Integer commentID) {
        this.commentID = commentID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }
}
