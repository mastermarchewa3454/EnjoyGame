package com.enjoy.mathero.ui.controller;

import com.enjoy.mathero.shared.CustomList;
import com.enjoy.mathero.shared.Utils;
import com.enjoy.mathero.ui.model.request.MaxStageRequestModel;
import com.enjoy.mathero.ui.model.request.SoloResultRequestModel;
import com.enjoy.mathero.ui.model.request.UserDetailsRequestModel;
import com.enjoy.mathero.ui.model.response.OperationStatusModel;
import com.enjoy.mathero.ui.model.response.SoloResultRest;
import com.enjoy.mathero.ui.model.response.StageSummaryReportRest;
import com.enjoy.mathero.ui.model.response.UserRest;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Integration tests for UserController class
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIT {

    @LocalServerPort
    private int port;

    private final Random RANDOM = new Random();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnoprtsuvwxyz";
    TestRestTemplate restTemplate = new TestRestTemplate("student1","student");
    HttpHeaders headers = new HttpHeaders();

    @Test
    void testCreateUser() {
        UserDetailsRequestModel userDetailsRequestModel = new UserDetailsRequestModel();
        userDetailsRequestModel.setUsername(generateRandomString(10));
        userDetailsRequestModel.setFirstName("Test");
        userDetailsRequestModel.setLastName("Test");
        userDetailsRequestModel.setPassword("password");
        userDetailsRequestModel.setEmail("email@email.com");
        userDetailsRequestModel.setClassName("Class A");

        HttpEntity<UserDetailsRequestModel> entity = new HttpEntity<UserDetailsRequestModel>(userDetailsRequestModel, headers);

        ResponseEntity<UserRest> response = restTemplate.exchange(createURLWithPort("/users"), HttpMethod.POST, entity, UserRest.class);

        UserRest actual = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDetailsRequestModel.getUsername(), actual.getUsername());
        assertNotNull(actual.getUserId());

    }

    @Test
    void testCreateUser_userAlreadyExists(){
        UserDetailsRequestModel userDetailsRequestModel = new UserDetailsRequestModel();
        userDetailsRequestModel.setUsername("student1");
        userDetailsRequestModel.setFirstName("Test");
        userDetailsRequestModel.setLastName("Test");
        userDetailsRequestModel.setPassword("password");
        userDetailsRequestModel.setEmail("email@email.com");
        userDetailsRequestModel.setClassName("Class A");

        HttpEntity<UserDetailsRequestModel> entity = new HttpEntity<UserDetailsRequestModel>(userDetailsRequestModel, headers);

        ResponseEntity<LinkedHashMap> response = restTemplate.exchange(createURLWithPort("/users"), HttpMethod.POST, entity, LinkedHashMap.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody().get("errors"));

    }

    @Test
    void testCreateUser_invalidRequestModel(){
        UserDetailsRequestModel userDetailsRequestModel = new UserDetailsRequestModel();
        userDetailsRequestModel.setUsername(generateRandomString(10));
        userDetailsRequestModel.setFirstName("Test");
        userDetailsRequestModel.setLastName("Test");
        userDetailsRequestModel.setPassword("password");
        userDetailsRequestModel.setEmail("emailemail.com");
        userDetailsRequestModel.setClassName("Class A");

        HttpEntity<UserDetailsRequestModel> entity = new HttpEntity<UserDetailsRequestModel>(userDetailsRequestModel, headers);

        ResponseEntity<LinkedHashMap> response = restTemplate.exchange(createURLWithPort("/users"), HttpMethod.POST, entity, LinkedHashMap.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody().get("errors"));
    }

    @Test
    void testCreateUser_requestModelWithEmptyFields(){
        UserDetailsRequestModel userDetailsRequestModel = new UserDetailsRequestModel();
        userDetailsRequestModel.setFirstName("Test");
        userDetailsRequestModel.setLastName("Test");
        userDetailsRequestModel.setPassword("password");
        userDetailsRequestModel.setEmail("emailemail.com");
        userDetailsRequestModel.setClassName("Class A");

        HttpEntity<UserDetailsRequestModel> entity = new HttpEntity<UserDetailsRequestModel>(userDetailsRequestModel, headers);

        ResponseEntity<LinkedHashMap> response = restTemplate.exchange(createURLWithPort("/users"), HttpMethod.POST, entity, LinkedHashMap.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody().get("errors"));
    }

    @Test
    void testGetUser(){
        UserRest expected = new UserRest();
        expected.setUserId("HoP15nIoWYl3gzTK99Gj2362AoUpF1");
        expected.setUsername("student8");
        expected.setFirstName("Mlody");
        expected.setLastName("Sarmata");
        expected.setEmail("student8@email.com");
        expected.setClassName("Class B");

        ResponseEntity<UserRest> response = restTemplate.getForEntity(
                createURLWithPort("/users/HoP15nIoWYl3gzTK99Gj2362AoUpF1"), UserRest.class);

        UserRest actual = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(actual);
        assertEquals(expected.getUserId(), actual.getUserId());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getClassName(), actual.getClassName());

    }

    @Test
    void testGetUser_userWithGivenIdDoesNotExist(){
        ResponseEntity<LinkedHashMap> response = restTemplate.getForEntity(
                createURLWithPort("/users/g8YzHXnQTQgU5SrQUwQJ4zhV"), LinkedHashMap.class);


        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody().get("errors"));

    }

    @Test
    void testGetUsers() {
        ResponseEntity<CustomList> response = restTemplate.getForEntity(
                createURLWithPort("/users"), CustomList.class);

        List actual = response.getBody().getWrapperList();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(actual);
        assertNotEquals(0, actual.size());
    }

    @Test
    void testGetUserSoloResults() {
        ResponseEntity<CustomList> response = restTemplate.getForEntity(
                createURLWithPort("/users/HoP15nIoWYl3gzTK99Gj2362AoUpF1/results"), CustomList.class);

        List actual = response.getBody().getWrapperList();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(actual);
        assertNotEquals(0, actual.size());
    }

    @Test
    void testGetUserSoloResults_invalidUserId() {
        ResponseEntity<LinkedHashMap> response = restTemplate.getForEntity(
                createURLWithPort("/users/g8YzHXnQTQgU5QJ4zhV/results"), LinkedHashMap.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
        assertNotNull(response.getBody().get("errors"));

    }

    @Test
    void testSetMaxStageNumber() {
        MaxStageRequestModel maxStageRequestModel = new MaxStageRequestModel();
        maxStageRequestModel.setMaxStageCanPlay(3);

        HttpEntity<MaxStageRequestModel> entity = new HttpEntity<MaxStageRequestModel>(maxStageRequestModel, headers);

        ResponseEntity<OperationStatusModel> response = restTemplate.exchange(
                createURLWithPort("/users/HoP15nIoWYl3gzTK99Gj2362AoUpF1/max-stage"), HttpMethod.POST, entity, OperationStatusModel.class);

        OperationStatusModel result = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SUCCESS", result.getOperationResult());
        assertEquals("MAX_STAGE_NUMBER", result.getOperationName());
    }

    @Test
    void testSetMaxStageNumber_invalidUserId() {
        MaxStageRequestModel maxStageRequestModel = new MaxStageRequestModel();
        maxStageRequestModel.setMaxStageCanPlay(3);

        HttpEntity<MaxStageRequestModel> entity = new HttpEntity<MaxStageRequestModel>(maxStageRequestModel, headers);

        ResponseEntity<LinkedHashMap> response = restTemplate.exchange(
                createURLWithPort("/users/g8YzHXnQTQgUwQJ4zhV/max-stage"), HttpMethod.POST, entity, LinkedHashMap.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody().get("errors"));
    }

    @Test
    void testCreateSoloResult() {
        SoloResultRequestModel soloResultRequestModel = new SoloResultRequestModel();
        soloResultRequestModel.setStageNumber(2);
        soloResultRequestModel.setScore(40);
        soloResultRequestModel.setEasyCorrect(10);
        soloResultRequestModel.setEasyTotal(12);
        soloResultRequestModel.setMediumTotal(15);
        soloResultRequestModel.setMediumCorrect(10);
        soloResultRequestModel.setHardTotal(7);
        soloResultRequestModel.setHardCorrect(3);

        HttpEntity<SoloResultRequestModel> entity = new HttpEntity<>(soloResultRequestModel, null);

        ResponseEntity<SoloResultRest> response = restTemplate.withBasicAuth("student1", "student").exchange(
                createURLWithPort("/users/HoP15nIoWYl3gzTK99Gj2362AoUpF1/results"), HttpMethod.POST, entity, SoloResultRest.class);

        SoloResultRest actual = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10, actual.getEasyCorrect());
        assertEquals("HoP15nIoWYl3gzTK99Gj2362AoUpF1", actual.getUserId());

    }

    @Test
    void testCreateSoloResult_invalidUserId() {
        SoloResultRequestModel soloResultRequestModel = new SoloResultRequestModel();
        soloResultRequestModel.setStageNumber(2);
        soloResultRequestModel.setScore(40);
        soloResultRequestModel.setEasyCorrect(10);
        soloResultRequestModel.setEasyTotal(12);
        soloResultRequestModel.setMediumTotal(15);
        soloResultRequestModel.setMediumCorrect(10);
        soloResultRequestModel.setHardTotal(7);
        soloResultRequestModel.setHardCorrect(3);

        HttpEntity<SoloResultRequestModel> entity = new HttpEntity<>(soloResultRequestModel, headers);

        ResponseEntity<SoloResultRest> response = restTemplate.exchange(
                createURLWithPort("/users/HoP15nIoWYl99Gj2362AoUpF1/results"), HttpMethod.POST, entity, SoloResultRest.class);

        SoloResultRest actual = response.getBody();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testCreateSoloResult_invalidRequestModel() {
        SoloResultRequestModel soloResultRequestModel = new SoloResultRequestModel();
        soloResultRequestModel.setStageNumber(2);
        soloResultRequestModel.setEasyCorrect(10);
        soloResultRequestModel.setEasyTotal(12);
        soloResultRequestModel.setMediumTotal(15);
        soloResultRequestModel.setMediumCorrect(10);
        soloResultRequestModel.setHardTotal(7);
        soloResultRequestModel.setHardCorrect(3);

        HttpEntity<SoloResultRequestModel> entity = new HttpEntity<>(soloResultRequestModel, headers);

        ResponseEntity<SoloResultRest> response = restTemplate.exchange(
                createURLWithPort("/users/g8YzHTQgU5SrQUwQJ4zhV/results"), HttpMethod.POST, entity, SoloResultRest.class);

        SoloResultRest actual = response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testGetAllStageSummaryReport() {
        ResponseEntity<CustomList> response = restTemplate.getForEntity(
                createURLWithPort("/users/HoP15nIoWYl3gzTK99Gj2362AoUpF1/summary-report-all"), CustomList.class);

        List actual = response.getBody().getWrapperList();

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertNotNull(actual);
        assertNotEquals(0, actual.size());
    }

    @Test
    void testGetStageSummaryReport() {
        ResponseEntity<StageSummaryReportRest> response = restTemplate.getForEntity(
                createURLWithPort("/users/HoP15nIoWYl3gzTK99Gj2362AoUpF1/summary-report?stageNumber=2"), StageSummaryReportRest.class);

        StageSummaryReportRest actual = response.getBody();

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertNotNull(actual);
        assertEquals("HoP15nIoWYl3gzTK99Gj2362AoUpF1", actual.getUserId());
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
