package com.example.demo.model.requests;

import junit.framework.TestCase;

public class CreateUserRequestTest extends TestCase {
    private static final String USERNAME = "testUser";
    private static final String PASSWORD = "P4ssword";

    private CreateUserRequest createUserRequest;

    @Override
    public void setUp() {
        createUserRequest = new CreateUserRequest();
    }

    public void testGetUsername() {
        createUserRequest.setUsername(USERNAME);
        String testUsername = createUserRequest.getUsername();

        assertNotNull(testUsername);
        assertEquals(USERNAME, testUsername);
    }

    public void testSetUsername() {
        createUserRequest.setUsername(USERNAME);
        String testUsername = createUserRequest.getUsername();

        assertNotNull(testUsername);
        assertEquals(USERNAME, testUsername);
    }

    public void testSetPassword() {
        createUserRequest.setPassword(PASSWORD);
        String testPassword = createUserRequest.getPassword();

        assertNotNull(testPassword);
        assertEquals(PASSWORD, testPassword);
    }

    public void testSetConfirmPassword() {
        createUserRequest.setConfirmPassword(PASSWORD);
        String testConfirmPassword = createUserRequest.getConfirmPassword();

        assertNotNull(testConfirmPassword);
        assertEquals(PASSWORD, testConfirmPassword);
    }

    public void testGetPassword() {
        createUserRequest.setPassword(PASSWORD);
        String testPassword = createUserRequest.getPassword();

        assertNotNull(testPassword);
        assertEquals(PASSWORD, testPassword);
    }

    public void testGetConfirmPassword() {
        createUserRequest.setConfirmPassword(PASSWORD);
        String testConfirmPassword = createUserRequest.getConfirmPassword();

        assertNotNull(testConfirmPassword);
        assertEquals(PASSWORD, testConfirmPassword);
    }
}