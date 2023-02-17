package com.userManagement.service;

import com.userManagement.User;
import com.userManagement.UserRepository;
import com.userManagement.service.bean.*;
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
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserManagementServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks

    UserManagementService userTest = new UserManagementService();
    private UserPost userPost;
    private User user;

    @BeforeEach
    void setUp() {

        System.out.println("Test is starting");

        MockitoAnnotations.openMocks(this);

        this.userPost = new UserPost();
        this.userPost.setEmail("tom@gmail.com");
        this.userPost.setFirstName("firstname");
        this.userPost.setLastName("lastname");
        this.userPost.setContactNumber("123456");
        this.userPost.setTags(List.of(new String[]{"a", "b"}));
    }

    @AfterEach
    void getUp() {

        System.out.println("Test is end");
    }

    @Test
    void addUserTest() {

          UserManagementService userManagementServices1=mock(UserManagementService.class);


        Agify agify = new Agify();
        agify.setAge(23);
        Genderize genderize = new Genderize();
        genderize.setGender("male");
        Country country = new Country();
        country.setCountry_id("CZ");
        country.setProbability(0.22f);
        Nationalize nationalize = new Nationalize();
        nationalize.setCountry(List.of(country));

        Mockito.when(restTemplate.getForObject("https://api.agify.io/?name=firstname", Agify.class)).thenReturn(agify);
        Mockito.when(restTemplate.getForObject("https://api.genderize.io/?name=firstname", Genderize.class)).thenReturn(genderize);
        Mockito.when(restTemplate.getForObject("https://api.nationalize.io?name=firstname", Nationalize.class)).thenReturn(nationalize);

        userTest.addUser(userPost);
        verify(userManagementServices1,atLeastOnce());

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

    @Test
    void agifyTest() throws Exception {

        Agify ag = new Agify();
        Class<UserManagementService> agifyClass = UserManagementService.class;
        Method agifyTestMethod = agifyClass.getDeclaredMethod("getAgify", String.class);
        agifyTestMethod.setAccessible(true);
        Mockito.when(restTemplate.getForObject("https://api.agify.io/?name=tom", Agify.class)).thenReturn(ag);
        Object result = agifyTestMethod.invoke(userTest, "tom");
        assertEquals(ag, result);
    }


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

         UserManagementService userManagementServices2=mock(UserManagementService.class);

        Agify agify = new Agify();
        agify.setAge(23);
        Genderize genderize = new Genderize();
        genderize.setGender("male");
        Country country = new Country();
        country.setCountry_id("CZ");
        country.setProbability(0.22f);
        Nationalize nationalize = new Nationalize();
        nationalize.setCountry(List.of(country));

        Mockito.when(restTemplate.getForObject("https://api.agify.io/?name=firstname", Agify.class)).thenReturn(agify);
        Mockito.when(restTemplate.getForObject("https://api.genderize.io/?name=firstname", Genderize.class)).thenReturn(genderize);
        Mockito.when(restTemplate.getForObject("https://api.nationalize.io?name=firstname", Nationalize.class)).thenReturn(nationalize);

        userTest.updateUser(userPost);
        verify(userManagementServices2, atLeastOnce());
    }

}

