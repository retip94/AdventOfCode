import java.awt.*;

public class Results {
    public static void main(String[] args) {
        //region input
        int[][] coordinatesInput = {{69, 102},{118, 274},{150, 269},{331, 284},{128, 302},{307, 192},{238, 52},{240, 339},{111, 127},{180, 156},{248, 265},{160, 69},{58, 136},{43, 235},{154, 202},{262, 189},{309, 53},{292, 67},{335, 198},{99, 199},{224, 120},{206, 313},{359, 352},{101, 147},{301, 47},{255, 347},{121, 153},{264, 343},{252, 225},{48, 90},{312, 139},{90, 277},{203, 227},{315, 328},{330, 81},{190, 191},{89, 296},{312, 255},{218, 181},{299, 149},{151, 254},{209, 212},{42, 76},{348, 183},{333, 227},{44, 210},{293, 356},{44, 132},{175, 77},{215, 109}};
        //endregion

        //create points from input
        Point[] coordinates = new Point[coordinatesInput.length];
        for (int i = 0; i < coordinatesInput.length; i++) {
            int x = coordinatesInput[i][0];
            int y = coordinatesInput[i][1];
            coordinates[i] = new Point(x, y);
        }

        //create 2D mapGrid with size of max coordinates
        Point maxCoordinates = getMaxCoordinate(coordinates);
        MapGrid mapGrid = new MapGrid(maxCoordinates.x+1, maxCoordinates.y+1);

        //mark coordinates on map
        for (int y = 0; y < mapGrid.sizeY; y++) {
            for (int x = 0; x < mapGrid.sizeX; x++) {
                mapGrid.markTheMap(x,y,mapGrid.getClosestCoordinate(x,y,coordinates));
            }
        }
        //calc area of each index
        //when area is infinite it will be set to 0
        int[][] areas = new int[coordinates.length][2];
        int maxArea = 0;
        for (int i = 0; i < coordinates.length; i++) {
            if (mapGrid.checkIfNotOnBorder(i)) {areas[i][1]=0;continue;}
//            if (mapGrid.checkIfAreaDontTouchMapCorner(i)) {areas[i][1]=0;continue;}
            areas[i][1] = mapGrid.calcArea(i);
            if (areas[i][1]>maxArea) maxArea = areas[i][1];
        }

        System.out.println("***task 1***");
        System.out.println(maxArea);
        System.out.println("***task 2***");
        Region region = new Region(mapGrid.sizeX, mapGrid.sizeY, 10000);
        for (int y = 0; y < region.sizeY; y++) {
            for (int x = 0; x < region.sizeX; x++) {
                int summaryDistance = region.getSummaryDistanceToCoordinates(x,y,coordinates);
                region.markRegion(x, y, summaryDistance);
            }
        }
        System.out.println(region.markedArea);


    }

    static Point getMaxCoordinate(Point[] coordinates) {
        int maxX = 0;
        int maxY = 0;
        for (Point coordinate : coordinates) {
            maxX = (coordinate.x > maxX ? coordinate.x : maxX);
            maxY = (coordinate.y > maxY ? coordinate.y : maxY);
        }
        return new Point(maxX, maxY);
    }
}
