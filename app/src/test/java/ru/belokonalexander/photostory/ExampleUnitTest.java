package ru.belokonalexander.photostory;

import org.junit.Test;

import static org.junit.Assert.*;
import static ru.belokonalexander.photostory.ExampleUnitTest.StrategyType.SINGLE;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    enum StrategyType {
        SINGLE, CHAIN;
    }

    @Test
    public void type_isCorrect() throws Exception {
        String s = "TAG&SINGLE";
        assertEquals(s.substring(s.lastIndexOf("&")+1,s.length()), "SINGLE");
    }

    @Test
    public void tag_isCorrect() throws Exception {
        String s = "TAG&SINGLE";
        assertEquals(s.substring(0,s.lastIndexOf("&")), "TAG");
    }

    @Test
    public void enum_isCorrect() throws Exception {



        StrategyType st = StrategyType.valueOf("SINGLE");
        assertEquals(st, SINGLE);
    }
}