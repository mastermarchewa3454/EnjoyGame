package com.enjoy.mathero;

import com.enjoy.mathero.io.entity.ClassEntity;
import com.enjoy.mathero.io.entity.RoleEntity;
import com.enjoy.mathero.io.entity.UserEntity;
import com.enjoy.mathero.io.repository.ClassRepository;
import com.enjoy.mathero.io.repository.RoleRepository;
import com.enjoy.mathero.io.repository.UserRepository;
import com.enjoy.mathero.shared.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import java.util.Arrays;

@Component
public class InitialSetup {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClassRepository classRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    Utils utils;

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event){
        RoleEntity admin = createRole("ROLE_ADMIN");
        RoleEntity student = createRole("ROLE_STUDENT");
        RoleEntity teacher = createRole("ROLE_TEACHER");

        UserEntity teacher1 = createTeacher("teacher1", "John", "Danish", "teacher", "teacher1@email.com", teacher);
        UserEntity teacher2 = createTeacher("teacher2", "Ben", "Spanish", "teacher", "teacher2@email.com", teacher);
        UserEntity teacher3 = createTeacher("teacher3", "Kate", "Vanish", "teacher", "teacher3@email.com", teacher);

        ClassEntity class1 = createClass("Class A", teacher1);
        ClassEntity class2 = createClass("Class B", teacher2);
        ClassEntity class3 = createClass("Class C", teacher3);

    }

    private RoleEntity createRole(String name){
        RoleEntity returnValue = new RoleEntity();

        if(roleRepository.findByRoleName(name) == null){
            returnValue.setRoleName(name);
            roleRepository.save(returnValue);
        }

        return returnValue;
    }

    private UserEntity createTeacher(String username, String firstName, String lastName, String password, String email, RoleEntity role){
        UserEntity returnValue = new UserEntity();

        if(userRepository.findByUsername(username) == null){
            returnValue.setUsername(username);
            returnValue.setFirstName(firstName);
            returnValue.setLastName(lastName);
            returnValue.setEncryptedPassword(bCryptPasswordEncoder.encode(password));
            returnValue.setEmail(email);
            returnValue.setUserId(utils.generateUserID(30));
            returnValue.setRoles(Arrays.asList(role));
            userRepository.save(returnValue);
        }

        return returnValue;
    }

    private ClassEntity createClass(String className, UserEntity teacherDetails){
        ClassEntity returnValue = new ClassEntity();

        if(classRepository.findByClassName(className) == null){
            returnValue.setTeacherDetails(teacherDetails);
            returnValue.setClassName(className);
            classRepository.save(returnValue);
        }

        return returnValue;
    }

}
