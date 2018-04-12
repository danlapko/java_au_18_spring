package hw3;

import com.sun.istack.internal.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class Node {

    static final int ALPHABET_SIZE = 52;
    final Node[] children = new Node[ALPHABET_SIZE];
    boolean isTerminal = false;
    int numberOfSubTerminals = 0;


}

public class TrieImpl implements Trie, StreamSerializable {
    private Node root;

    private static int charToInt(char ch) {
        if ('a' <= ch && ch <= 'z') {
            return ch - 'a';
        }
        if ('A' <= ch && ch <= 'Z') {
            return 26 + ch - 'A';
        }
        throw new IllegalArgumentException("Character is expected to be in range 'a'-'z','A'-'Z'");
    }

    TrieImpl() {
        root = new Node();
    }

    /**
     * Expected complexity: O(|element|)
     *
     * @return <tt>true</tt> if this set did not already contain the specified
     * element
     */
    @Override
    public boolean add(@Nullable String element) {
        if (element == null || contains(element)) {
            return false;
        }

        Node node = root;
        root.numberOfSubTerminals++;

        for (int i = 0; i < element.length(); i++) {
            int ch = charToInt(element.charAt(i));

            Node nextNode = node.children[ch];
            if (nextNode == null) {
                nextNode = new Node();
                node.children[ch] = nextNode;
            }
            nextNode.numberOfSubTerminals++;
            node = nextNode;
        }

        node.isTerminal = true;

        return true;
    }

    /**
     * Expected complexity: O(|element|)
     */
    @Override
    public boolean contains(@Nullable String element) {
        if (element == null) {
            return false;
        }

        Node node = root;

        for (int i = 0; i < element.length(); i++) {
            int ch = charToInt(element.charAt(i));

            Node nextNode = node.children[ch];
            if (nextNode == null) {
                return false;
            }

            node = nextNode;
        }
        return node.isTerminal;
    }

    /**
     * Expected complexity: O(|element|)
     *
     * @return <tt>true</tt> if this set contained the specified element
     */
    @Override
    public boolean remove(@Nullable String element) {
        if (element == null || !contains(element)) {
            return false;
        }

        Node node = root;
        root.numberOfSubTerminals--;

        for (int i = 0; i < element.length(); i++) {
            int ch = charToInt(element.charAt(i));
            Node subnode = node.children[ch];
            subnode.numberOfSubTerminals--;
            if (subnode.numberOfSubTerminals == 0) {
                node.children[ch] = null;
                return true;
            }
            node = subnode;
        }

        assert node.isTerminal;
        node.isTerminal = false;

        return true;
    }

    /**
     * Expected complexity: O(1)
     */
    @Override
    public int size() {
        return root.numberOfSubTerminals;
    }

    /**
     * Expected complexity: O(|prefix|)
     */
    @Override
    public int howManyStartsWithPrefix(@Nullable String prefix) {
        if (prefix == null) {
            return 0;
        }
        Node node = root;
        for (int i = 0; i < prefix.length(); i++) {
            int ch = charToInt(prefix.charAt(i));
            node = node.children[ch];
            if (node == null) {
                return 0;
            }
        }

        return node.numberOfSubTerminals;
    }


    static class Serializer {

        protected final static byte NULL_NODE = 0;
        protected final static byte NOT_TERMINAL = 1;
        protected final static byte TERMINAL = 2;

        static void serialize(Node node, OutputStream out) throws IOException {
            if (node == null) {
                out.write(new byte[]{NULL_NODE});
                return;
            }
            out.write(new byte[]{node.isTerminal ? TERMINAL : NOT_TERMINAL});
            for (Node child : node.children) {
                serialize(child, out);
            }
        }


        static Node deserialize(InputStream in) throws IOException {
            int type = in.read();


            if (type == -1) {
                throw new hw3.SerializationException();
            }
            if (type == NULL_NODE) {
                return null;
            }
            Node result = new Node();
            if (type == TERMINAL) {
                result.isTerminal = true;
                result.numberOfSubTerminals++;
            } else if (type == NOT_TERMINAL) {
                result.isTerminal = false;
            } else {
                throw new hw3.SerializationException();
            }
            for (int i = 0; i < Node.ALPHABET_SIZE; i++) {
                result.children[i] = deserialize(in);
                if (result.children[i] != null) {
                    result.numberOfSubTerminals += result.children[i].numberOfSubTerminals;
                }
            }
            return result;
        }
    }


    @Override
    public void serialize(OutputStream out) {
        try {
            Serializer.serialize(root, out);
        } catch (IOException e) {
            throw new SerializationException();
        }
    }

    /**
     * Replace current state with data from input stream
     *
     * @param in
     */
    @Override
    public void deserialize(InputStream in) {
        try {
            root = Serializer.deserialize(in);
        } catch (IOException e) {
            throw new SerializationException();
        }
    }


}
