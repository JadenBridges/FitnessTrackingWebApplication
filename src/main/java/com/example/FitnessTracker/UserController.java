package com.example.FitnessTracker;

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
                return 0;
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
        return 0;
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
        return 0;
    }

}
