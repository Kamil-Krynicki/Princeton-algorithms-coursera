

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node<Item> head;
    private Node<Item> tail;

    public Deque() {
        size = 0;
        head = null;
        tail = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();

        Node<Item> newNode = new Node<>(item);

        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            head.next = newNode;
            newNode.prv = head;
            head = newNode;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();

        Node<Item> newNode = new Node<>(item);

        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            tail.prv = newNode;
            newNode.next = tail;
            tail = newNode;
        }

        size++;
    }

    public Item removeFirst() {
        if (size == 0) throw new NoSuchElementException();

        Item result = head.data;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head = head.prv;
            head.next = null;
        }

        size--;
        return result;
    }

    public Item removeLast() {
        if (size == 0) throw new NoSuchElementException();

        Item result = tail.data;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            tail = tail.next;
            tail.prv = null;
        }

        size--;
        return result;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator<>(head);
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        private Node<Item> runner;

        public DequeIterator(Node<Item> start) {
            this.runner = start;
        }

        @Override
        public boolean hasNext() {
            return runner != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item result = runner.data;
            runner = runner.prv;
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    ;

    private class Node<Item> {
        private Item data;
        private Node<Item> next;
        private Node<Item> prv;

        public Node(Item data) {
            this.data = data;
            this.next = null;
            this.prv = null;
        }
    }
}
