import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CircularSuffixArrayTest {


    @Test
    public void problemDescriptionTestCases() {
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray("ABRACADABRA!");

        Assert.assertEquals(circularSuffixArray.index(11), 2);
        Assert.assertEquals(circularSuffixArray.index(3), 0);
    }


    @Test
    public void problemDescriptionTestCases3() {
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray("**********");

        Assert.assertEquals(circularSuffixArray.index(0), 0);
        Assert.assertEquals(circularSuffixArray.index(3), 3);
    }

    @Test
    public void problemDescriptionTestCases2() {

        int n = 3000000;

        char[] data = new char[n];

        for (int i = 0; i < n; i++) {
            data[i] = (char) (Math.random() * 24 + 'a');
        }

        long t1 = System.currentTimeMillis();
        new CircularSuffixArray(new String(data));
        long t2 = System.currentTimeMillis();

        System.out.println(t2 - t1);

    }
}