package com.example.FitnessTracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SummaryTests {
    private final SummaryRepository summaryRepository;
    private final UserRepository userRepository;
    private final SummaryController summaryController;


    @Autowired
    public SummaryTests( SummaryController sc,SummaryRepository sr, UserRepository ur){
        summaryRepository = sr;
        summaryController =sc;
        userRepository = ur;
    }
    //-------------------------------------------------------------------------
    // Controller: SummaryController
    // Endpoints:
    //		  VERB | URL                          | METHOD
    //      -------+------------------------------+--------------
    //		  GET  | /summary/get                 | getSummary(integer)
    //-------------------------------------------------------------------------
    @Test
    void happyGetSummary(){
        User user = new User("uniqueuser2", "pwd");
        userRepository.save(user);
        Summary summary = new Summary();
        summary.setUserID(user.getUserID());
        summary.setPace(420.2);
        summary.setDistance(10.0);
        summaryRepository.save(summary);

        String input = summaryController.getSummary(user.getUserID());
        Assertions.assertEquals("Total distance of this user:10.0\n" +
                "Longest run of this user:10.0\n" +
                "Fastest run of this user:00:0", input);
    }
    //passing in an invalid userID
    @Test
    void unhappyGetSummary(){
        String input = summaryController.getSummary(345678);
        Assertions.assertEquals(input,"No available data on user or user does not exist");

    }
    //user exists but no data in summary
    @Test
    void unhappy2GetSummary(){
        String input = summaryController.getSummary(2);
        Assertions.assertEquals(input,"No available data on user or user does not exist");
    }

}
