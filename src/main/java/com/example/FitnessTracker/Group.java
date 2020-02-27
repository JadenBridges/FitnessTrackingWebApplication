package com.example.FitnessTracker;

import javax.persistence.*;
import java.util.List;

@Entity
public class Group {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer groupID;

    @ManyToMany(mappedBy="group")
    private List<User> users;

    private int owner;

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
