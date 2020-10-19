import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Random queue implementation using a resizing array.
@SuppressWarnings("unchecked")
public class ResizingArrayRandomQueue<Item> implements Iterable<Item> {
    //CODE HERE
    private Item[] q;
    private int N;

    // Construct an empty queue.
    public ResizingArrayRandomQueue() {
        //CODE HERE
        this.q = (Item[]) new Object[2];    // Capactity of the queue, some elements maybe null
        this.N = 0; // the actual size of the queue
    }

    // Is the queue empty?
    public boolean isEmpty() {
        //CODE HERE
        return this.N == 0;
    }

    // The number of items on the queue.
    public int size() {
        //CODE HERE
        return this.N;
    }

    // Add item to the queue.
    public void enqueue(Item item) {
        //CODE HERE
        if (item == null) {
            throw new NullPointerException();
        }
        if (this.N == this.q.length) {
            this.resize(2 * this.N);
        }
        this.q[N] = item;
        this.N++;
    }

    // Remove and return a random item from the queue.
    public Item dequeue() {
        //CODE HERE
        int random_index = StdRandom.uniform(0, N);
        Item return_item = this.q[random_index];
        if (return_item == null) {
            throw new NoSuchElementException();
        }
        this.q[random_index] = this.q[N - 1];
        this.q[N - 1] = null;
        if (this.N == (this.q.length / 4)) {
            this.resize(this.q.length / 2);
        }
        this.N--;
        return return_item;
    }

    // Return a random item from the queue, but do not remove it.
    public Item sample() {
        //CODE HERE
        Item return_value = this.q[StdRandom.uniform(0, this.N)];
        if (return_value == null) {
            throw new NoSuchElementException();
        }
        return return_value;
    }

    // An independent iterator over items in the queue in random order.
    public Iterator<Item> iterator() {
        //CODE HERE
        return new RandomQueueIterator();
    }

    // An iterator, doesn't implement remove() since it's optional.
    private class RandomQueueIterator implements Iterator<Item> {
        //CODE HERE
        private Item[] items;

        private int current;

        RandomQueueIterator() {
            //CODE HERE
            this.items = (Item[]) new Object[q.length];
            for (int i = 0; i < q.length; i++) {
                this.items[i] = q[i];
            }
            StdRandom.shuffle(this.items);
            current = 0;
        }

        public boolean hasNext() {
            return current < N;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            //CODE HERE
            Item return_item = q[current++];
            if (return_item == null) {
                throw new NoSuchElementException();
            }
            return return_item;
        }
    }

    // A string representation of the queue.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item + " ");
        }
        return s.toString().substring(0, s.length() - 1);
    }

    // Helper method for resizing the underlying array.
    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            if (q[i] != null) {
                temp[i] = q[i];
            }
        }
        q = temp;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        ResizingArrayRandomQueue<Integer> q =
                new ResizingArrayRandomQueue<Integer>();
        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readInt());
        }
        int sum1 = 0;
        for (int x : q) {
            sum1 += x;
        }
        int sum2 = sum1;
        for (int x : q) {
            sum2 -= x;
        }
        int sum3 = 0;
        while (q.size() > 0) {
            sum3 += q.dequeue();
        }
        StdOut.println(sum1);
        StdOut.println(sum2);
        StdOut.println(sum3);
        StdOut.println(q.isEmpty());
    }
}
