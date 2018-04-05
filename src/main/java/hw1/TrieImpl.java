package hw1;

import com.sun.istack.internal.Nullable;

import java.util.HashMap;

class Node {
    final HashMap<Character, Node> children = new HashMap<>();
    final Node parent;
    boolean isTerminal = false;
    int numberOfSubTerminals = 0;

    Node(Node parent) {
        this.parent = parent;
    }
}

public class TrieImpl implements Trie {
    final private Node root;


    TrieImpl() {
        root = new Node(null);
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
            char ch = element.charAt(i);
            Node nextNode = node.children.get(ch);
            if (nextNode == null) {
                nextNode = new Node(node);
                node.children.put(ch, nextNode);
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
            char ch = element.charAt(i);

            Node nextNode = node.children.get(ch);
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
            char ch = element.charAt(i);
            node = node.children.get(ch);
            node.numberOfSubTerminals--;
        }

        assert node.isTerminal;
        node.isTerminal = false;


        for (int iChar = element.length() - 1; node.numberOfSubTerminals == 0 && iChar >= 0; iChar--) {
            node = node.parent;
            node.children.remove(element.charAt(iChar));
        }

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
            char ch = prefix.charAt(i);
            node = node.children.get(ch);
            if (node == null) {
                return 0;
            }
        }

        return node.numberOfSubTerminals;
    }
}
