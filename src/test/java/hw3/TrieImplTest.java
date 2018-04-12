package hw3;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


public class TrieImplTest {
    private TrieImpl trie;
    private static int ELEMENTS_COUNT = 15;
    private String elements[];

    private String genWithPrefix(String prefix, int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return prefix + salt.toString();

    }

    private void makeTestTrie() {

        elements = new String[ELEMENTS_COUNT];

        elements[0] = "aa";
        trie.add(elements[0]);

        for (int i = 1; i < ELEMENTS_COUNT; i++) {
            elements[i] = genWithPrefix(elements[(i - 1) / 2], 2);
            trie.add(elements[i]);
        }
    }

    @Before
    public void setUp() {
        trie = new TrieImpl();
    }

    @After
    public void tearDown() {
        trie = null;
        elements = null;
    }

    //  ----------- SIMPLE CASES ------------
    @Test
    public void add() {
        assertTrue(trie.add("abcd"));
    }

    @Test
    public void contains() {
        makeTestTrie();

        for (String elem : elements) {
            assertTrue(trie.contains(elem));
        }
        for (String elem : elements) {
            assertFalse(trie.contains(elem.substring(0, elem.length() - 1)));
        }
    }

    @Test
    public void size() {
        makeTestTrie();
        assertEquals(ELEMENTS_COUNT, trie.size());
    }

    @Test
    public void remove() {
        makeTestTrie();

        assertTrue(trie.remove(elements[0]));
        assertFalse(trie.remove(elements[0]));
    }

    @Test
    public void howManyStartsWithPrefix() {
        makeTestTrie();
        assertEquals(ELEMENTS_COUNT, trie.howManyStartsWithPrefix(elements[0]));
        assertEquals(1, trie.howManyStartsWithPrefix(elements[ELEMENTS_COUNT - 1]));
    }

    //  ---------- COMPLEX CASES ----------
    @Test
    public void add_contains_size_remove_size_contains() {
        // add
        makeTestTrie();

        // contains
        for (String elem : elements) {
            assertTrue(trie.contains(elem));
        }
        for (String elem : elements) {
            assertFalse(trie.contains(elem.substring(0, elem.length() - 1)));
        }

        // size
        assertEquals(ELEMENTS_COUNT, trie.size());


        // remove
        int removeEvery = 3;
        int nLeaved = 0;
        for (int i = 0; i < ELEMENTS_COUNT; i++) {
            if (i % removeEvery == 0) {
                trie.remove(elements[i]);
            } else {
                nLeaved += 1;
            }
        }

        assertEquals(nLeaved, trie.size()); // size

        //contains
        for (int i = 0; i < ELEMENTS_COUNT; i++) {
            if (i % removeEvery == 0) {
                assertFalse(trie.contains(elements[i]));
            } else {
                assertTrue(trie.contains(elements[i]));
            }
        }
    }

    @Test
    public void add_contains_size_prefix_remove_size_prefix() {
        // add
        String trunk = "abcdefg";
        String branchs = "ABCDEFG";
        String elements[] = new String[trunk.length() + 1];
        trie = new TrieImpl();
        trie.add(trunk);
        elements[0] = trunk;

        for (int i = 0; i < trunk.length(); i++) {
            String tmp = trunk.substring(0, i + 1) + branchs.charAt(i);
            trie.add(tmp);
            elements[i + 1] = tmp;
        }

        // contains
        for (String elem : elements) {
            assertTrue(trie.contains(elem));
        }

        // size
        assertEquals(trunk.length() + 1, trie.size());

        //prefix
        int howManyInTrie;
        int trueValue;
        for (int i = 0; i < trunk.length(); i++) {
            howManyInTrie = trie.howManyStartsWithPrefix(trunk.substring(0, i + 1));
            trueValue = trunk.length() - i + 1;
            assertEquals(trueValue, howManyInTrie);
        }
        for (int i = 1; i < trunk.length(); i++) {
            howManyInTrie = trie.howManyStartsWithPrefix(elements[i]);
            assertEquals(1, howManyInTrie);
        }

        // remove
        int removeEvery = 2;
        int nLeaved = 0;
        for (int i = 0; i < elements.length; i++) {
            if (i % removeEvery == 0) {
                trie.remove(elements[i]);
            } else {
                nLeaved += 1;
            }
        }

        // size
        assertEquals(nLeaved, trie.size());

        // prefix
        for (int i = 0; i < trunk.length(); i++) {
            howManyInTrie = trie.howManyStartsWithPrefix(trunk.substring(0, i + 1));
            trueValue = nLeaved - (i - i / removeEvery);
            assertEquals(trueValue, howManyInTrie);
        }
        for (int i = 1; i < trunk.length(); i++) {
            howManyInTrie = trie.howManyStartsWithPrefix(elements[i]);
            if (i % removeEvery == 0) {
                assertEquals(0, howManyInTrie);
            } else {
                assertEquals(1, howManyInTrie);
            }
        }
    }

    @Test
    public void add_new_prefix() {
        trie = new TrieImpl();
        trie.add("ab");
        trie.add("bc");
        assertEquals(1, trie.howManyStartsWithPrefix("a"));
        assertEquals(1, trie.howManyStartsWithPrefix("ab"));
        assertEquals(1, trie.howManyStartsWithPrefix("b"));
        assertEquals(1, trie.howManyStartsWithPrefix("bc"));
    }

    @Test
    public void remove_without_prefix() {
        trie = new TrieImpl();
        trie.add("abcefg");
        trie.add("abc");
        trie.remove("abcefg");
        assertEquals(1, trie.size());
        assertTrue(trie.contains("abc"));
        assertFalse(trie.contains("abcefg"));
    }

    @Test
    public void double_add_size_prefix() {
        trie = new TrieImpl();
        trie.add("abc");
        trie.add("abde");
        trie.add("abde");

        assertEquals(2, trie.size());
        assertEquals(2, trie.howManyStartsWithPrefix("ab"));
        assertEquals(1, trie.howManyStartsWithPrefix("abc"));
        assertEquals(1, trie.howManyStartsWithPrefix("abd"));
    }

    @Test
    public void double_remove_size_prefix() {
        trie = new TrieImpl();
        trie.add("abc");
        trie.add("abde");
        assertTrue(trie.contains("abc"));
        assertTrue(trie.remove("abc"));
        assertFalse(trie.remove("abc"));
        assertEquals(1, trie.size());
        assertEquals(0, trie.howManyStartsWithPrefix("abc"));
        assertEquals(1, trie.howManyStartsWithPrefix("ab"));
    }


    /**
     * Serializtion Tests
     **/
    @Test
    public void serialization() throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        trie.serialize(outputStream);

        TrieImpl newTrie = new TrieImpl();
        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        newTrie.deserialize(inputStream);

        assertEquals(trie.size(), newTrie.size());

        verifyHowManyStartsWithPrefix("", trie, newTrie);

    }

    void verifyHowManyStartsWithPrefix(String prefix, TrieImpl trie1, TrieImpl trie2) {
        for (char ch = 'a'; ch <= 'z'; ch++) {
            prefix += ch;
            assertEquals(trie1.howManyStartsWithPrefix(prefix), trie2.howManyStartsWithPrefix(prefix));
            if (trie2.howManyStartsWithPrefix(prefix) > 0) {
                verifyHowManyStartsWithPrefix(prefix, trie1, trie2);
            }
        }
    }

    @Test(expected = SerializationException.class)
    public void serializtionException() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        trie.serialize(outputStream);

        byte[] bytes = outputStream.toByteArray();
        bytes[bytes.length - 3] = 4;

        InputStream inputStream = new ByteArrayInputStream(bytes);
        TrieImpl newTrie = new TrieImpl();
        newTrie.deserialize(inputStream);

        assertEquals(trie.size(), newTrie.size());
    }
}