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
}