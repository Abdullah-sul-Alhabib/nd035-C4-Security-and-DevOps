package com.example.demo;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HelperTest {

    @Test
    public void testGetCount() {
        List<String> list = Arrays.asList("a","b");
        final long actual = Helper.getCount(list);
        assertEquals(2,actual);
    }

    @Test
    public void testGetStats() {
    }

    @Test
    public void testGetStringsOfLength3() {
    }

    @Test
    public void testGetSquareList() {
    }

    @Test
    public void testGetMergedList() {
    }

    @Test
    public void testGetFilteredList() {
    }
}