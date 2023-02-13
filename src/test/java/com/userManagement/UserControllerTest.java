package com.userManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userManagement.service.UserManagementService;
import com.userManagement.service.bean.UserPost;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserManagementService userManagementService;
    private User user;
    private UserPost userPost;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setupMock() {

        System.out.println("Test is starting");
        MockitoAnnotations.openMocks(this);

        this.userPost = new UserPost(

                "password",
                "fistName",
                "lastName",
                "contractNumber",
                "tom@gmail.com",
                Collections.singletonList("a:b:c")

        );

    }


    @AfterEach
    void tearDown() {

        System.out.println("Test is end");

    }

    @Test
    void deleteUserTest() throws Exception {

        doNothing().when(userManagementService).deleteUserByUserName(userPost.getEmail());
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/user-management/user/{email}",userPost.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    void addUserTest() throws Exception {



        doNothing().when(userManagementService).addUser(isA(UserPost.class));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/user-management/user")
                        .content(new ObjectMapper().writeValueAsString(userPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    void userUpdatedTest() throws Exception {

        var user1=new User(
                "tom@gmail.com",
                "password1111",
                "fistName",
                "lastName",
                "contractNumber222",
                "tom@gmail.com",
                "a:b:c",
                0,
                "null",
                "null",
                "active",
                "null",
                " null"

        );



        doNothing().when(userManagementService).updateUser(isA(UserPost.class));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/user-management/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user1)))
                .andExpect(status().isOk());
    }


    @Test
    void getAllUsersTest() throws Exception {

        this.mockMvc.perform((MockMvcRequestBuilders
                        .get("/all", userRepository.findAll())
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk());

    }

}