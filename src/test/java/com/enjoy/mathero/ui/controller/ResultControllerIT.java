package com.enjoy.mathero.ui.controller;

import com.enjoy.mathero.shared.CustomList;
import com.enjoy.mathero.ui.model.request.DuoResultRequestModel;
import com.enjoy.mathero.ui.model.response.DuoResultRest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResultControllerIT {

    @LocalServerPort
    private int port;

    private final Random RANDOM = new Random();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnoprtsuvwxyz";
    TestRestTemplate restTemplate = new TestRestTemplate("student1", "student");
    HttpHeaders headers = new HttpHeaders();

    @Test
    void testGetTop20() {
        ResponseEntity<CustomList> response = restTemplate.getForEntity(
                createURLWithPort("/results/top20"), CustomList.class);

        List actual = response.getBody().getWrapperList();

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertNotNull(actual);
        assertNotEquals(0, actual.size());
        assertTrue(actual.size()<=20);
    }

    @Test
    void testGetTop20Duo() {
        ResponseEntity<CustomList> response = restTemplate.getForEntity(
                createURLWithPort("/results/duo/top20"), CustomList.class);

        List actual = response.getBody().getWrapperList();

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertNotNull(actual);
        assertNotEquals(0, actual.size());
        assertTrue(actual.size()<=20);
    }

    @Test
    void testCreateDuoResult() {
        DuoResultRequestModel duoResultRequestModel = new DuoResultRequestModel();
        duoResultRequestModel.setStageNumber(2);
        duoResultRequestModel.setScore(40);

        headers.add("Content-Type", "application/json");
        HttpEntity<DuoResultRequestModel> entity = new HttpEntity<>(duoResultRequestModel, headers);

        ResponseEntity<DuoResultRest> response = restTemplate.withBasicAuth("student1", "student").exchange(
                createURLWithPort("/results/duo?userId1=HoP15nIoWYl3gzTK99Gj2362AoUpF1&userId2=d7byuZdktSpfyaCuRx2Ym4MAcfoK7E"), HttpMethod.POST, entity, DuoResultRest.class);

        DuoResultRest actual = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("HoP15nIoWYl3gzTK99Gj2362AoUpF1", actual.getUserId1());
        assertEquals("student8", actual.getUsername1());
        assertEquals("d7byuZdktSpfyaCuRx2Ym4MAcfoK7E", actual.getUserId2());
        assertEquals("student9", actual.getUsername2());
        assertEquals(2, actual.getStageNumber());
        assertEquals(40, actual.getScore());

    }

    @Test
    void testCreateDuoResult_invalidUserId() {
        DuoResultRequestModel duoResultRequestModel = new DuoResultRequestModel();
        duoResultRequestModel.setStageNumber(2);
        duoResultRequestModel.setScore(40);

        HttpEntity<DuoResultRequestModel> entity = new HttpEntity<>(duoResultRequestModel, null);

        ResponseEntity<String> response = restTemplate.withBasicAuth("student1", "student").exchange(
                createURLWithPort("/results/duo?userId1=HoP15nIoWYl3g99Gj2362AoUpF1&userId2=d7byuZdktSpfyaCuRx2Ym4MAcfoK7E"), HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

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
