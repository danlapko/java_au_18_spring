package hw1;

import java.util.HashMap;

class Node{
    HashMap<Character, Node> children;
    Node parent;
    boolean is_terminal;
    int number_of_sub_terminals;
    {
        children = new HashMap<>();
        is_terminal = false;
        number_of_sub_terminals = 0;
    }

    Node(Node parent){
        this.parent = parent;
    }
}

public class TrieImpl implements Trie {
    final private Node root;


    TrieImpl(){
        root = new Node(null);
    }

    /**
     * Expected complexity: O(|element|)
     * @return <tt>true</tt> if this set did not already contain the specified
     *         element
     */
    @Override
    public boolean add(String element) {
        if(contains(element))
            return false;

        Node node = root;
        root.number_of_sub_terminals += 1;

        for(int i=0; i<element.length(); i++){
            char ch = element.charAt(i);
            Node next_node = node.children.get(ch);
            if(next_node == null) {
                next_node = new Node(node);
                node.children.put(ch, next_node);
            }
            next_node.number_of_sub_terminals += 1;
            node = next_node;
        }
        if(!node.is_terminal) {
            node.is_terminal = true;

        }

        return true;
    }

    /**
     * Expected complexity: O(|element|)
     */
    @Override
    public boolean contains(String element) {
        Node node = root;

        for(int i=0; i<element.length(); i++){
            char ch = element.charAt(i);

            Node next_node = node.children.get(ch);
            if(next_node == null)
                return false;

            node = next_node;
        }
        return node.is_terminal;
    }

    /**
     * Expected complexity: O(|element|)
     * @return <tt>true</tt> if this set contained the specified element
     */
    @Override
    public boolean remove(String element) {
        if (!contains(element))
            return false;

        Node node = root;
        root.number_of_sub_terminals -= 1;

        for(int i=0; i<element.length(); i++){
            char ch = element.charAt(i);
            node = node.children.get(ch);
            node.number_of_sub_terminals -= 1;
        }

        assert node.is_terminal;
        node.is_terminal = false;

        int i_char = element.length() - 1;
        while (node.number_of_sub_terminals==0 && i_char >= 0){
            node = node.parent;
            node.children.remove(element.charAt(i_char));
            i_char -= 1;
        }

        return true;
    }

    /**
     * Expected complexity: O(1)
     */
    @Override
    public int size() {
        return root.number_of_sub_terminals;
    }

    /**
     * Expected complexity: O(|prefix|)
     */
    @Override
    public int howManyStartsWithPrefix(String prefix) {
        Node node = root;
        for(int i=0; i<prefix.length(); i++){
            char ch = prefix.charAt(i);
            node = node.children.get(ch);
            if (node==null)
                return 0;
        }

        return node.number_of_sub_terminals;
    }
}
