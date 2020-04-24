package com.enjoy.mathero.security;

/**
 * Constant values to keep code more readable
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864000000;
    public static final String TOKEN_PREFIX = "Masniutko ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";
    public static final String LOGIN_URL = "/users/login";
    public static final String CREATE_TEACHER_URL = "/teachers";
    public static final String CREATE_CLASS_URL = "/classes";
    public static final String TOKEN_SECRET = "jf9i4jgu83nfl0";
}
