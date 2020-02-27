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
    private SummaryRepository summaryRepository;

    //---------------------------------------------------------------
    // Method:  getSummary
    // Purpose: To get the summary of a specific userID
    // Inputs:  userID
    // Output:  String with total distance and longest run of the user if succeeds, Error message if fails
    //---------------------------------------------------------------
    @GetMapping("/summary/get")
    public String getSummary(@RequestParam Integer userID) {
        Double distance = 0.0;
        Double longest_run = 0.0;
        Double fastest_run = 0.0;
        ArrayList<Summary> summaries = (ArrayList<Summary>) summaryRepository.findAll();


        for(Summary summary : summaries ){
            if(summary.getUserID()==userID){
                distance += summary.getDistance();
                if(summary.getSummaryID() ==1)
                    fastest_run = summary.getPace();
                else if(summary.getPace()<fastest_run)
                    fastest_run = summary.getPace();
                if(summary.getDistance()>longest_run)
                    longest_run = summary.getDistance();
            }
            else return "No available data on user";
        }
        int[] pace_min = splitToComponentTimes(fastest_run);

        return "Total distance of this user: " + distance.toString() + "\n" +"Longest run of this user: " + longest_run.toString() + "\n" + "Fastest run of this user : " + pace_min[0] + pace_min[1]+":" + pace_min[2];
    }

    //---------------------------------------------------------------
    // Method:  updateSummary
    // Purpose: To update summary of a specific userID
    // Inputs:  activityID, description,distance,hours,minutes,seconds
    // Output:  activtyID of summary being updated else zero
    //---------------------------------------------------------------

    public void updateSummary(Activity activity){
        Double pace;
        Integer time;
        time = activity.getHours()*3600 + activity.getMinutes()*60 + activity.getSeconds();
        pace = time/activity.getDistance();
        Summary newSummary = new Summary();
        newSummary.setUserID(activity.getUserID());
        newSummary.setDistance(activity.getDistance());
        newSummary.setPace(pace);
        summaryRepository.save(newSummary);


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

