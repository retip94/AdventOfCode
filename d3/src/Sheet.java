public class Sheet {
    private int sheetWidth;
    private int sheetLength;
    private int[][] sheetPlan = new int[0][0];

    Sheet(int width, int length){
        this.sheetWidth = width;
        this.sheetLength = length;
        this.sheetPlan = new int[width][length];
    }

    public int getSheetWidth() {
        return sheetWidth;
    }

    public int getSheetLength() {
        return sheetLength;
    }

    public int getPieceOfSheetValue(int xCoordinate, int yCoordinate) {
        try {
            return this.sheetPlan[xCoordinate][yCoordinate];
        }
        catch(ArrayIndexOutOfBoundsException exception) {
            System.out.println("Array index out of bounds");
            return 0;
        }
    }

    private void increasePieceOfSheetValue(int xCoordinate, int yCoordinate) {
        try {
            this.sheetPlan[xCoordinate][yCoordinate]++;
        }
        catch(ArrayIndexOutOfBoundsException exception) {
            System.out.println("Array index out of bounds");
        }
    }

    void markClaimOnSheet(ElfsSheetClaim claim) {
        int startingX = claim.getInchesFromLeft();
        int startingY = claim.getInchesFromTop();
        int width = claim.getWidth();
        int length = claim.getLength();
        for (int y = startingY; y < (startingY + length); y++) {
            for (int x = startingX; x < (startingX + width); x++) {
                increasePieceOfSheetValue(x,y);
        }}
    }

    int getAmountOfCommonSheetPieces() {
        int counter = 0;
        for (int y = 0; y < sheetLength; y++) {
            for (int x = 0; x < sheetWidth; x++) {
                if (this.getPieceOfSheetValue(x, y) > 1) counter++;
            }
        }
        return counter;
    }
}

