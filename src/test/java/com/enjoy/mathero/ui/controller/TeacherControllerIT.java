package com.enjoy.mathero.ui.controller;

import com.enjoy.mathero.ui.model.response.TeacherRest;
import com.enjoy.mathero.ui.model.response.UserRest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Random;

import static org.junit.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TeacherControllerIT {

    @LocalServerPort
    private int port;

    private final Random RANDOM = new Random();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnoprtsuvwxyz";
    TestRestTemplate restTemplate = new TestRestTemplate("student1", "student");
    HttpHeaders headers = new HttpHeaders();

    @Test
    void testGetUser(){
        TeacherRest expected = new TeacherRest();
        expected.setUserId("k18Wx9f1fiwKOiALEV1jg7woMCcJ8r");
        expected.setFirstName("John");
        expected.setLastName("Danish");
        expected.setTeachClassId("EFRnFJwGI742GEjvRWfFGx66OwerIi");
        expected.setTeachClassName("Class A");

        ResponseEntity<TeacherRest> response = restTemplate.getForEntity(
                createURLWithPort("/teachers/k18Wx9f1fiwKOiALEV1jg7woMCcJ8r"), TeacherRest.class);

        TeacherRest actual = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(actual);
        assertEquals(expected.getUserId(), actual.getUserId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getTeachClassId(), actual.getTeachClassId());
        assertEquals(expected.getTeachClassName() , actual.getTeachClassName());

    }

    @Test
    void testGetUser_userWithGivenIdDoesNotExist(){
        ResponseEntity<LinkedHashMap> response = restTemplate.getForEntity(
                createURLWithPort("/teachers/g8YzHXnQTQgU5SrQUwQJ4zhV"), LinkedHashMap.class);


        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody().get("errors"));

    }

    private String createURLWithPort(String uri){
        return "http://localhost:" + port + "/mathero" + uri;
    }

    private String generateRandomString(int length){
        StringBuilder returnValue = new StringBuilder(length);

        for(int i = 0;i <length;i++){
            returnValue.append(ALPHABET.charAt((RANDOM.nextInt(ALPHABET.length()))));
        }

        return new String(returnValue);
    }
}
