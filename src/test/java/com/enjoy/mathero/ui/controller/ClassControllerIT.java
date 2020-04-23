package com.enjoy.mathero.ui.controller;

import com.enjoy.mathero.shared.CustomList;
import com.enjoy.mathero.ui.model.response.ClassRest;
import com.enjoy.mathero.ui.model.response.ClassStageSummaryRest;
import com.enjoy.mathero.ui.model.response.StageSummaryReportRest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClassControllerIT {

    @LocalServerPort
    private int port;

    private final Random RANDOM = new Random();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnoprtsuvwxyz";
    TestRestTemplate restTemplate = new TestRestTemplate("student1", "student");
    HttpHeaders headers = new HttpHeaders();

    @Test
    void testGetAllClasses() {
        ResponseEntity<CustomList> response = restTemplate.withBasicAuth("student1", "student").getForEntity(
                createURLWithPort("/classes"), CustomList.class);

        List actual = response.getBody().getWrapperList();

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertNotNull(actual);
        assertNotEquals(0, actual.size());
    }

    @Test
    void testGetClassById() {
        ResponseEntity<ClassRest> response = restTemplate.withBasicAuth("student1", "student").getForEntity(
                createURLWithPort("/classes/EFRnFJwGI742GEjvRWfFGx66OwerIi"), ClassRest.class);

        ClassRest actual = response.getBody();

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertNotNull(actual);
        assertNotEquals(0, actual.getClassName().length());
        assertNotEquals(0, actual.getClassId().length());
        assertNotEquals(0, actual.getStudents().size());
    }

    @Test
    void testGetClassById_invalidClassId() {
        ResponseEntity<ClassRest> response = restTemplate.withBasicAuth("student1", "student").getForEntity(
                createURLWithPort("/classes/EFRnFJwGIvRWfFGx66OwerIi"), ClassRest.class);


        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

    @Test
    void testGetAllClassStageSummary() {
        ResponseEntity<CustomList> response = restTemplate.getForEntity(
                createURLWithPort("/classes/EFRnFJwGI742GEjvRWfFGx66OwerIi/summary-report-all"), CustomList.class);

        List actual = response.getBody().getWrapperList();

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertNotNull(actual);
        assertNotEquals(0, actual.size());
    }

    @Test
    void testGetClassStageSummary() {
        ResponseEntity<ClassStageSummaryRest> response = restTemplate.getForEntity(
                createURLWithPort("/classes/EFRnFJwGI742GEjvRWfFGx66OwerIi/summary-report?stageNumber=2"), ClassStageSummaryRest.class);

        ClassStageSummaryRest actual = response.getBody();

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertNotNull(actual);
        assertEquals("EFRnFJwGI742GEjvRWfFGx66OwerIi", actual.getClassId());
        assertEquals(2, actual.getStageNumber());
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
