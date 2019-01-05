
import org.junit.Assert;
import org.junit.Test;

public class SheetTest {

    @Test
    public void task1Example() {
        Sheet sheet = new Sheet(8, 8);
        ElfsSheetClaim[] elfsSheetClaims = new ElfsSheetClaim[3];
        elfsSheetClaims[0] = new ElfsSheetClaim(1,1,3,4,4);
        elfsSheetClaims[1] = new ElfsSheetClaim(2,3,1,4,4);
        elfsSheetClaims[2] = new ElfsSheetClaim(3, 5, 5, 2, 2);
        for (ElfsSheetClaim claim : elfsSheetClaims) {
            claim.markClaimOnSheet(sheet);
        }
        Assert.assertEquals(4, sheet.getAmountOfOverlappedSheetPieces());
    }
}