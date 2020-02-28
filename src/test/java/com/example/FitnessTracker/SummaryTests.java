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
        Summary summary = new Summary();
        summary.setUserID(3);
        summary.setPace(420.2);
        summary.setTotal_distance(50.0);
        summaryRepository.save(summary);

        String input = summaryController.getSummary(3);
        Assertions.assertEquals("Total distance of this user:50.0\n" +
                                "Quickest run of user: 07:0", input);
    }
    //passing in an invalid userID
    @Test
    void unhappyGetSummary(){
        String input = summaryController.getSummary(5);
        Assertions.assertEquals("No available data on user",input);

    }
    //user exists but no data in summary
    @Test
    void unhappy2GetSummary(){
        String input = summaryController.getSummary(2);
        Assertions.assertEquals(input,"No available data on user");
    }

}
