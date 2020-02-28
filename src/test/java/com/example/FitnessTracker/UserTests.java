package com.example.FitnessTracker;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    void happyGetUser(){
        Integer input = userController.createUser("uniqueusername","test");
        Assertions.assertEquals(input,1);
    }

    //try to create new account with existing username
    @Test
    void unhappyGetUser(){
        Integer input = userController.createUser("uniqueusername","test");
        Assertions.assertEquals(input,0);

    }

    //Valid credentials
    @Test
    void happyUserLogin(){
        User user = new User("test1", "pass1");
        userRepository.save(user);
        Integer input = userController.loginUser("test1","pass1");
        Assertions.assertEquals(input,user.getUserID());
    }
    //Invalid username & password
    @Test
    void unhappy1UserLogin(){
        User user = new User("test", "pass");
        userRepository.save(user);
        Integer input = userController.loginUser("fakeusername","fakepassword");
        Assertions.assertEquals(input,1);
    }
    //Invalid Password
    @Test
    void unhappy2UserLogin(){
        User user = new User("test", "pass");
        userRepository.save(user);
        Integer input = userController.loginUser("test","fakepassword");
        Assertions.assertEquals(input,1);
    }
}
