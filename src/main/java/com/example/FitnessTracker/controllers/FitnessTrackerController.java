package com.example.FitnessTracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class FitnessTrackerController {

    public FitnessTrackerController(){}

    @GetMapping("/feeds")
    public String displayFeeds() {
        return "feeds";
    }

    @RequestMapping("/")
    public String displayLogin() {
        return "UserForm";
    }


}
