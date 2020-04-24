package com.enjoy.mathero.shared;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Utils used in entities creation process
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
@Component
public class Utils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnoprtsuvwxyz";


    public String generateUserID(int length){
        return generateRandomString(length);
    }

    public String generateSoloResultID(int length){
        return generateRandomString(length);
    }

    public String generateCustomLobbyId(int length){
        return generateRandomString(length);
    }

    public String generateClassId(int length){
        return generateRandomString(length);
    }

    /**
     * Returns random string with given length
     * @param length length of string
     * @return generated string
     */
    private String generateRandomString(int length){
        StringBuilder returnValue = new StringBuilder(length);

        for(int i = 0;i <length;i++){
            returnValue.append(ALPHABET.charAt((RANDOM.nextInt(ALPHABET.length()))));
        }

        return new String(returnValue);
    }

    /**
     * Return number between given range
     * @param min lower bound
     * @param max upper bound
     * @return generated number
     */
    public int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
