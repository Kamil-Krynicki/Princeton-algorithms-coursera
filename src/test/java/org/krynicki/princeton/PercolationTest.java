package org.krynicki.princeton;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by K on 2016-09-06.
 */
public class PercolationTest {

    private Percolation p;

    @Test
    void percolateTest() {
        p = new Percolation(5);

        p.printState();

        p.open(1, 3);
        p.open(1, 5);
        p.open(1, 1);

        p.printState();

        p.open(2, 2);
        p.open(2, 4);

        p.printState();

        p.open(3, 1);
        p.open(3, 3);
        p.open(3, 5);

        p.printState();

        p.open(2, 3);

        p.printState();

        p.open(3, 3);

        p.printState();

        p.open(4, 3);

        p.printState();

        p.open(5, 3);

        p.printState();





    }

}