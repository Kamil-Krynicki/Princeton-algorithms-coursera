package org.krynicki.princeton;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by K on 2016-09-06.
 */
public class PercolationTest {

    private Percolation p;

    @Test
    void percolateTest1() {
        p = new Percolation(1);
        p.open(1, 1);
        p.percolates();
    }

    @Test
    void percolateTest3() {
        p = new Percolation(10);
        p.open(2, 10);
        p.printState();

        p.open(6, 8);
        p.printState();

        p.open(2, 6);
        p.printState();

        p.open(1, 4);
        p.printState();

        p.open(8, 4);
        p.printState();

        p.open(10, 1);
        p.printState();

        p.open(4, 2);
        p.printState();

        p.open(4, 8);
        p.printState();

        p.open(9, 3);
        p.printState();

        p.open(2, 2);
        p.printState();

        p.open(9, 1);
        p.printState();

        p.open(4, 3);
        p.printState();

        p.open(5, 5);
        p.printState();

        p.open(5, 7);
        p.printState();

        p.open(2, 8);
        p.printState();

        p.open(6, 4);
        p.printState();

        p.open(7, 5);
        p.printState();

        p.open(9, 6);
        p.printState();

        p.open(3, 7);
        p.printState();

        p.open(4, 7);
        p.printState();

        p.open(7, 1);
        p.printState();

        p.open(9, 4);
        p.printState();

        p.open(3, 10);
        p.printState();

        p.open(1, 10);
        p.printState();

        p.open(10, 10);
        p.printState();

        p.open(9, 7);
        p.printState();

        p.open(1, 5);
        p.printState();

        p.open(9, 8);
        p.printState();

        p.open(6, 1);
        p.printState();

        p.open(2, 5);
        p.printState();

        p.open(3, 4);
        p.printState();

        p.open(6, 9);
        p.printState();

        p.open(5, 8);
        p.printState();

        p.open(3, 2);
        p.printState();

        p.open(4, 6);
        p.printState();

        p.open(1, 7);
        p.printState();

        p.open(7, 9);
        p.printState();

        p.open(3, 9);
        p.printState();

        p.open(4, 4);
        p.printState();

        p.open(4, 10);
        p.printState();

        p.open(3, 5);
        p.printState();

        p.open(3, 8);
        p.printState();

        p.open(1, 8);
        p.printState();

        p.open(3, 1);
        p.printState();

        p.open(6, 7);
        p.printState();

        p.open(2, 3);
        p.printState();

        p.open(7, 4);
        p.printState();

        p.open(9, 10);
        p.printState();

        p.open(7, 6);
        p.printState();

        p.open(5, 2);
        p.printState();

        p.open(8, 3);
        p.printState();

        p.open(10, 8);
        p.printState();

        p.open(7, 10);
        p.printState();

        p.open(4, 5);
        p.printState();

        p.open(8, 10);
        p.printState();



        p.percolates();
    }




    @Test
    void percolateTest2() {
        p = new Percolation(5);

        //p.printState();

        p.open(1, 3);
        p.printState();
        p.open(1, 5);
        p.printState();
        p.open(1, 1);

        //p.printState();

        p.open(2, 2);
        p.open(2, 4);

        //p.printState();

        p.open(3, 1);
        p.open(3, 3);
        p.open(3, 5);

        //p.printState();

        p.open(2, 3);

        //p.printState();

        p.open(3, 3);

        //p.printState();

        p.open(4, 3);

        //p.printState();

        p.open(5, 3);

        // p.printState();


    }

}