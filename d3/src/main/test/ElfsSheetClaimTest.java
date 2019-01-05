import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ElfsSheetClaimTest {
    @Test
    public void decodingStringClaims() {
        ElfsSheetClaim claim = new ElfsSheetClaim("#1 @ 1,3: 4x4");
        Assert.assertEquals(1, claim.getId());
        Assert.assertEquals(1, claim.getInchesFromLeft());
        Assert.assertEquals(3, claim.getInchesFromTop());
        Assert.assertEquals(4, claim.getWidth());
        Assert.assertEquals(4, claim.getLength());
    }
}