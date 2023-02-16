package com.userManagement.service;

import com.userManagement.User;
import com.userManagement.UserRepository;
import com.userManagement.service.bean.Agify;
import com.userManagement.service.bean.Genderize;
import com.userManagement.service.bean.Nationalize;
import com.userManagement.service.bean.UserPost;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserManagementServiceTest {

   // @Mock
    private UserRepository userRepository;
    @Mock
    private RestTemplate restTemplate;


    @Mock
    private Agify getAgify;
    @Mock
    private Genderize genderize;
    @Mock
    private Nationalize nationalize;
       @InjectMocks
    UserManagementService userTest = new UserManagementService();
       private  UserPost userPost;
       private  User user;


    @BeforeEach
    void setUp() {

        System.out.println("Test is starting");
        MockitoAnnotations.openMocks(this);
        this.userPost = new UserPost();
        this.userPost.setEmail("123@gmail.com");
        this.userPost.setFirstName("first name");
        this.userPost.setLastName("last name");
        this.userPost.setContactNumber("238237");
        this.userPost.setTags(List.of(new String[]{"a", "b"}));
    }

    @AfterEach
    void getUp() {

        System.out.println("Test is end");

    }

    @Test
    void addUserTest() {

             /*var user= new User(
                "tom@gmail.com",
                   "password",
                 "tom",
                  "lastName",
                "tom@gmail.com",
                "contractNumber",
                "a:b:c",
                20,
                "gender",
                "nationality",
                "active",
                "null",
                "null"


              */



        Mockito.when(restTemplate.getForObject("https://api.agify.io/?name=firstname", Agify.class)).thenReturn(getAgify);

          userTest.addUser(userPost);
       Assertions.assertEquals(getAgify.getAge(), user.getAge());

        Assertions.assertEquals(user.getUserName(), userPost.getEmail());

        Assertions.assertEquals(user.getStatus(), "active");

    }


    @Test
    void dateTest() {

        System.out.println(userTest.date());
        assertEquals("-", userTest.date().substring(5, 6));
        assertEquals(":", userTest.date().substring(16, 17));

    }

    @Test
    void genderizeTest() throws Exception {

        Genderize gen = new Genderize();
        Class<UserManagementService> genderizeClass = UserManagementService.class;
        Method genderizeTestMethod = genderizeClass.getDeclaredMethod("getGenderize", String.class);
        genderizeTestMethod.setAccessible(true);
        Mockito.when(restTemplate.getForObject("https://api.genderize.io/?name=tom", Genderize.class)).thenReturn(gen);
        Object result = genderizeTestMethod.invoke(userTest, "tom");
        Assertions.assertEquals(gen, result);

    }

   /* @Test
    void agifyTest() throws Exception {

        Agify ag = new Agify();
        Class<UserManagementService> agifyClass = UserManagementService.class;
        Method agifyTestMethod = agifyClass.getDeclaredMethod("getAgify", String.class);
        agifyTestMethod.setAccessible(true);
        Mockito.when(restTemplate.getForObject("https://api.agify.io/?name=tom", Agify.class)).thenReturn(ag);
        Object result = agifyTestMethod.invoke(userTest, "tom");
        assertEquals(ag, result);
    }


    */

    @Test
    void nationalizeTest() throws Exception {

        Nationalize na = new Nationalize();
        Class<UserManagementService> natinalizeClass = UserManagementService.class;
        Method nationlizeTestMethod = natinalizeClass.getDeclaredMethod("getNationalize", String.class);
        nationlizeTestMethod.setAccessible(true);
        Mockito.when(restTemplate.getForObject("https://api.nationalize.io?name=tom", Nationalize.class)).thenReturn(na);
        Object result = nationlizeTestMethod.invoke(userTest, "tom");
        Assertions.assertEquals(na, result);

    }

    @Test
    void testDeleteExistingUserByUsername() {

        User user1 = new User();
        user1.setUserName("test@yahoo.com");

        when(userRepository.findById(user1.getUserName())).thenReturn(Optional.of(user1));
        doNothing().when(userRepository).deleteById(isA(String.class));
        userTest.deleteUserByUserName(user1.getUserName());
        verify(userRepository, times(1)).deleteById(user1.getUserName());
    }

    @Test
    void testDeleteNonExistingUserByUser() {
        User user2 = new User();
        user2.setUserName("test@yahoo.com");


        when(userRepository.findById(user2.getUserName())).thenReturn(Optional.empty());
        userTest.deleteUserByUserName(user2.getUserName());

        verify(userRepository, times(0)).deleteById(user2.getUserName());
    }


    @Test
    void updateUserTest() {

        User user1 = new User();

        user1.setEmail("test@email.com");
        user1.setPassword("password");
        user1.setLastName("lastname");
        user1.setFirstName("firstname");
        user1.setContactNumber("1234567890");
        user1.setTags("a:b:c");


        assertEquals("test@email.com", user1.getEmail());
        assertEquals("password", user1.getPassword());
        assertEquals("lastname", user1.getLastName());
        assertEquals("firstname", user1.getFirstName());
        assertEquals("1234567890", user1.getContactNumber());
        assertEquals("a:b:c", user1.getTags());
    }
}

