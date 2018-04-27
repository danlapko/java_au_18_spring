package hw4;

import org.junit.Test;

import static org.junit.Assert.*;

public class Function1Test {
    @Test
    public void compose() {
        Function1<Integer, String> intToStr = Object::toString;
        Function1<String, Integer> strToLength = String::length;

        Function1<Integer, Integer> intToStrToLength = strToLength.compose(intToStr);
        assertEquals((Integer) 5, intToStrToLength.apply(12345));
    }

}