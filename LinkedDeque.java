import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Deque implementation using a linked list.
public class LinkedDeque<Item> implements Iterable<Item> {
    //CODE HERE
    private Node first;
    private Node last;
    private int N;

    // Helper doubly-linked list class.
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    // Construct an empty deque.
    public LinkedDeque() {
        //CODE HERE
        this.first = null;
        this.last = null;
        this.N = 0;
    }

    // Is the dequeue empty?
    public boolean isEmpty() {
        //CODE HERE
        return this.first == null && this.last == null;
    }

    // The number of items on the deque.
    public int size() {
        //CODE HERE
        return this.N;
    }

    // Add item to the front of the deque.
    public void addFirst(Item item) {
        //CODE HERE
        if (item == null) {
            throw new NullPointerException();
        }
        Node insert_node = new Node();
        insert_node.item = item;
        if (isEmpty()) {
            this.first = insert_node;
            this.last = insert_node;
        } else {
            this.first.prev = insert_node;
        }
        insert_node.next = this.first;
        this.first = insert_node;
        N++;
    }

    // Add item to the end of the deque.
    public void addLast(Item item) {
        //CODE HERE
        if (item == null) {
            throw new NullPointerException();
        }
        Node insert_node = new Node();
        insert_node.item = item;
        if (isEmpty()) {
            this.first = insert_node;
            this.last = insert_node;
        } else {
            this.last.next = insert_node;
            insert_node.prev = this.last;
        }
        this.last = insert_node;
        N++;
    }

    // Remove and return item from the front of the deque.
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item remove_node = this.first.item;
        this.first = this.first.next;
        if (this.first == null) {
            this.last = null;
        } else {
            this.N--;
            this.first.prev = null;
        }
        return remove_node;
    }


    // Remove and return item from the end of the deque.
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item remove_node = this.last.item;
        this.last = this.last.prev;
        if (this.last == null) {
            this.first = null;
        } else {
            this.N--;
            this.last.next = null;
        }
        return remove_node;
    }


    // An iterator over items in the queue in order from front to end.
    public Iterator<Item> iterator() {
        //CODE HERE
        return new DequeIterator(this.first);
    }

    // An iterator, doesn't implement remove() since it's optional.
    private class DequeIterator implements Iterator<Item> {
        //CODE HERE
        private Node current_node;


        DequeIterator(Node first) {
            current_node = first;
        }

        public boolean hasNext() {
            return current_node != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            //CODE HERE
            if (isEmpty()) {
                throw new NoSuchElementException();
            }
            Item item = current_node.item;
            current_node = current_node.next;
            return item;

        }
    }

    // A string representation of the deque.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item + " ");
        }
        return s.toString().substring(0, s.length() - 1);
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        LinkedDeque<Character> deque = new LinkedDeque<Character>();
        String quote = "There is grandeur in this view of life, with its "
                + "several powers, having been originally breathed into a few "
                + "forms or into one; and that, whilst this planet has gone "
                + "cycling on according to the fixed law of gravity, from so "
                + "simple a beginning endless forms most beautiful and most "
                + "wonderful have been, and are being, evolved. ~ "
                + "Charles Darwin, The Origin of Species";
        int r = StdRandom.uniform(0, quote.length());
        for (int i = quote.substring(0, r).length() - 1; i >= 0; i--) {
            deque.addFirst(quote.charAt(i));
        }
        for (int i = 0; i < quote.substring(r).length(); i++) {
            deque.addLast(quote.charAt(r + i));
        }
        StdOut.println(deque.isEmpty());
        StdOut.printf("(%d characters) ", deque.size());
        for (char c : deque) {
            StdOut.print(c);
        }
        StdOut.println();
        double s = StdRandom.uniform();
        for (int i = 0; i < quote.length(); i++) {
            if (StdRandom.bernoulli(s)) {
                deque.removeFirst();
            } else {
                deque.removeLast();
            }
        }
        StdOut.println(deque.isEmpty());
    }
}
