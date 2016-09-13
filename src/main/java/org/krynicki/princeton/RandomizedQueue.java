package org.krynicki.princeton;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] nodes;

    public RandomizedQueue() {
        size = 0;
        nodes = (Item[]) new Object[10];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize(int newSize) {
        Item[] tmp = nodes;

        nodes = (Item[]) new Object[newSize];
        System.arraycopy(tmp, 0, nodes, 0, size);
    }

    private boolean sparse() {
        return size < nodes.length / 4 && nodes.length >= 10; // block decreasing at 10
    }

    private boolean full() {
        return size == nodes.length;
    }

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();

        if (full()) {
            resize(nodes.length * 2);
        }

        nodes[size++] = item;
    }

    public Item dequeue() {
        if (size == 0) throw new NoSuchElementException();

        int randomIndex = ThreadLocalRandom.current().nextInt(size());

        Item result = nodes[randomIndex];
        --size;

        nodes[randomIndex] = nodes[size];
        nodes[size] = null;

        if (sparse()) {
            resize(nodes.length / 2);
        }

        return result;
    }

    public Item sample() {
        if (size == 0) throw new NoSuchElementException();
        return nodes[ThreadLocalRandom.current().nextInt(size())];
    }

    public Iterator<Item> iterator() {
        return new RandomIterator<>(this.nodes);
    }

    private class RandomIterator<Item> implements Iterator<Item> {
        private Iterator<Item> randomIterator;

        public RandomIterator(Item[] nodes) {
            List<Item> rand = new ArrayList<Item>(nodes.length);
            for (Item i : nodes) {
                if (i != null) {
                    rand.add(i);
                } else {
                    break;
                }
            }

            Collections.shuffle(rand);
            this.randomIterator = rand.iterator();
        }

        @Override
        public boolean hasNext() {
            return randomIterator.hasNext();
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return randomIterator.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        for (int i = 0; i < 100; i++) {
            queue.enqueue("A");
        }

        for (int i = 0; i < 100; i++) {
            queue.dequeue();
        }

        Iterator<String> it = queue.iterator();
    }
}
