package org.krynicki.princeton;

import org.testng.annotations.Test;

/**
 * Created by K on 2016-09-06.
 */
public class UnionFindTest {

    private UnionFind uf;
    @Test
    void testUnionFind(){
        uf = new UnionFind(20);

        uf.union(1, 4);
        uf.connected(1, 4);

        uf.union(4, 5);
        uf.connected(1, 5);

        uf.union(5, 9);
        uf.connected(5, 9);
    }

}