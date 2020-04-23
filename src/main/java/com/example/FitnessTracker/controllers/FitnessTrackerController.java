package com.example.FitnessTracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class FitnessTrackerController {

    public FitnessTrackerController(){}

    @GetMapping("/feeds")
    public String index(Model m, @RequestParam int userID) {
        Map<String, Integer> map = new HashMap<>();
        int t = userID;
        map.put("userID", t);
        m.mergeAttributes(map);
        return "feeds";
    }

    @RequestMapping("/")
    public String displayLogin() {
        return "UserForm";
    }


}
