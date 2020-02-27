package com.example.FitnessTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ActivityController {
    @Autowired
    private ActivityRepository activityRepository;
    private DatabaseUtility databaseUtility = new DatabaseUtility();
    @Autowired
    private SummaryController summaryController;

    //---------------------------------------------------------------
    // Method:  createActivity
    // Purpose: To create and store an activity
    // Inputs:  Activity
    // Output:  void
    //---------------------------------------------------------------
    @PostMapping("/activity/create")
    public int createActivity(@RequestBody Activity activity) {
        activityRepository.save(activity);
        summaryController.updateSummary(activity);
        return 1;
    }

    //---------------------------------------------------------------
    // Method:  updateActivity
    // Purpose: To update an activity
    // Inputs:  activityID, Activity
    // Output:  void
    //---------------------------------------------------------------
    @PutMapping("/activity/update")
    public int updateActivity(@RequestParam int activityID, @RequestBody Activity activity) {
        if(activityRepository.findById(activityID).isPresent()){
            Activity oldActivity = activityRepository.findById(activityID).get();
            oldActivity.setUserID(activity.getUserID());
            oldActivity.setTitle(activity.getTitle());
            oldActivity.setDescription(activity.getDescription());
            oldActivity.setDistance(activity.getDistance());
            oldActivity.setHours(activity.getHours());
            oldActivity.setMinutes(activity.getMinutes());
            oldActivity.setSeconds(activity.getSeconds());
            activityRepository.save(oldActivity);
            return 1;
        }

        return -1;
    }

    //---------------------------------------------------------------
    // Method:  deleteActivity
    // Purpose: To delete an activity
    // Inputs:  activityID
    // Output:  void
    //---------------------------------------------------------------
    @DeleteMapping("/activity/delete")
    public int deleteActivity(@RequestParam int activityID) {
        if(activityRepository.findById(activityID).isPresent()){
            activityRepository.delete(activityRepository.findById(activityID).get());
            return 1;
        }
        return -1;
    }




}
