package hw4;

import org.junit.Test;

import static org.junit.Assert.*;

public class PredicateTest {

    @Test
    public void and() {
        Predicate<Integer> isEven = x -> x % 2 == 0;
        Predicate<Integer> divisibleByThree = x -> x % 3 == 0;
        Predicate<Integer> divisibleBySix = isEven.and(divisibleByThree);
        assertTrue(divisibleBySix.apply(18));
        assertFalse(divisibleBySix.apply(9));

    }

    @Test
    public void or() {
        Predicate<Integer> isEven = x -> x % 2 == 0;
        Predicate<Integer> divisibleByThree = x -> x % 3 == 0;
        Predicate<Integer> divisibleByThreeOrTwo = isEven.or(divisibleByThree);
        assertTrue(divisibleByThreeOrTwo.apply(15));
        assertTrue(divisibleByThreeOrTwo.apply(22));
        assertFalse(divisibleByThreeOrTwo.apply(5));
    }

    @Test
    public void not() {
        Predicate<Integer> isEven = x -> x % 2 == 0;
        Predicate<Integer> isOdd = isEven.not();
        assertTrue(isOdd.apply(11));
        assertFalse(isOdd.apply(8));
    }
}