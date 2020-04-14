package com.enjoy.mathero.service.impl;

import com.enjoy.mathero.exceptions.ClassServiceException;
import com.enjoy.mathero.exceptions.RoleServiceException;
import com.enjoy.mathero.exceptions.UserServiceException;
import com.enjoy.mathero.io.entity.ClassEntity;
import com.enjoy.mathero.io.entity.RoleEntity;
import com.enjoy.mathero.io.repository.ClassRepository;
import com.enjoy.mathero.io.repository.RoleRepository;
import com.enjoy.mathero.io.repository.UserRepository;
import com.enjoy.mathero.io.entity.UserEntity;
import com.enjoy.mathero.service.UserService;
import com.enjoy.mathero.shared.Utils;
import com.enjoy.mathero.shared.dto.ClassDto;
import com.enjoy.mathero.shared.dto.UserDto;
import com.enjoy.mathero.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ClassRepository classRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user, String role, String className) {

        if(userRepository.findByUsername(user.getUsername()) != null)
            throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());

        ClassEntity studentClass = null;
        if(role.equals("ROLE_STUDENT")){
            studentClass = classRepository.findByClassName(className);
            if(studentClass == null)
                throw new ClassServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        String publicUserId = utils.generateUserID(30);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setUserId(publicUserId);
        List<RoleEntity> roles = new ArrayList<>();
        roles.add(roleRepository.findByRoleName(role));

        if(roles.get(0) == null)
            throw new RoleServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        userEntity.setRoles(roles);
        userEntity.setClassDetails(studentClass);
        userEntity.setMaxStageCanPlay(0);

        UserDto returnValue = new UserDto();

        UserEntity storedUserDetails = userRepository.save(userEntity);
        if(storedUserDetails.getRoles().get(0).getRoleName().equals("ROLE_STUDENT")){
            ClassDto classDto = new ClassDto();
            BeanUtils.copyProperties(storedUserDetails.getClassDetails(), classDto);
            returnValue.setClassDetails(classDto);
        }

        BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) {
        UserDto userDto = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setMaxStageCanPlay(user.getMaxStageCanPlay());

        UserEntity updatedUserDetails = userRepository.save(userEntity);
        BeanUtils.copyProperties(updatedUserDetails, userDto);

        return userDto;
    }

    @Override
    public UserDto getUser(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);

        if(userEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userEntity, UserDto.class);

        return userDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userEntity, UserDto.class);

        return userDto;
    }

    @Override
    public UserDto getTeacherByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null || !userEntity.getRoles().get(0).getRoleName().equals("ROLE_TEACHER"))
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userEntity, UserDto.class);

        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);

        if(userEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        List<GrantedAuthority> authorities = new ArrayList<>();
        List<RoleEntity> roles = userEntity.getRoles();
        for(RoleEntity role: roles){
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        return new User(userEntity.getUsername(), userEntity.getEncryptedPassword(), authorities);
    }

    @Override
    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
        List<UserEntity> users = usersPage.getContent();


        for(UserEntity userEntity: users){
            UserDto userDto = new UserDto();
            if(userEntity.getClassDetails() != null){
                ClassDto classDto = new ClassDto();
                BeanUtils.copyProperties(userEntity.getClassDetails(), classDto);
                userDto.setClassDetails(classDto);
            }
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }

        return returnValue;
    }

}
