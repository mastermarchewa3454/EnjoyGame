package com.enjoy.mathero.service;

import com.enjoy.mathero.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);
    UserDto updateUser(String userId, UserDto user);
    UserDto getUser(String email);
    UserDto getUserByUserId(String id);
    void deleteUser(String userId);
}
