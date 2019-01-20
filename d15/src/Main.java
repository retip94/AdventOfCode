import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static String[] input = {"#######", "#.G...#", "#...EG#", "#.#.#G#", "#..G#E#", "#.....#", "#######"};
    static char[][] plan = new char[input.length][input[0].length()];
    static List<Elf> elves = new ArrayList<>();
    static List<Goblin> goblins = new ArrayList<>();
    public static void main(String[] args) {
        drawMap();
        int roundsCounter = 0;
        while (roundsCounter < 100) {

            roundsCounter++;
        }
        System.out.println(Arrays.deepToString(plan).replace("], ","]\n ").replace(", ",""));


    }

    static void drawMap() {
        for (int y = 0; y < input.length; y++) {
            String row = input[y];
            for (int x = 0; x < row.length(); x++) {
                char c = row.charAt(x);
                plan[y][x] = c;
                if(c=='E') elves.add(new Elf(x, y));
                if(c=='G') goblins.add(new Goblin(x, y));
            }
        }
    }

    static List<Unit> getUnits() {
        List<Unit> units = new ArrayList<>();
        for (Elf elf : elves) {
            units.add((Unit) elf);
        }
        for (Goblin goblin : goblins) {
            units.add((Unit) goblin);
        }
    }

}
