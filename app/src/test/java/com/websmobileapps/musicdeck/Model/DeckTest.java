package com.websmobileapps.musicdeck.Model;

import junit.framework.TestCase;

public class DeckTest extends TestCase {
    Deck testDeck = new Deck("testCreator", "testTitle", "testSubject");

    public void testGetTitle() {
        assertEquals("testTitle", testDeck.getTitle());
    }

    public void testSetTitle() {
        testDeck.setTitle("testTitle1");
        assertEquals("testTitle1", testDeck.getTitle());
    }

    public void testGetCreator() {
        assertEquals("testCreator", testDeck.getCreator());
    }

    public void testSetCreator() {
        testDeck.setCreator("testCreator1");
        assertEquals("testCreator1", testDeck.getCreator());
    }

    public void testGetSubject() {
        assertEquals("testSubject", testDeck.getSubject());
    }

    public void testSetSubject() {
        testDeck.setSubject("testSubject1");
        assertEquals("testSubject1", testDeck.getSubject());
    }
}