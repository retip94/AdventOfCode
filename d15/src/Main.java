import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static String[] input = {"#######", "#.G...#", "#...EG#", "#.#.#G#", "#..G#E#", "#.....#", "#######"};
    static char[][] plan = new char[input.length][input[0].length()];
    static List<Unit> elves = new ArrayList<>();
    static List<Goblin> goblins = new ArrayList<>();
    public static void main(String[] args) {
        drawMap();
        printMap();
        int roundsCounter = 0;
        while (roundsCounter < 2) {
            for (Unit unit : getSortedUnits()) {
                System.out.println(unit.getInfo());
                boolean successfulAttack = unit.tryToAttack();
                System.out.println("Successful attack - " + successfulAttack);
                if (!successfulAttack) {
                    Pointt nextPoint =  unit.tryToMove(getUnits());
                    System.out.println("moved to: " + nextPoint);
                }
            }
            roundsCounter++;
        }
        printMap();


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

    static List<Unit> getSortedUnits() {
        List<Unit> units = new ArrayList<>();
        units.addAll(elves);
        units.addAll(goblins);
        units.sort(Unit::compareByPosition);
        return units;
    }

    static List<Unit> getUnits() {
        List<Unit> units = elves;
        units.addAll(goblins);
        return units;
    }

    static void printMap() {
        System.out.println(Arrays.deepToString(plan).replace("], ","]\n ").replace(", ",""));
    }

}
