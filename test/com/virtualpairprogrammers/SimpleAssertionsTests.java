package com.virtualpairprogrammers;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleAssertionsTests {

    @Test
    public void testAssertArrayEquals() {
        byte[] expected = "test".getBytes();
        byte[] actual = "test".getBytes();

        assertArrayEquals("failure - byte arrays are not the same", expected, actual);
    }

    @Test
    public void testAssertFalse() {
        assertFalse("failure - should be false", false);
    }

    @Test
    public void testAssertTrue() {
        assertTrue("failure - should be true", true);
    }

    @Test
    public void testAssertNotNull() {
        assertNotNull("should not be null", new Object());
    }

    @Test
    public void testAssertNull() {
        assertNull("should be null", null);
    }

    @Test
    public void testAssertNotSame() {
        assertNotSame("should not be same Objects", new Object(), new Object());
    }

    @Test
    public void testAssertSame() {
        Integer num = Integer.valueOf(66);

        assertSame("should be same", num, num);
    }
}
