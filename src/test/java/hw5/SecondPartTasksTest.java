package hw5;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static hw5.SecondPartTasks.*;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.fail;

public class SecondPartTasksTest {

    @Test
    public void testFindQuotes() throws IOException {
        Path p1 = Paths.get("f1.txt");
        Path p2 = Paths.get("f2.txt");
        Path p3 = Paths.get("f3.txt");
//        Files.delete(p1);
//        Files.delete(p2);
//        Files.delete(p3);

        Files.createFile(p1);
        Files.createFile(p2);
        Files.createFile(p3);

        FileWriter out1 = new FileWriter(p1.toFile());
        out1.write("mama mila ramu\nparticular party participation ");
        out1.close();

        FileWriter out2 = new FileWriter(p2.toFile());
        out2.write("milllion mila abc\nbillion \n ten mila");
        out2.close();

        FileWriter out3 = new FileWriter(p3.toFile());
        out3.write("a mila abc\nmariya abc\n mariya and mila");
        out3.close();

        assertEquals(Arrays.asList("mama mila ramu",
                "milllion mila abc", " ten mila", "a mila abc", " mariya and mila"),
                findQuotes(Arrays.asList(p1.toString(),
                        p2.toString(), p3.toString()), "mila"));

        Files.delete(p1);
        Files.delete(p2);
        Files.delete(p3);
    }

    @Test
    public void testPiDividedBy4() {
        assertEquals(Math.PI / 4, piDividedBy4(), 1e-2);
    }

    @Test
    public void testFindPrinter() {
        Map<String, List<String>> authorToBook = new HashMap<>();
        authorToBook.put("IVANOV", Arrays.asList("odin dva", "tri",
                "4 5 6 7 8 9 zero", "four"));
        authorToBook.put("PETROV", Arrays.asList("aaa bbbb ccccccccccc ddddddd",
                "aaa bbbb ccccccccccc ddddddd", "aaa bbbb ccccccccccc ddddddd"));

        assertEquals("PETROV", findPrinter(authorToBook));
    }

    @Test
    public void testCalculateGlobalOrder() {
        Map<String, Integer> shop1 = new HashMap<>();
        shop1.put("v1", 2);
        shop1.put("v3", 2);

        Map<String, Integer> shop2 = new HashMap<>();
        shop2.put("v2", 1);
        shop2.put("v3", 7);

        Map<String, Integer> true_result = new HashMap<>();
        true_result.put("v1", 2);
        true_result.put("v2", 1);
        true_result.put("v3", 9);
        assertEquals(true_result,  calculateGlobalOrder(Arrays.asList(shop1, shop2)));
    }
}