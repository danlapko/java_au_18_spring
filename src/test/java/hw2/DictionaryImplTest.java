package hw2;

import com.sun.istack.internal.NotNull;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import static org.junit.Assert.*;

public class DictionaryImplTest {

    @NotNull
    private String genRandomString(String prefix, int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return prefix + salt.toString();

    }

    @Test
    public void constructor() {
        DictionaryImpl dict = new DictionaryImpl(0.25, 0.75);
        assertEquals(0, dict.size());
        assertEquals(8, dict.getLength());
        assertNull(dict.get("abc"));
        assertFalse(dict.contains("abc"));
    }

    @Test
    public void putALot_hashMapCompare_removeALot_hashMapCompare_() {
        DictionaryImpl dict = new DictionaryImpl(0.25, 0.75);
        HashMap<String, String> hashMap = new HashMap<>();

        // put a lot
        for (int i = 0; i < 10000; i++) {
            String key = genRandomString("abcdef", 2);
            String value = genRandomString("", 30);
            dict.put(key, value);
            hashMap.put(key, value);
        }

        // compare to hashMap
        assertEquals(hashMap.size(), dict.size());

        for (HashMap.Entry<String, String> entry : hashMap.entrySet()) {
            assertEquals(entry.getValue(), dict.get(entry.getKey()));
        }

        // remove a lot
        HashMap<String, String> notInDict = new HashMap<>();

        int flag = 0;
        for (Iterator<HashMap.Entry<String, String>> it = hashMap.entrySet().iterator(); it.hasNext(); ) {
            HashMap.Entry<String, String> entry = it.next();
            if ((flag++) % 2 == 0) {
                it.remove();

                assertEquals(entry.getValue(), dict.remove(entry.getKey()));
                notInDict.put(entry.getKey(), null);
            }
        }

        // compare to hashMap
        assertEquals(hashMap.size(), dict.size());

        for (HashMap.Entry<String, String> entry : hashMap.entrySet()) {
            assertEquals(entry.getValue(), dict.get(entry.getKey()));
        }

        for (HashMap.Entry<String, String> entry : notInDict.entrySet()) {
            assertNull(dict.get(entry.getKey()));
        }

    }

    @Test
    public void init_contains_put_get_update_get_remove_contains_clear_contains() {
        // init
        DictionaryImpl dict = new DictionaryImpl(0.25, 0.75);

        // contains
        assertNull(dict.get("1"));

        // put
        for (int i = 0; i < 7; i++) {
            dict.put("" + i, "" + i);
        }

        //get
        for (int i = 0; i < 7; i++) {
            assertEquals("" + i, dict.get("" + i));
        }

        //update
        for (int i = 0; i < 17; i++) {
            dict.put("" + i, "" + (i + 1));
        }

        //get
        for (int i = 0; i < 17; i++) {
            assertEquals("" + (i + 1), dict.get("" + i));
        }

        //update
        for (int i = 0; i < 17; i += 2) {
            dict.remove("" + i);
        }

        //contains
        for (int i = 0; i < 17; i++) {
            assertEquals(i % 2 == 1, dict.contains("" + i));
        }

        // clear
        dict.clear();

        //contains
        assertEquals(0, dict.size());
        for (int i = 0; i < 17; i++) {
            assertFalse(dict.contains("" + i));
        }
        assertEquals(8, dict.getLength());

    }

    @Test
    public void putTwice_size_removeTwise_size_clearTwice_size() {
        DictionaryImpl dict = new DictionaryImpl(0.25, 0.75);

        // put twice
        dict.put("a", "a");
        assertEquals("a", dict.get("a"));
        dict.put("a", "b");
        assertEquals("b", dict.get("a"));
        dict.put("c", "c");

        //size
        assertEquals(2, dict.size());

        //remove twice
        dict.remove("a");
        dict.remove("a");
        assertFalse(dict.contains("a"));

        assertEquals(1, dict.size());
    }

    //    @Test(expected = NullPointerException.class)
    @Test
    public void valueNull_get_update_get_keyNull() {
        DictionaryImpl dict = new DictionaryImpl(0.25, 0.75);

        // value null
        dict.put("a", null);

        // get
        assertEquals(1, dict.size());
        assertEquals(null, dict.get("a"));

        // update, remove, get
        dict.put("a", "b");
        assertEquals("b", dict.get("a"));
        assertEquals("b", dict.remove("a"));
        assertEquals(0, dict.size());

        // key null
        dict.put(null, "a");
        assertEquals("a", dict.remove(null));
    }
}
