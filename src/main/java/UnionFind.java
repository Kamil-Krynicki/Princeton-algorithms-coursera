

/**
 * Created by K on 2016-09-06.
 */
public class UnionFind {

    int[] sizes;
    int[] unions;

    public UnionFind(int size){
        sizes = new int[size];
        unions = new int[size];

        for(int i=0;i<size;i++){
            sizes[i] = 1;
            unions[i] = i;
        }
    }

    private int root(int p){
        int root;

        if(unions[p] != p){
            root = root(unions[p]);
            unions[p] = root;
        }
        else {
            root = p;
        }

        return root;
    }


    public void union(int p, int q){
        int rootP = root(p);
        int rootQ = root(q);

        if(sizes[rootP] > sizes[rootQ]) {
            unions[rootQ] = rootP;
            sizes[rootP] += sizes[rootQ];
        }
        else {
            unions[rootP] = rootQ;
            sizes[rootQ] += sizes[rootP];
        }
    }

    public boolean connected(int p, int q){
        int rootP = root(p);
        int rootQ = root(q);

        return rootP == rootQ;
    }
}
