package com.example.FitnessTracker;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTests {

    private final UserRepository userRepository;

    @Autowired
    public UserTests( UserRepository ur){
        userRepository = ur;
    }

    //-------------------------------------------------------------------------
    // Controller: UserController
    // Endpoints:
    //		  VERB | URL                          | METHOD
    //      -------+------------------------------+--------------
    //		  POST | /user/create                 | createUser(string,string)
    //		   Get | /user/login                  | loginUser(string,string)
    //-------------------------------------------------------------------------
    @Test
    void getUser(){
    UserController usc = new UserController(userRepository);
    Integer input = usc.createUser("test","test");
        Assertions.assertEquals(input,1);
    }

    //Valid credentials
    @Test
    void happyUserLogin(){
        UserController usc = new UserController(userRepository);
        User user = new User("test", "pass");
        userRepository.save(user);
        Integer input = usc.loginUser("test","pass");
        Assertions.assertEquals(input,user.getUserID());
    }
    //Invalid username & password
    @Test
    void unhappy1UserLogin(){
        UserController usc = new UserController(userRepository);
        User user = new User("test", "pass");
        userRepository.save(user);
        Integer input = usc.loginUser("fakeusername","fakepassword");
        Assertions.assertEquals(input,1);
    }
    //Invalid Password
    @Test
    void unhappy2UserLogin(){
        UserController usc = new UserController(userRepository);
        User user = new User("test", "pass");
        userRepository.save(user);
        Integer input = usc.loginUser("test","fakepassword");
        Assertions.assertEquals(input,1);
    }
}
