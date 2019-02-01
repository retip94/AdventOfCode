import java.awt.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    static char[][] ground;
    static List<Scan> scans = new ArrayList<>();
    static Point waterSpring = new Point(500, 0);


    public static void main(String[] args) {
        getInput();
        HashMap<String, Integer> grid = getMinsAndSize();
        ground = new char[grid.get("sizeY")][grid.get("sizeX")];
        markOnGrid(grid.get("minX"), grid.get("minY"));
        Point startingPoint = new Point(waterSpring.x - grid.get("minX"), waterSpring.y - grid.get("minY") + 1);
        printGround();
        Deque<Point> waterFlowing = new LinkedList<>();
        waterFlowing.add(startingPoint);
        while (!waterFlowing.isEmpty()) {
            Point p = waterFlowing.removeLast();
            while (p.y < grid.get("sizeY") - 1) {
                ground[p.y][p.x] = '|';
                if (below(p) == '.') {
                    waterFlowing.add(new Point(p.x, p.y + 1));
                    p.translate(0, 1);
                } else {
                    Integer leftWall = null;
                    Integer leftDrop = null;
                    for (int _x = p.x; _x > 0; _x--) {
                        if (ground[p.y + 1][_x - 1] == '.') {
                            leftDrop = _x - 1;
                            break;
                        }
                        if (ground[p.y][_x - 1] == '#') {
                            leftWall = _x;
                            break;
                        }
                    }
                    Integer rightWall = null;
                    Integer rightDrop = null;
                    for (int _x = p.x; _x < grid.get("sizeX") - 1; _x++) {
                        if (ground[p.y + 1][_x + 1] == '.') {
                            rightDrop = _x + 1;
                            break;
                        }
                        if (ground[p.y][_x + 1] == '#') {
                            rightWall = _x;
                            break;
                        }
                    }
                    if (leftWall != null && rightWall != null) {
                        for (int _x = leftWall; _x <= rightWall; _x++) {
                            ground[p.y][_x] = '~';
                        }
                        break;
                    } else if (leftDrop != null && rightWall != null) {
                        waterFlowing.add(new Point(leftDrop, p.y));
                        for (int _x = leftDrop; _x <= rightWall; _x++) {
                            ground[p.y][_x] = '|';
                        }
                        break;
                    } else if (leftDrop != null && rightDrop != null) {
                        waterFlowing.add(new Point(leftDrop, p.y));
                        waterFlowing.add(new Point(rightDrop, p.y));
                        for (int _x = leftDrop; _x <= rightDrop; _x++) {
                            ground[p.y][_x] = '|';
                        }
                        break;
                    } else {
                        waterFlowing.add(new Point(rightDrop, p.y));
                        for (int _x = leftWall; _x <= rightDrop; _x++) {
                            ground[p.y][_x] = '|';
                        }
                        break;
                    }
                }
                printGround();
            }


        }


//        int steps = 0;
//        int steps2 = 0;
//        boolean leftBlocked = false;
//        boolean waterBlocked = false;
//        while (steps2 < 7) {
//            steps = 0;
//            Point p = new Point(startingPoint);
//            waterBlocked = false;
//            leftBlocked = false;
//            while (steps<100) {
//                if (waterBlocked) {
//                    ground[p.y][p.x] = '~';
//                    if (p.x > 0) {
//                        char onLeft = ground[p.y][p.x - 1];
//                        if (onLeft != '|') {
//                            break;
//                        } else
//                            p.translate(-1, 0);
//                    }
//                }
//
//                char below = ground[p.y + 1][p.x];
//                if (below == '.' || below == '|') {
//                    p.translate(0, 1);
//                    ground[p.y][p.x] = '|';
//                    leftBlocked = false;
//                } else if (below == '#' || below == '~') {
//                    if (p.x > 0 && !leftBlocked) {
//                        char onLeft = ground[p.y][p.x - 1];
//                        if (onLeft == '.' || onLeft == '|') {
//                            p.translate(-1, 0);
//                            ground[p.y][p.x] = '|';
//                        } else
//                            leftBlocked = true;
//                    } else if (p.x < grid.get("sizeX")) {
//                        char onRight = ground[p.y][p.x + 1];
//                        if (onRight == '.' || onRight == '|') {
//                            p.translate(1, 0);
//                            ground[p.y][p.x] = '|';
//                        } else
//                            waterBlocked = true;
//                    }
//                }
//                steps++;
//                printGround();
//            }
//            printGround();
//            steps2++;
//        }
//        System.out.println(steps);
    }

    static void printGround() {
        System.out.println("___________________________________________");
        System.out.println(Arrays.deepToString(ground).replace("[[", "[").replace("], ", "]\n").replace(",", ""));
    }

    static void getInput() {
        try {
            Stream<String> textStream = Files.lines(Paths.get("input0.txt"), Charset.forName("UTF-8"));
            textStream.forEach((line) -> scans.add(new Scan(line)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static HashMap getMinsAndSize() {
        int minY = waterSpring.y;
        int maxY = waterSpring.y;
        int minX = waterSpring.x;
        int maxX = waterSpring.x;
        for (Scan scan : scans) {
            if (scan.isVertical) {
                minY = Math.min(minY, scan.startPoint);
                maxY = Math.max(maxY, scan.endPoint);
                minX = Math.min(minX, scan.rowOrColumn);
                maxX = Math.max(maxX, scan.rowOrColumn);
            } else {
                minY = Math.min(minY, scan.rowOrColumn);
                maxY = Math.max(maxY, scan.rowOrColumn);
                minX = Math.min(minX, scan.startPoint);
                maxX = Math.max(maxX, scan.endPoint);
            }
        }
        HashMap<String, Integer> result = new HashMap<>();
        result.put("minY", minY);
        result.put("minX", minX);
        result.put("sizeY", maxY - minY + 1);
        result.put("sizeX", maxX - minX + 1);
        return result;
    }

    static void markOnGrid(int minX, int minY) {
        Arrays.stream(ground).forEach(layer -> Arrays.fill(layer, '.'));
        ground[waterSpring.y-minY][waterSpring.x-minX] = '+';
        for (Scan scan : scans) {
            if (scan.isVertical) {
                int x = scan.rowOrColumn;
                for (int y = scan.startPoint; y <= scan.endPoint; y++) {
                    ground[y - minY][x - minX] = '#';
                }
            } else {
                int y = scan.rowOrColumn;
                for (int x = scan.startPoint; x <= scan.endPoint; x++) {
                    ground[y - minY][x - minX] = '#';
                }
            }
        }
    }

    static char below(Point point) {
        return ground[point.y+1][point.x];
    }
}