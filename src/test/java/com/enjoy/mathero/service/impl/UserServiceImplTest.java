package com.enjoy.mathero.service.impl;

import com.enjoy.mathero.exceptions.ClassServiceException;
import com.enjoy.mathero.exceptions.RoleServiceException;
import com.enjoy.mathero.exceptions.UserServiceException;
import com.enjoy.mathero.io.entity.ClassEntity;
import com.enjoy.mathero.io.entity.RoleEntity;
import com.enjoy.mathero.io.entity.UserEntity;
import com.enjoy.mathero.io.repository.ClassRepository;
import com.enjoy.mathero.io.repository.RoleRepository;
import com.enjoy.mathero.io.repository.UserRepository;
import com.enjoy.mathero.service.UserService;
import com.enjoy.mathero.shared.Utils;
import com.enjoy.mathero.shared.dto.ClassDto;
import com.enjoy.mathero.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    Utils utils;

    @Mock
    RoleRepository roleRepository;

    @Mock
    ClassRepository classRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    UserEntity studentEntity;
    UserEntity teacherEntity;
    List<ClassEntity> classEntities;
    List<RoleEntity> teacherRole;
    List<RoleEntity> studentRole;
    String publicUserId = "A6nH4hfAHgahsd485";
    String encryptedPassword = "AKJ5b3b2kfskdfn%jkad";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        classEntities = getClasses();
        teacherRole = getTeacherRole();
        studentRole = getStudentRole();
        studentEntity = getStudentEntity();
        teacherEntity = getTeacherEntity();
    }

    @Test
    void createUser_student() {
        UserDto studentDto = getStudentDto();

        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(classRepository.findByClassName(anyString())).thenReturn(classEntities.get(0));
        when(roleRepository.findByRoleName(anyString())).thenReturn(studentRole.get(0));
        when(utils.generateUserID(anyInt())).thenReturn(publicUserId);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        when(userRepository.save(any(UserEntity.class))).thenReturn(studentEntity);

        UserDto savedDetails = userService.createUser(studentDto, "ROLE_STUDENT", "Class A");

        assertNotNull(savedDetails);
        assertNotNull(savedDetails.getClassDetails());
        assertEquals(studentDto.getUsername(), savedDetails.getUsername());
        assertEquals(studentDto.getFirstName(), savedDetails.getFirstName());
        assertEquals(studentDto.getLastName(), savedDetails.getLastName());
        assertEquals(studentDto.getEmail(), savedDetails.getEmail());
        assertEquals(publicUserId, savedDetails.getUserId());
        assertEquals(encryptedPassword, savedDetails.getEncryptedPassword());
        assertEquals(0, savedDetails.getMaxStageCanPlay());
        assertEquals(classEntities.get(0).getClassName(), savedDetails.getClassDetails().getClassName());

    }

    @Test
    void createUser_teacher(){
        UserDto teacherDto = getTeacherDto();

        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(classRepository.findByClassName(anyString())).thenReturn(null);
        when(roleRepository.findByRoleName(anyString())).thenReturn(teacherRole.get(0));
        when(utils.generateUserID(anyInt())).thenReturn(publicUserId);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        when(userRepository.save(any(UserEntity.class))).thenReturn(teacherEntity);

        UserDto savedDetails = userService.createUser(teacherDto, "ROLE_TEACHER", null);

        assertNotNull(savedDetails);
        assertNull(savedDetails.getClassDetails());
        assertEquals(teacherDto.getUsername(), savedDetails.getUsername());
        assertEquals(teacherDto.getFirstName(), savedDetails.getFirstName());
        assertEquals(teacherDto.getLastName(), savedDetails.getLastName());
        assertEquals(teacherDto.getEmail(), savedDetails.getEmail());
        assertEquals(publicUserId, savedDetails.getUserId());
        assertEquals(encryptedPassword, savedDetails.getEncryptedPassword());
        assertEquals(0, savedDetails.getMaxStageCanPlay());

    }

    @Test
    void createUser_teacherClassDetailsEmptyWhenExistingClassNameSpecified(){
        UserDto teacherDto = getTeacherDto();

        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(classRepository.findByClassName(anyString())).thenReturn(classEntities.get(0));
        when(roleRepository.findByRoleName(anyString())).thenReturn(teacherRole.get(0));
        when(utils.generateUserID(anyInt())).thenReturn(publicUserId);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        when(userRepository.save(any(UserEntity.class))).thenReturn(teacherEntity);

        UserDto savedDetails = userService.createUser(teacherDto, "ROLE_TEACHER", "Class A");

        assertNull(savedDetails.getClassDetails());

    }

    @Test
    void createUser_UserServiceException(){
        UserDto studentDto = getStudentDto();
        when(userRepository.findByUsername(anyString())).thenReturn(studentEntity);

        assertThrows(UserServiceException.class, ()-> userService.createUser(studentDto, "ROLE_STUDENT", "Class A"));

    }

    @Test
    void createUser_ClassServiceException () {
        UserDto studentDto = getStudentDto();
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(classRepository.findByClassName(anyString())).thenReturn(null);

        assertThrows(ClassServiceException.class, ()-> userService.createUser(studentDto, "ROLE_STUDENT", "Class A"));

    }

    @Test
    void createUser_RoleServiceException() {
        UserDto studentDto = getStudentDto();
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(classRepository.findByClassName(anyString())).thenReturn(classEntities.get(0));
        when(roleRepository.findByRoleName(anyString())).thenReturn(null);

        assertThrows(RoleServiceException.class, ()-> userService.createUser(studentDto, "ROLE_STUDENT", "Class A"));

    }

    @Test
    void updateUser() {
    }

    @Test
    void getUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(studentEntity);

        UserDto returnedDetails = userService.getUser("student");

        assertNotNull(returnedDetails);
        assertNotNull(returnedDetails.getClassDetails());
        assertEquals(studentEntity.getFirstName(), returnedDetails.getFirstName());
        assertEquals(studentEntity.getLastName(), returnedDetails.getLastName());
        assertEquals(studentEntity.getUserId(), returnedDetails.getUserId());
        assertEquals(studentEntity.getEncryptedPassword(), returnedDetails.getEncryptedPassword());
        assertEquals(studentEntity.getEmail(), returnedDetails.getEmail());
        assertEquals(studentEntity.getUsername(), returnedDetails.getUsername());
        assertEquals(classEntities.get(0).getClassName(), returnedDetails.getClassDetails().getClassName());
        assertEquals(classEntities.get(0).getClassId(), returnedDetails.getClassDetails().getClassId());
        assertEquals(0, returnedDetails.getTeachClasses().size());

    }

    @Test
    void getUser_UserServiceException(){
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        assertThrows(UserServiceException.class, ()-> userService.getUser("student"));
    }

    @Test
    void getUserByUserId() {
        when(userRepository.findByUserId(anyString())).thenReturn(studentEntity);

        UserDto returnedDetails = userService.getUserByUserId(publicUserId);

        assertNotNull(returnedDetails);
        assertNotNull(returnedDetails.getClassDetails());
        assertEquals(studentEntity.getFirstName(), returnedDetails.getFirstName());
        assertEquals(studentEntity.getLastName(), returnedDetails.getLastName());
        assertEquals(studentEntity.getUserId(), returnedDetails.getUserId());
        assertEquals(studentEntity.getEncryptedPassword(), returnedDetails.getEncryptedPassword());
        assertEquals(studentEntity.getEmail(), returnedDetails.getEmail());
        assertEquals(studentEntity.getUsername(), returnedDetails.getUsername());
        assertEquals(classEntities.get(0).getClassName(), returnedDetails.getClassDetails().getClassName());
        assertEquals(classEntities.get(0).getClassId(), returnedDetails.getClassDetails().getClassId());
        assertEquals(0, returnedDetails.getTeachClasses().size());

    }

    @Test
    void getTeacherByUserId() {
        when(userRepository.findByUserId(anyString())).thenReturn(teacherEntity);

        UserDto returnedDetails = userService.getTeacherByUserId(publicUserId);

        assertNotNull(returnedDetails);
        assertNull(returnedDetails.getClassDetails());
        assertEquals(teacherEntity.getFirstName(), returnedDetails.getFirstName());
        assertEquals(teacherEntity.getLastName(), returnedDetails.getLastName());
        assertEquals(teacherEntity.getUserId(), returnedDetails.getUserId());
        assertEquals(teacherEntity.getEncryptedPassword(), returnedDetails.getEncryptedPassword());
        assertEquals(teacherEntity.getEmail(), returnedDetails.getEmail());
        assertEquals(teacherEntity.getUsername(), returnedDetails.getUsername());
        assertEquals(2, returnedDetails.getTeachClasses().size());

    }

    @Test
    void getTeacherByUserId_userEntityWithoutTeacherRole() {
        when(userRepository.findByUserId(anyString())).thenReturn(studentEntity);

        assertThrows(UserServiceException.class, ()-> userService.getTeacherByUserId(publicUserId));
    }

    @Test
    void getTeacherByUserId_UserServiceException() {
        when(userRepository.findByUserId(anyString())).thenReturn(null);

        assertThrows(UserServiceException.class, ()-> userService.getTeacherByUserId(publicUserId));

    }

    @Test
    void loadUserByUsername() {
        when(userRepository.findByUsername(anyString())).thenReturn(studentEntity);

        UserDetails returnedDetails = userService.loadUserByUsername("student");

        assertNotNull(returnedDetails);
        assertEquals(studentEntity.getUsername(), returnedDetails.getUsername());
        assertEquals(studentEntity.getEncryptedPassword(), returnedDetails.getPassword());
        assertNotNull(returnedDetails.getAuthorities());
        assertEquals(studentEntity.getRoles().size(), returnedDetails.getAuthorities().size());

    }

    @Test
    void loadUserByUsername_UserServiceException() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        assertThrows(UserServiceException.class, ()-> userService.loadUserByUsername("student"));
    }

    @Test
    void deleteUser_UserServiceException() {
        when(userRepository.findByUserId(anyString())).thenReturn(null);

        assertThrows(UserServiceException.class, ()-> userService.deleteUser(publicUserId));

    }

    @Test
    void getUsers() {
        Pageable pageRequest = PageRequest.of(1, 5);
        List<UserEntity> users = Arrays.asList(studentEntity, teacherEntity);
        Page<UserEntity> userPage = new PageImpl<>(users, pageRequest, users.size());

        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        List<UserDto> returnedDetails = userService.getUsers(1, 5);

        assertNotNull(returnedDetails);
        assertEquals(users.size(), returnedDetails.size());

    }

    private List<ClassEntity> getClasses(){
        List<ClassEntity> classEntities = new ArrayList<>();

        ClassEntity class1 = new ClassEntity();
        class1.setClassName("Class A");
        class1.setClassId("AJSDHG4234hg3jjh");

        ClassEntity class2 = new ClassEntity();
        class2.setClassName("Class B");
        class2.setClassId("AJSDHG4234hg3jjh");

        classEntities.add(class1);
        classEntities.add(class2);

        return classEntities;
    }

    private List<RoleEntity> getTeacherRole(){
        RoleEntity teacher = new RoleEntity();
        teacher.setRoleName("ROLE_TEACHER");
        return Collections.singletonList(teacher);
    }

    private List<RoleEntity> getStudentRole(){
        RoleEntity teacher = new RoleEntity();
        teacher.setRoleName("ROLE_STUDENT");
        return Collections.singletonList(teacher);
    }

    private UserEntity getStudentEntity(){
        UserEntity studentEntity = new UserEntity();
        studentEntity.setUserId(publicUserId);
        studentEntity.setFirstName("Test");
        studentEntity.setLastName("Test");
        studentEntity.setUsername("student");
        studentEntity.setMaxStageCanPlay(0);
        studentEntity.setEmail("test@test.com");
        studentEntity.setEncryptedPassword(encryptedPassword);
        studentEntity.setClassDetails(classEntities.get(0));
        studentEntity.setRoles(studentRole);
        return studentEntity;
    }

    private UserEntity getTeacherEntity(){
        UserEntity teacherEntity = new UserEntity();
        teacherEntity.setUserId(publicUserId);
        teacherEntity.setFirstName("Test");
        teacherEntity.setLastName("Test");
        teacherEntity.setUsername("teacher");
        teacherEntity.setEmail("test@test.com");
        teacherEntity.setTeachClasses(classEntities);
        teacherEntity.setEncryptedPassword(encryptedPassword);
        teacherEntity.setRoles(teacherRole);
        return teacherEntity;
    }

    private UserDto getStudentDto(){
        UserDto studentDto = new UserDto();
        studentDto.setUsername("student");
        studentDto.setFirstName("Test");
        studentDto.setLastName("Test");
        studentDto.setPassword("student");
        studentDto.setEmail("test@test.com");

        return studentDto;
    }

    private UserDto getTeacherDto(){
        UserDto studentDto = new UserDto();
        studentDto.setUsername("teacher");
        studentDto.setFirstName("Test");
        studentDto.setLastName("Test");
        studentDto.setPassword("teacher");
        studentDto.setEmail("test@test.com");

        return studentDto;
    }
}