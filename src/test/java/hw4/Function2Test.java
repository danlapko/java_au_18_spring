package hw4;

import org.junit.Test;

import static org.junit.Assert.*;

public class Function2Test {

    @Test
    public void compose() {
        Function2<Integer, Integer, Integer> summator = (x, y) -> x + y;
        Function1<Integer, String> intToStr = Object::toString;

        Function2<Integer, Integer, String> sumToStr = summator.compose(intToStr);
        assertEquals("3", sumToStr.apply(1, 2));
    }

    @Test
    public void bind1() {
        Function2<Integer, Integer, Double> divisor = (x, y) -> (double) x / y;
        Function1<Integer, Double> inverse = divisor.bind1(1);
        assertEquals((Double) 0.25, inverse.apply(4));
    }

    @Test
    public void bind2() {
        Function2<Integer, Integer, Double> divisor = (x, y) -> (double) x / y;
        Function1<Integer, Double> half = divisor.bind2(2);
        assertEquals((Double) 2.5, half.apply(5));
    }

    @Test
    public void curry() {
        Function2<Integer, Integer, Double> divisor = (x, y) -> (double) x / y;
        Function1<Integer, Function1<Integer, Double>> dividerFor = divisor.curry();

        Function1<Integer, Double> dividerFor100 = dividerFor.apply(100);
        assertEquals((Double) 20.0, dividerFor100.apply(5));
    }
}