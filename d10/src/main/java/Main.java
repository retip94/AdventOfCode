import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {
    static Pointf[] points;
    public static void main(String[] args) {
        long runningTime = System.currentTimeMillis();
        getInput();
        int height = findHeight()[0];
        int lastHeight = height + 1;
        int timer=0;
        while (lastHeight > height) {
            lastHeight = height;
            for (Pointf point : points) {
                point.move();
            }
            height = findHeight()[0];
            timer++;
        }
        for (Pointf point : points) {
            point.moveBack();
        }
        int[] X = findWidth();
        int[] Y = findHeight();
        showTheSky(X, Y);
        System.out.println("Running time: " + (double)(System.currentTimeMillis() - runningTime) / 1000 +"s");
        System.out.println("***task 2***");
        System.out.println(timer-1);
    }


    static void getInput() {
        try {
            List<String> txtInput = Files.readAllLines(Paths.get("input2.txt"), Charset.forName("UTF-8"));
            points = new Pointf[txtInput.size()];
            for (int i = 0; i < txtInput.size(); i++) {
                String str = txtInput.get(i).replace("position=<", "");
                str = str.replace("> velocity=<", ",");
                str = str.replace(">", "");
                str = str.replace(" ", "");
                int[] data = Arrays.stream(str.split(",")).mapToInt(Integer::parseInt).toArray();
                points[i] = new Pointf(data);
            }
        } catch (Exception e) {
            System.out.println("error");
            System.out.println(e.getMessage());
        }
    }

    static int[] findWidth() {
        int maxX = 0;
        int minX = 999999;
        for (Pointf point : points) {
            maxX = (point.x > maxX) ? point.x : maxX;
            minX = (point.x < minX) ? point.x : minX;
        }
        return new int[]{Math.abs(maxX - minX + 1), minX, maxX};
    }

    static int[] findHeight() {
        int maxY = 0;
        int minY = 999999;
        for (Pointf point : points) {
            maxY = (point.y > maxY) ? point.y : maxY;
            minY = (point.y < minY) ? point.y : minY;
        }
        return new int[]{Math.abs(maxY - minY + 1), minY, maxY};
    }


    static void showTheSky(int[] X, int[] Y) {
        boolean[][] sky = new boolean[Y[0]][X[0]];
        for (Pointf point : points) {
            System.out.println(point.x + "," + point.y);
            sky[point.y - Y[1]][point.x - X[1]] = true;
        }
        for (int y = 0; y < sky.length; y++) {
            for (int x = 0; x < sky[0].length; x++) {
                char symbol = sky[y][x] ? '#' : '.';
                System.out.print(symbol);
            }
            System.out.println();
        }
    }
}
