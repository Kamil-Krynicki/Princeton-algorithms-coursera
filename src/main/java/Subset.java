

import edu.princeton.cs.algs4.StdIn;

public class Subset {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException();
        }

        RandomizedQueue<String> queue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }

        int numPrint = Integer.parseInt(args[0]);

        for (int i = 0; i < numPrint; i++) {
            System.out.println(queue.dequeue());
        }
    }
}
