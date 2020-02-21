package com.example.FitnessTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user/create")
    public int createUser(@RequestParam String username, @RequestParam String password){
        User n = new User();
        n.setUsername(username);
        n.setPassword(password);
        userRepository.save(n);
        return n.getUserID();
    }
}
