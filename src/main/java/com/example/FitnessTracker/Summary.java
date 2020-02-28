package com.example.FitnessTracker;

import javax.persistence.*;

@Entity
@NamedQuery(name = "Summary.findByUserID",
        query = "select s from Summary s where s.userID = ?1")
public class Summary {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer summaryID;

    private Integer userID;
    private Double distance;
    private Double pace;

    public Integer getSummaryID() {
        return summaryID;
    }

    public void setSummaryID(Integer summaryID) {
        this.summaryID = summaryID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }



    public Double getPace() {
        return pace;
    }

    public void setPace(Double pace) {
        this.pace = pace;
    }



}
