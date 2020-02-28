package com.example.FitnessTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

//import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;

@RestController
public class SummaryController {
    private final SummaryRepository summaryRepository;
    @Autowired
    public SummaryController(SummaryRepository repo){
        summaryRepository = repo;
    }


    //---------------------------------------------------------------
    // Method:  getSummary
    // Purpose: To get the summary of a specific userID
    // Inputs:  userID
    // Output:  String with total distance and longest run of the user if succeeds, Error message if fails
    //---------------------------------------------------------------
    @GetMapping("/summary/get")
    public String getSummary(@RequestParam Integer userID) {
        Double distance = 0.0;
        Double pace=0.0;
        int[] input= {1,2};

        ArrayList<Summary> summaries = (ArrayList<Summary>) summaryRepository.findAll();


        for(Summary summary : summaries ){
            if(summary.getUserID()==userID){
                distance = summary.getTotal_distance();
                pace = summary.getPace();
                input = splitToComponentTimes(pace);
            }
            else return "No available data on user or user does not exist";
        }

        return "Total distance of this user:" + distance + "\n" +"Quickest run of user: " + input[0] + input[1]+":"+input[2];
    }

    //---------------------------------------------------------------
    // Method:  updateSummary
    // Purpose: To update summary of a specific userID
    // Inputs:  activityID, description,distance,hours,minutes,seconds
    // Output:  activtyID of summary being updated else zero
    //---------------------------------------------------------------

    public void updateSummary(Activity activity) {
        Double pace;
        Boolean flag = false;

        Integer time;
        time = activity.getHours() * 3600 + activity.getMinutes() * 60 + activity.getSeconds();
        pace = time / activity.getDistance();
        ArrayList<Summary> summaries = (ArrayList<Summary>) summaryRepository.findAll();
        for (Summary summary : summaries) {
            if (activity.getUserID() == summary.getUserID()) {
                flag = true;
                summary.setTotal_distance(summary.getTotal_distance() + activity.getDistance());
                if (summary.getPace() > pace)
                    summary.setPace(pace);
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

