package com.enjoy.mathero;

import com.enjoy.mathero.io.entity.*;
import com.enjoy.mathero.io.repository.*;
import com.enjoy.mathero.shared.Utils;
import com.enjoy.mathero.ui.model.response.SoloResultRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class InitialSetup {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClassRepository classRepository;

    @Autowired
    SoloResultRepository soloResultRepository;

    @Autowired
    DuoResultRepository duoResultRepository;

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

        List<UserEntity> students = new ArrayList<>();

        UserEntity student1 = createStudent("student1", "Tazar", "Crag", "student", "student1@email.com", 0, class1, student);
        UserEntity student2 = createStudent("student2", "Benny", "Hill", "student", "student2@email.com", 1, class2, student);
        UserEntity student3 = createStudent("student3", "Harry", "Potter", "student", "student3@email.com", 2, class3, student);
        UserEntity student4 = createStudent("student4", "Robert", "Lewandowski", "student", "student4@email.com", 3, class1, student);
        UserEntity student5 = createStudent("student5", "Artur", "Skowronek", "student", "student5@email.com", 2, class2, student);
        UserEntity student6 = createStudent("student6", "Hermiona", "Granger", "student", "student6@email.com", 0, class3, student);
        UserEntity student7 = createStudent("student7", "Twoja", "Mama", "student", "student7@email.com", 0, class1, student);
        UserEntity student8 = createStudent("student8", "Mlody", "Sarmata", "student", "student8@email.com", 3, class2, student);
        UserEntity student9 = createStudent("student9", "Kuba", "Grabowski", "student", "student9@email.com", 0, class3, student);
        UserEntity student10 = createStudent("student10", "Quebona", "Fide", "student", "student10@email.com", 0, class1, student);
        UserEntity student11 = createStudent("student11", "Taco", "Hemingway", "student", "student11@email.com", 0, class2, student);
        UserEntity student12 = createStudent("student12", "Adam", "Mickiewicz", "student", "student12@email.com", 0, class3, student);
        UserEntity student13 = createStudent("student13", "Andrzej", "Duda", "student", "student13@email.com", 0, class1, student);
        UserEntity student14 = createStudent("student14", "Jarek", "Kaczka", "student", "student14@email.com", 0, class2, student);

        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        students.add(student6);
        students.add(student7);
        students.add(student8);
        students.add(student9);
        students.add(student10);
        students.add(student11);
        students.add(student12);
        students.add(student13);
        students.add(student14);

        /*for(int i =0;i<100;i++){
            UserEntity firstStudent = students.get(utils.getRandomNumberInRange(0,13));
            UserEntity secondStudent = students.get(utils.getRandomNumberInRange(0,13));

            SoloResultEntity soloResultEntity = createSoloResult(firstStudent);
            if(!firstStudent.getUserId().equals(secondStudent.getUserId())){
                DuoResultEntity duoResultEntity = createDuoResult(firstStudent, secondStudent);
            }

        }*/


    }

    private RoleEntity createRole(String name){
        RoleEntity returnValue = new RoleEntity();
        RoleEntity roleEntity = roleRepository.findByRoleName(name);

        if(roleEntity == null){
            returnValue.setRoleName(name);
            roleRepository.save(returnValue);
        }
        else{
            returnValue = roleEntity;
        }

        return returnValue;
    }

    private UserEntity createTeacher(String username, String firstName, String lastName, String password, String email, RoleEntity role){
        UserEntity returnValue = new UserEntity();
        UserEntity userEntity = userRepository.findByUsername(username);

        if(userEntity == null){
            returnValue.setUsername(username);
            returnValue.setFirstName(firstName);
            returnValue.setLastName(lastName);
            returnValue.setEncryptedPassword(bCryptPasswordEncoder.encode(password));
            returnValue.setEmail(email);
            returnValue.setUserId(utils.generateUserID(30));
            returnValue.setRoles(Arrays.asList(role));
            userRepository.save(returnValue);
        }
        else{
            returnValue = userEntity;
        }

        return returnValue;
    }

    private ClassEntity createClass(String className, UserEntity teacherDetails){
        ClassEntity returnValue = new ClassEntity();
        ClassEntity classEntity = classRepository.findByClassName(className);

        if(classEntity == null){
            returnValue.setClassId(utils.generateClassId(30));
            returnValue.setTeacherDetails(teacherDetails);
            returnValue.setClassName(className);
            classRepository.save(returnValue);
        }
        else{
            returnValue = classEntity;
        }

        return returnValue;
    }

    private UserEntity createStudent(String username, String firstName, String lastName, String password, String email, int maxStageCanPlay, ClassEntity classDetails, RoleEntity role){
        UserEntity returnValue = new UserEntity();
        UserEntity userEntity = userRepository.findByUsername(username);

        if(userEntity == null){
            returnValue.setUsername(username);
            returnValue.setFirstName(firstName);
            returnValue.setLastName(lastName);
            returnValue.setEncryptedPassword(bCryptPasswordEncoder.encode(password));
            returnValue.setEmail(email);
            returnValue.setUserId(utils.generateUserID(30));
            returnValue.setMaxStageCanPlay(maxStageCanPlay);
            returnValue.setClassDetails(classDetails);
            returnValue.setRoles(Arrays.asList(role));
            userRepository.save(returnValue);
        }
        else {
            returnValue = userEntity;
        }

        return returnValue;
    }

    private SoloResultEntity createSoloResult(UserEntity userDetails){
        SoloResultEntity returnValue = new SoloResultEntity();

        returnValue.setResultId(utils.generateSoloResultID(30));
        returnValue.setStageNumber(utils.getRandomNumberInRange(1,5));
        returnValue.setScore(utils.getRandomNumberInRange(20,1000));
        returnValue.setEasyTotal(utils.getRandomNumberInRange(10,20));
        returnValue.setEasyCorrect(utils.getRandomNumberInRange(0,returnValue.getEasyTotal()));
        returnValue.setMediumTotal(utils.getRandomNumberInRange(10,20));
        returnValue.setMediumCorrect(utils.getRandomNumberInRange(0,returnValue.getMediumTotal()));
        returnValue.setHardTotal(utils.getRandomNumberInRange(10,20));
        returnValue.setHardCorrect(utils.getRandomNumberInRange(0,returnValue.getHardTotal()));
        returnValue.setUserDetails(userDetails);
        soloResultRepository.save(returnValue);

        return returnValue;
    }

    private DuoResultEntity createDuoResult(UserEntity userDetails1, UserEntity userDetails2){
        DuoResultEntity returnValue = new DuoResultEntity();

        returnValue.setResultId(utils.generateSoloResultID(30));
        returnValue.setStageNumber(utils.getRandomNumberInRange(1,5));
        returnValue.setScore(utils.getRandomNumberInRange(20,1000));
        returnValue.setUserDetails1(userDetails1);
        returnValue.setUserDetails2(userDetails2);
        duoResultRepository.save(returnValue);

        return returnValue;
    }

}
