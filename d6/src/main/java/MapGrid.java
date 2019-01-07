public class MapGrid {
    private int[][] grid;
    int sizeX;
    int sizeY;

    MapGrid(int sizeX, int sizeY) {
        grid = new int[sizeY][sizeX];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    void markTheMap(int x, int y, int mark) {
        grid[y][x] = mark+1;
    }

    int getClosestCoordinate(int x, int y, Point[] coordinates) {
        int closestCoordinate = 0;
        Point point = new Point(x, y);
        int minDistance = 9999999;
        boolean inTheMiddle = false;
        for (int i = 0; i < coordinates.length; i++) {
            int distance = point.calculateDistanceTo(coordinates[i]);
            if (distance < minDistance) {
                minDistance = distance;
                closestCoordinate = i;
                inTheMiddle = false;   //reset boolean so it only counts for the smallest value
            } else if (distance == minDistance) {
                inTheMiddle = true;
            }
        }
        //if point is in the middle of two points return -1, else return the index of closest cooordinate
        return (inTheMiddle ? -1 : closestCoordinate);
    }

    void print() {
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                System.out.print(grid[y][x]);
            }
            System.out.println();
        }
    }

    boolean checkIfNotOnBorder(int index) {
        index += 1;
        for (int x = 0; x < sizeX; x++) {
            if (grid[0][x]==index) return true;
            if (grid[sizeY-1][x]==index) return true;
        }
        for (int y = 1; y < sizeY; y++) {
            if (grid[y][0]==index) return true;
            if (grid[y][sizeX-1]==index) return true;
        }
        return false;
    }

    int calcArea(int index) {
        index += 1;
        int area = 0;
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                if(grid[y][x]==index) area++;
            }
        }
        return area;
    }

}
