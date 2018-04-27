package hw4;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CollectionsTest {

    @Test
    public void map() {
        List<Integer> l = Arrays.asList(1, 2, 3);
        Function1<Integer, Integer> func = x -> x * 2;
        Iterable<Integer> res = Collections.map(func, l);

        List<Integer> arrayList = new ArrayList<>();
        res.forEach(arrayList::add);

        assertEquals(Arrays.asList(2, 4, 6), arrayList);
    }

    @Test
    public void filter() {
        List<Integer> l = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Predicate<Integer> pred = x -> x % 2 == 0;
        Iterable<Integer> res = Collections.filter(pred, l);

        List<Integer> arrayList = new ArrayList<>();
        res.forEach(arrayList::add);

        assertEquals(Arrays.asList(2, 4, 6, 8), arrayList);
    }

    @Test
    public void takeWhile() {
        List<Integer> l = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Predicate<Integer> pred = x -> x < 5;
        Iterable<Integer> res = Collections.takeWhile(pred, l);

        List<Integer> arrayList = new ArrayList<>();
        res.forEach(arrayList::add);

        assertEquals(Arrays.asList(1, 2, 3, 4), arrayList);
    }

    @Test
    public void takeUnless() {
        List<Integer> l = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Predicate<Integer> pred = x -> x == 5;
        Iterable<Integer> res = Collections.takeUnless(pred, l);

        List<Integer> arrayList = new ArrayList<>();
        res.forEach(arrayList::add);

        assertEquals(Arrays.asList(1, 2, 3, 4), arrayList);
    }

    @Test
    public void foldl() {
        List<Integer> l = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Function2<Integer, Integer, Integer> func = (ini, y) -> ini + y;
        Integer res = Collections.foldl(func, 0, l);

        assertEquals((Integer) 45, res);
    }

    @Test
    public void foldr() {
        List<Integer> l = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Function2<Integer, Integer, Integer> func = (ini, y) -> ini + y;
        Integer res = Collections.foldr(func, 0, l);

        assertEquals((Integer) 45, res);
    }
}