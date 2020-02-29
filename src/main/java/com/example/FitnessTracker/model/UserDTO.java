package com.example.FitnessTracker.model;

public class UserDTO {

    private int userID;
    private String username;

    public UserDTO(int _userID, String _username) {
        userID = _userID;
        username = _username;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
