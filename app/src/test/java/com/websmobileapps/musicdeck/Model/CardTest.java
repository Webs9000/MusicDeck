package com.websmobileapps.musicdeck.Model;

import junit.framework.TestCase;

public class CardTest extends TestCase {
    Card testCard = new Card("testTitle", "testArtist", 0, "testURL", "testDate",
            42, "testTrack");

    public void testGetTitle() {
        assertEquals("testTitle", testCard.getTitle());
    }

    public void testSetTitle() {
        testCard.setTitle("testTitle1");
        assertEquals("testTitle1", testCard.getTitle());
    }

    public void testGetArtist() {
        assertEquals("testArtist", testCard.getArtist());
    }

    public void testSetArtist() {
        testCard.setArtist("testArtist1");
        assertEquals("testArtist1", testCard.getTitle());
    }

    public void testGetListRank() {
        assertEquals(0, testCard.getListRank());
    }

    public void testSetListRank() {
        testCard.setListRank(1);
        assertEquals(1, testCard.getListRank());
    }

    public void testGetArtURL() {
        assertEquals("testURL", testCard.getArtURL());
    }

    public void testSetArtURL() {
        testCard.setArtURL("testArtURL1");
        assertEquals("testArtURL1", testCard.getArtURL());
    }

    public void testGetPublicationDate() {
        assertEquals("testDate", testCard.getPublicationDate());
    }

    public void testSetPublicationDate() {
        testCard.setPublicationDate("testPublicationDate1");
        assertEquals("testPublicationDate1", testCard.getPublicationDate());
    }

    public void testGetRating() {
        assertEquals(42, testCard.getRating());
    }

    public void testSetRating() {
        testCard.setRating(1);
        assertEquals(1, testCard.getRating());
    }

    public void testGetFavTrack() {
        assertEquals("testTrack", testCard.getFavTrack());
    }

    public void testSetFavTrack() {
        testCard.setFavTrack("testFavTrack1");
        assertEquals("testFavTrack1", testCard.getFavTrack());
    }
}