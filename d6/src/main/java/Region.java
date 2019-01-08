public class Region {
    int sizeX;
    int sizeY;
    private int grid[][];
    private int maxSumarryDistance;
    int markedArea;

    Region(int sizeX, int sizeY, int maxSumarryDistance) {
        grid = new int[sizeY][sizeX];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.maxSumarryDistance = maxSumarryDistance;
    }

    void markRegion(int x, int y, int summaryDistance) {
        if (summaryDistance<maxSumarryDistance){
            grid[y][x] = 1;
            markedArea += 1;}
    }

    int getSummaryDistanceToCoordinates(int x, int y, Point[] coordinates) {
        int summaryDistance = 0;
        Point point = new Point(x, y);
        for (Point coordinate : coordinates) {
            summaryDistance += point.calculateDistanceTo(coordinate);
        }
        return summaryDistance;
    }
}
