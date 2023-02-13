package com.userManagement;



import com.userManagement.service.UserManagementService;
import com.userManagement.service.bean.UserPost;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController

public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserManagementService userManagementService;

    @PostMapping(path = "/api/user-management/user")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody UserPost userPost) {

        userManagementService.addUser(userPost);

    }

    @DeleteMapping("/api/user-management/user/{userName}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable String userName){
        userManagementService.deleteUserByUserName(userName);

    }

    @PutMapping("/api/user-management/user")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody UserPost userPost) {

        userManagementService.updateUser(userPost);

    }



    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
     return userRepository.findAll();


    }


}