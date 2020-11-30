package com.websmobileapps.musicdeck.Model;

import junit.framework.TestCase;

public class DatabaseUserTest extends TestCase {

    DatabaseUser testUser = new DatabaseUser("testName");
    public void testGetUsername() {
        assertEquals("testName", testUser.getUsername());
    }

    public void testSetUsername() {
        testUser.setUsername("testName1");
        assertEquals("testName1", testUser.getUsername());
    }
}