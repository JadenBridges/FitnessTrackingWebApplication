package com.example.FitnessTracker.model;

import javax.persistence.*;

@Entity
public class GroupUserLink {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer linkID;

    private int groupID;
    private int userID;

    public GroupUserLink(){}

    public GroupUserLink(int groupID, int userID){
        this.groupID = groupID;
        this.userID = userID;
    }

    public Integer getLinkID() {
        return linkID;
    }

    public void setLinkID(Integer linkID) {
        this.linkID = linkID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
