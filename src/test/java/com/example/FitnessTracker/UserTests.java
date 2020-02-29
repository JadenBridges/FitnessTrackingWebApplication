package com.example.FitnessTracker;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class UserTests {

    private final UserRepository userRepository;
    private final UserController userController;

    @Autowired
    public UserTests(UserController ifc, UserRepository ur){
        userRepository = ur;
        userController = ifc;

    }

    //-------------------------------------------------------------------------
    // Controller: UserController
    // Endpoints:
    //		  VERB | URL                          | METHOD
    //      -------+------------------------------+--------------
    //		  POST | /user/create                 | createUser(string,string)
    //		   Get | /user/login                  | loginUser(string,string)
    //-------------------------------------------------------------------------


    //Create a new user Test
    @Test
    void happyGetUser() {
        Integer input = userController.createUser("uniqueusername", "test");
        Assertions.assertEquals(1, input);
        userController.removeUser("uniqueusername");
    }

    //try to create new account with existing username
    @Test
    void unhappyGetUser(){
        User user = new User("uniqueusername1", "test");
        userRepository.save(user);
        Integer input = userController.createUser("uniqueusername1","test");
        Assertions.assertEquals(0,input);

    }

    //Valid credentials
    @Test
    void happyUserLogin(){
        User user = new User("test1", "pass1");
        userRepository.save(user);
        Integer input = userController.loginUser("test1","pass1");
        Assertions.assertEquals(input,user.getUserID());
        userRepository.delete(user);
    }
    //Invalid username & password
    @Test
    void unhappy1UserLogin(){
        User user = new User("test", "pass");
        userRepository.save(user);
        Integer input = userController.loginUser("fakeusername","fakepassword");
        Assertions.assertEquals(0,input);
    }
    //Invalid Password
    @Test
    void unhappy2UserLogin(){
        User user = new User("test2", "pass");
        userRepository.save(user);
        Integer input = userController.loginUser("test2","fakepassword");
        Assertions.assertEquals(0,input);
    }
// Delete an existing user
    @Test
    void happyDelete(){
        User user = new User("uniqueusername2", "pass");
        userRepository.save(user);
        Integer input = userController.removeUser("uniqueusername2");
        Assertions.assertEquals(1,input);

    }
    //Delete a user that does not exist
    @Test
    void unhappyDelete(){
        Integer input = userController.removeUser("uniqueusername2");
        Assertions.assertEquals(0,input);
    }
}
