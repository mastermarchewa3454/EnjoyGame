package com.enjoy.mathero.service;

import com.enjoy.mathero.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user, String role, String className);
    UserDto updateUser(String userId, UserDto user);
    UserDto getUser(String email);
    UserDto getTeacherByUserId(String id);
    List<UserDto> getUsers(int page, int limit);
    UserDto getUserByUserId(String id);
    void deleteUser(String userId);
}
