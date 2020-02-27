package com.example.FitnessTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

//import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;

@RestController
public class SummaryController {
    @Autowired
    private ActivityRepository activityRepository;

    //---------------------------------------------------------------
    // Method:  getSummary
    // Purpose: To get the summary of a specific userID
    // Inputs:  userID
    // Output:  String with total distance and longest run of the user if succeeds, Error message if fails
    //---------------------------------------------------------------
    @GetMapping("/summary/get")
    public String getSummary(@RequestParam Integer userID){
        Double distance =0.0;
        Integer longest_run= 0;
        ArrayList<Activity> activities = (ArrayList<Activity>)activityRepository.findAll();
        for(Activity activity : activities ){
            Integer run = activity.getHours()*3600 + activity.getMinutes() *60 + activity.getSeconds();
            if(activity.getUserID()==userID){
                distance += activity.getDistance();
                if(run>= longest_run)
                    longest_run =run;
            }
            else return "No available data on user";
        }
        int[] time = splitToComponentTimes(longest_run);
        return "Total distance of this user: " + distance.toString() + "\n" +"Longest run of this user: " + time[0] + " hour "+time[1] + " minutes " + time[2] + " seconds";

    }
    //---------------------------------------------------------------
    // Method:  updateSummary
    // Purpose: To update summary of a specific userID
    // Inputs:  activityID, description,distance,hours,minutes,seconds
    // Output:  acitivtyID of summary being updated else zero
    //---------------------------------------------------------------
    @PutMapping("summary/update")
    public int updateSummary(@RequestParam Integer activityID, String description, Double distance, Integer hours, Integer minutes, Integer seconds){
        ArrayList<Activity> activities = (ArrayList<Activity>)activityRepository.findAll();
        for(Activity activity : activities){
            if(activity.getActivityID()== activityID)
                activity.setDescription(description);
                activity.setDistance(distance);
                activity.setHours(hours);
                activity.setMinutes(minutes);
                activity.setSeconds(seconds);
                activityRepository.save(activity);
                return activity.getActivityID();
        }
        return 0;
    }

    public static int[] splitToComponentTimes(Integer input)
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

