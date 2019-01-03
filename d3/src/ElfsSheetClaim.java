public class ElfsSheetClaim {
    private int id;
    private int inchesFromLeft;
    private int inchesFromTop;
    private int width;
    private int length;

    public ElfsSheetClaim(int id, int inchesFromLeft, int inchesFromTop, int width, int length) {
        this.id = id;
        this.inchesFromLeft = inchesFromLeft;
        this.inchesFromTop = inchesFromTop;
        this.width = width;
        this.length = length;
    }

    public ElfsSheetClaim(String claim) {
        claim = claim.replace(" ", "");
        claim = claim.replace("#", "");
        String[] claimInfo = claim.split("[@,:x]");
        try {
            this.id = Integer.valueOf(claimInfo[0]);
            this.inchesFromLeft = Integer.valueOf(claimInfo[1]);
            this.inchesFromTop = Integer.valueOf(claimInfo[2]);
            this.width = Integer.valueOf(claimInfo[3]);
            this.length = Integer.valueOf(claimInfo[4]);
        } catch (NumberFormatException exception) {
            System.out.println("Decoding claim string error");
        }
    }

    public int getId() {
        return id;
    }
    public int getInchesFromLeft() {
        return inchesFromLeft;
    }
    public int getInchesFromTop() {
        return inchesFromTop;
    }
    public int getLength() {
        return length;
    }
    public int getWidth() {
        return width;
    }
}
