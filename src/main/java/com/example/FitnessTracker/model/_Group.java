package com.example.FitnessTracker.model;

import javax.persistence.*;
import java.util.List;

@Entity
// underscore due to group being a reserved word in MySQL
public class _Group {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer groupID;

    private int owner;

    public _Group(){}

    public _Group(int owner){
        this.owner = owner;
    }

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
}
