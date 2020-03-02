package com.example.FitnessTracker.controllers;

import com.example.FitnessTracker.model.ActivityRepository;
import com.example.FitnessTracker.model.SummaryRepository;
import com.example.FitnessTracker.model.Activity;
import com.example.FitnessTracker.model.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;

//import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;

@RestController
public class SummaryController {
    private final SummaryRepository summaryRepository;
    private final ActivityRepository activityRepository;
    @Autowired
    public SummaryController(SummaryRepository repo, ActivityRepository actRepo){
        summaryRepository = repo;
        activityRepository = actRepo;
    }


    //---------------------------------------------------------------
    // Method:  getSummary
    // Purpose: To get the summary of a specific userID
    // Inputs:  userID
    // Output:  String with total distance and longest run of the user if succeeds, Error message if fails
    //---------------------------------------------------------------
    @GetMapping("/summary/get")
    public String getSummary(@RequestParam Integer userID) {
        double distance = 0.0;
        double pace=0.0;
        int[] input= {1,2};

        ArrayList<Summary> summaries = (ArrayList<Summary>) summaryRepository.findAll();


        for(Summary summary : summaries ){
            if(summary.getUserID()==userID){
                distance = summary.getTotal_distance();
                pace = summary.getPace();
                input = splitToComponentTimes(pace);
                return "Total distance of this user:" + distance + "\n" +"Quickest run of user: " + input[0] + input[1]+":"+input[2];
            }
            else return "No available data on user";
        }
        return "No available data on user";
    }

    //---------------------------------------------------------------
    // Method:  updateSummary
    // Purpose: To update summary of a specific activity
    // Inputs:  activity
    // Output:
    //---------------------------------------------------------------

    public void updateSummary(Activity activity) {
        double pace;
        boolean flag = false;

        int time;
        time = activity.getHours() * 3600 + activity.getMinutes() * 60 + activity.getSeconds();
        pace = time / activity.getDistance();
        ArrayList<Summary> summaries = (ArrayList<Summary>) summaryRepository.findAll();
        for (Summary summary : summaries) {
            if (activity.getUserID() == summary.getUserID()) {
                flag = true;
                summary.setTotal_distance(summary.getTotal_distance() + activity.getDistance());
                if (summary.getPace() > pace)
                    summary.setPace(pace);
                else if (summary.getPace() == 0){
                    summary.setPace(pace);
                }
            }
        }
        if (!flag) {
            Summary newSummary = new Summary();
            newSummary.setUserID(activity.getUserID());
            newSummary.setTotal_distance(activity.getDistance());
            newSummary.setPace(pace);
            summaryRepository.save(newSummary);
        }

    }
    //---------------------------------------------------------------
    // Method:  removeSummary
    // Purpose: To remove summary of a specific activity
    // Inputs:  activity
    // Output:
    //---------------------------------------------------------------
    public void removeSummary(Activity activity){
        double pace;
        int time;
        time = activity.getHours() * 3600 + activity.getMinutes() * 60 + activity.getSeconds();
        pace = time / activity.getDistance();
        ArrayList<Summary> summaries = (ArrayList<Summary>) summaryRepository.findAll();
        for (Summary summary : summaries) {
            if(activity.getUserID() == summary.getUserID()){
                summary.setTotal_distance(summary.getTotal_distance()-activity.getDistance());
                if(summary.getPace()==pace){
                    ArrayList<Activity> allActivities = (ArrayList<Activity>) activityRepository.findAll();
                    boolean paceSet = false;
                    for(Activity a : allActivities){
                        if(a.getUserID() == summary.getUserID() && a.getActivityID() != activity.getActivityID()){
                            if(!paceSet){
                                pace = getPace(a);
                                summary.setPace(pace);
                                paceSet = true;
                            }
                            if(getPace(a) < pace){
                                pace = getPace(a);
                                summary.setPace(pace);
                            }
                        }
                    }
                    if(!paceSet)
                        summary.setPace(0.0);
                }
            }
        }
    }

    private double getPace(Activity a){
        double pace;
        int time;
        time = a.getHours() * 3600 + a.getMinutes() * 60 + a.getSeconds();
        pace = time / a.getDistance();
        return pace;
    }




    public static int[] splitToComponentTimes(Double input)
    {
        long longVal = input.longValue();
        int hours = (int) longVal / 3600;
        int remainder = (int) longVal - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;

        int[] ints = {hours , mins , secs};
        return ints;
    }
}

