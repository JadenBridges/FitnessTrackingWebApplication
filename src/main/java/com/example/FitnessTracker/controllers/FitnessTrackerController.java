package com.example.FitnessTracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;

@Controller
public class FitnessTrackerController {

    public FitnessTrackerController(){}

    @GetMapping("/index")
    public String index(Model m) {
        Map<String, String> map = new HashMap<>();
        String t = "Spring <b>Boot</b>";
        map.put("spring", t);
        m.mergeAttributes(map);
        return "index";
    }
}
