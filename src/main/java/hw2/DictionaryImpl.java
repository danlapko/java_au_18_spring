package hw2;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;


public class DictionaryImpl implements Dictionary {
    static private class Pair {
        Pair(String key, String value) {
            this.key = key;
            this.value = value;
        }

        final String key;
        String value;
    }

    static private class Chain extends LinkedList<Pair> {
    }

    static private class Table extends ArrayList<Chain> {
    }


    final private int initialLength = 8;
    private int keysCnt;

    private Table table;
    private final double loadFactorUpper;
    private final double loadFactorLower;
    private int length; // table length

    public int getLength() {
        return length;
    }


    // хеш-таблица, использующая список
    // ключами и значениями выступают строки
    DictionaryImpl(@NotNull double loadFactorLower, @NotNull double loadFactorUpper) {
        this.loadFactorLower = loadFactorLower;
        this.loadFactorUpper = loadFactorUpper;
        init();
    }

    private void init() {
        table = new Table();
        for (int i = 0; i < initialLength; i++) {
            table.add(new Chain());
        }
        length = initialLength;
        keysCnt = 0;
    }


    // стандартный способ получить хеш объекта -- вызвать у него метод hashCode()
    private int hash(@NotNull String key) {
        int keyInt = key.hashCode();


        return (keyInt & 0x7FFFFFFF) % length;
    }

    private void rehashIfNecessary() {
        if ((float) keysCnt / length >= loadFactorUpper) {
            rehash(true);
        } else if ((float) keysCnt / length < loadFactorLower && length >= 2 * initialLength) {
            rehash(false);
        }
    }

    private void rehash(boolean enlargement) {
        Table oldTable = table;
        table = new Table();

        if (enlargement) {
            length *= 2;
        } else {
            length /= 2;
        }

        for (int i = 0; i < length; i++) {
            table.add(new Chain());
        }

        for (Chain chain : oldTable) {
            for (Pair pair : chain) {
                put(pair.key, pair.value);
                keysCnt--;
            }
        }
    }

    // кол-во ключей в таблице
    @Override
    public int size() {
        return keysCnt;
    }

    // true, если такой ключ содержится в таблице
    @Override
    public boolean contains(@NotNull String key) {
        return get(key) != null;
    }

    // возвращает значение, хранимое по ключу key
    // если такого нет, возвращает null
    @Override
    public @Nullable
    String get(@NotNull String key) {
        if (key == null) {
            key = "";
        }

        Chain chain = table.get(hash(key));

        for (Pair pair : chain) {
            if (key.equals(pair.key)) {
                return pair.value;
            }
        }

        return null;
    }

    // положить по ключу key значение value
    // и вернуть ранее хранимое, либо null;
    // провести рехеширование по необходимости
    @Override
    public @Nullable
    String put(@Nullable String key, @Nullable String value) {
        if (key == null) {
            key = "";
        }

        int keyHash = hash(key);

        Chain chain = table.get(keyHash);

        for (Pair pair : chain) {
            if (key.equals(pair.key)) {
                String oldValue = pair.value;
                pair.value = value;
                return oldValue;
            }
        }

        chain.add(new Pair(key, value));
        keysCnt++;
        rehashIfNecessary();
        return null;
    }

    // забыть про пару key-value для переданного key
    // и вернуть забытое value, либо null, если такой пары не было;
    // провести рехеширование по необходимости
    @Override
    public @Nullable
    String remove(@Nullable String key) {
        if (key == null) {
            key = "";
        }

        Chain chain = table.get(hash(key));
        for (Pair pair : chain)
            if (key.equals(pair.key)) {
                chain.remove(pair);
                keysCnt--;
                rehashIfNecessary();
                return pair.value;
            }
        return null;
    }

    // забыть про все пары key-value
    @Override
    public void clear() {
        for (Chain chain : table) {
            chain.clear();
        }
        table.clear();
        init();
    }
}
