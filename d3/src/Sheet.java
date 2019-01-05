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

    public void increasePieceOfSheetValue(int xCoordinate, int yCoordinate) {
        try {
            this.sheetPlan[xCoordinate][yCoordinate]++;
        }
        catch(ArrayIndexOutOfBoundsException exception) {
            System.out.println("x: "+xCoordinate+" y: "+yCoordinate);
            System.out.println("Array index out of bounds");
        }
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

