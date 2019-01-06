import java.util.Arrays;

public class Results {
    public static void main(String[] args) {
        //region input
        int[][] coordinates = {{1, 1},{1, 6},{8, 3},{3, 4},{5, 5},{8, 9}};

        //create 2D array with size of max coordinates
        int[] maxCoordinates = getMaxCoordinate(coordinates);
        int[][] map = new int[maxCoordinates[1] + 1][maxCoordinates[0] + 1];

        for (int i =0;i<coordinates.length;i++) {
            int x = coordinates[i][0];
            int y = coordinates[i][1];
            map[y][x] = i+1;
        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = getClosestCoordinate(new int[]{i, j}, coordinates) + 1;
            }
        }

        printMap(map);

    }

    public static void printMap(int[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }



    public static int calcDistance(int[] from, int[] destination) {
        int distance = Math.abs(destination[0] - from[1]) + Math.abs(destination[1] - from[0]);
        return distance;
    }

    public static int[] getMaxCoordinate(int[][] coordinates) {
        int maxX = 0;
        int maxY = 0;
        for (int[] coordinate : coordinates) {
            maxX = (coordinate[0] > maxX ? coordinate[0] : maxX);
            maxY = (coordinate[1] > maxY ? coordinate[1] : maxY);
        }
        return new int[]{maxX, maxY};
    }

    public static int getClosestCoordinate(int[] point, int[][] coordinates) {
        int minDistance = 9999999;
        int closestCoordinate = 0;
        boolean sameDistance = false;
        for (int i = 0; i < coordinates.length; i++) {
            int distance = calcDistance(point, coordinates[i]);
            if (distance < minDistance) {
                minDistance = distance;
                closestCoordinate = i;
                sameDistance = false;   //reset boolean so it only counts for the smallest value
            } else if (distance == minDistance) {
                sameDistance = true;
            }
        }
        return (sameDistance ? -1 : closestCoordinate);
    }
}
