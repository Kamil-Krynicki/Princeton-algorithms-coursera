package org.krynicki.princeton;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by K on 2016-09-06.
 */
public class PercolationStatsTest {
    @Test
    void test() {
        PercolationStats ps = new PercolationStats(30, 100);

        System.out.println("mean = "+  ps.mean());
        System.out.println("stddev = "+  ps.stddev());
        System.out.println("95% confidence interval = "+ ps.confidenceLo() +", "+ ps.confidenceHi());
    }
}