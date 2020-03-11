package com.enjoy.mathero.ui.controller;

import com.enjoy.mathero.service.UserService;
import com.enjoy.mathero.shared.dto.UserDto;
import com.enjoy.mathero.ui.model.request.UserDetailsRequestModel;
import com.enjoy.mathero.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public String getUser(){
        return "huj dupa";
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails){
        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;
    }

    @PutMapping
    public String updateUser(){
        return "xd";
    }

    @DeleteMapping
    public String deleteUser(){
        return "usuniety";
    }

}
