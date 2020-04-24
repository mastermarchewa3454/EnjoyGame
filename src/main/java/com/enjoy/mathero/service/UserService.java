package com.enjoy.mathero.service;

import com.enjoy.mathero.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Business logic to deal with users.
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user, String role, String className);
    UserDto updateUser(String userId, UserDto user);
    UserDto getUser(String email);
    UserDto getTeacherByUserId(String id);
    List<UserDto> getUsers(int page, int limit);
    UserDto getUserByUserId(String id);
    void deleteUser(String userId);
}
