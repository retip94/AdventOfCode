import java.awt.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
        System.out.println(Arrays.deepToString(ground).replace("[[", "[").replace("], ", "]\n").replace(",", ""));
    }

    static void getInput() {
        try {aea
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
}