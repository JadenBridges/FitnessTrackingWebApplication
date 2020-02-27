package com.example.FitnessTracker;

import javax.persistence.*;

@Entity
public class GroupUserLink {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer linkID;

    private int groupID;
    private int userID;

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
