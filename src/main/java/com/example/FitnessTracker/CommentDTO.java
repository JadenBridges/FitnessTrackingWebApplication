package com.example.FitnessTracker;

public class CommentDTO {

    private UserDTO userDTO;
    private int postID;
    private String message;

    public CommentDTO(UserDTO _userDTO, int _postID, String _message) {
        userDTO = _userDTO;
        postID = _postID;
        message = _message;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
