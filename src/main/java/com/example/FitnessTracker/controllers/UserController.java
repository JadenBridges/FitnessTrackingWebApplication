package com.example.FitnessTracker.controllers;

import com.example.FitnessTracker.model.User;
import com.example.FitnessTracker.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    // Inputs:  username,password
    // Output:  1 if succesful 0 if fails
    //---------------------------------------------------------------
    @PostMapping("/user/create")
    public int createUser(@RequestParam String username, @RequestParam String password){
        ArrayList<User> userArrayList = (ArrayList<User>) userRepository.findAll();
        for(User user1 : userArrayList) {
            if(user1.getUsername().equals(username))
                return -1;
        }
        User n = new User(username, password);
        userRepository.save(n);
        return 1;
    }

    //---------------------------------------------------------------
    // Method:  userLogin
    // Purpose: To login as a specific user
    // Inputs:  username,password
    // Output:  0 if fails, userid if succesful
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
        return -1;
    }
    //---------------------------------------------------------------
    // Method:  removeUser
    // Purpose: To delete  a specific user
    // Inputs:  username
    // Output:  0 if fails, 1 if succesful
    //---------------------------------------------------------------
    @DeleteMapping("/user/delete")
    public int removeUser(@RequestParam String username) {
        ArrayList<User> userArrayList = (ArrayList<User>) userRepository.findAll();
        for(User user1 : userArrayList) {
            if (user1.getUsername().equals(username)){
                userRepository.deleteById(user1.getUserID());
                return 1;
            }
        }
        return -1;
    }

    //---------------------------------------------------------------
    // Method:  getUserID
    // Purpose: To obtain the user id of a user based off their
    //          username
    // Inputs:  username
    // Output:  0 if fails, 1 if successful
    //---------------------------------------------------------------
    @GetMapping("/user/getuserid")
    public int getUserID(@RequestParam String username) {
        ArrayList<User> userArrayList = (ArrayList<User>) userRepository.findAll();
        for(User user1 : userArrayList) {
            if (user1.getUsername().equals(username)){
                return user1.getUserID();
            }
        }
        return -1;
    }

    //---------------------------------------------------------------
    // Method:  getUsername
    // Purpose: To obtain the username of a user based off their
    //          user id
    // Inputs:  user id
    // Output:  0 if fails, 1 if successful
    //---------------------------------------------------------------
    @GetMapping("/user/getusername")
    public String getUsername(@RequestParam int userid) {
        ArrayList<User> userArrayList = (ArrayList<User>) userRepository.findAll();
        for(User user1 : userArrayList) {
            if (user1.getUserID() == userid) {
                return user1.getUsername();
            }
        }
        return "";
    }
}
