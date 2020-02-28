package com.example.FitnessTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository repo){
        userRepository = repo;
    }

    //---------------------------------------------------------------
    // Method:  createUser
    // Purpose: To create a new user
    // Inputs:  username
    // Output:  password
    //---------------------------------------------------------------
    @PostMapping("/user/create")
    public int createUser(@RequestParam String username, @RequestParam String password){
        User n = new User(username, password);
        userRepository.save(n);
        return 1;
    }

    //---------------------------------------------------------------
    // Method:  userLogin
    // Purpose: To login as a specific user
    // Inputs:  username,password
    // Output:  1 if successful, 0 if not
    //---------------------------------------------------------------

    @GetMapping ("user/login")
    public int loginUser(@RequestParam String username, @RequestParam String password){
        ArrayList<User> userArrayList = (ArrayList<User>) userRepository.findAll();
        for(User user1 : userArrayList) {
            if(user1.getUsername().equals(username)) {
                if(user1.getPassword().equals(password)){
                    return user1.getUserID();
                }
            }
        }
        return 1;
    }

}
