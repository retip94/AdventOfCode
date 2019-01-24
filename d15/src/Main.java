import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
//    static String[] input = {"#######", "#.G...#", "#...EG#", "#.#.#G#", "#..G#E#", "#.....#", "#######"};    //result 47*590 = 27730
    static String[] input = {"#######","#G..#E#","#E#E.E#","#G.##.#","#...#E#","#...E.#","#######"};            //result 37*982 = 36334
    static char[][] plan = new char[input.length][input[0].length()];
    static List<Elf> elves = new ArrayList<>();
    static List<Goblin> goblins = new ArrayList<>();
    public static void main(String[] args) {
        drawMap();
        printMap();
        int roundsCounter = 0;
        while (!elves.isEmpty() && !goblins.isEmpty()) {
            for (Unit unit : getSortedUnits()) {
                if(!unit.alive) continue;
                System.out.println(unit.getInfo());
                boolean successfulAttack = unit.tryToAttack();
                System.out.println("Successful attack - " + successfulAttack);
                if (!successfulAttack) {
                    Pointt nextPoint =  unit.tryToMove(getUnits());
                    System.out.println("moved to: " + nextPoint);
                    unit.tryToAttack();
                }
            }
            printMap();
            roundsCounter++;
            printSummary(roundsCounter);
            if (roundsCounter == 37) {
                System.out.println("breakpoint");
            }
        }
        int result = getResult(roundsCounter);
        System.out.println(result);




    }

    static void drawMap() {
        for (int y = 0; y < input.length; y++) {
            String row = input[y];
            for (int x = 0; x < row.length(); x++) {
                char c = row.charAt(x);
                if(c=='E'){
                    elves.add(new Elf(x, y));
                    plan[y][x] = '.';}
                else if(c=='G'){
                    goblins.add(new Goblin(x, y));
                    plan[y][x] = '.';}
                else plan[y][x] = c;
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
        List<Unit> units = new ArrayList<>();
        units.addAll(elves);
        units.addAll(goblins);
        return units;
    }

    static void printMap() {
        char[][] tempMap = new char[plan.length][plan[0].length];
        for (int i = 0; i < plan.length; i++) {
            tempMap[i] = plan[i].clone();
        }
        for (Unit u : getUnits()) {
            tempMap[u.pos.y][u.pos.x] =
                    u.race.equals("elf") ? 'E' : 'G';
        }
        System.out.println(Arrays.deepToString(tempMap).replace("], ","]\n ").replace(", ",""));
    }

    static void printSummary(int round) {
        System.out.println("---------------------------------AFTER ROUND  " + round);
        for (Unit u : getSortedUnits())
            System.out.println(u.getInfo());
    }

    static int getResult(int round) {
        int sum = 0;
        for (Unit u : getUnits()) {
            sum += u.HP;
        }
        System.out.println("rounds: " + round);
        System.out.println("sum: " + sum);
        return sum * round;
    }

}
