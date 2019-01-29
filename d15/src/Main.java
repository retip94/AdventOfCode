import java.util.*;

public class Main {
//    static String[] input = {"#######", "#.G...#", "#...EG#", "#.#.#G#", "#..G#E#", "#.....#", "#######"};      //result 47*590 = 27730
//    static String[] input = {"#######","#G..#E#","#E#E.E#","#G.##.#","#...#E#","#...E.#","#######"};            //result 37*982 = 36334
//    static String[] input = {"#######","#E..EG#","#.#G.E#","#E.##E#","#G..#.#","#..E#.#","#######"};            //result 46*859 = 39514
//    static String[] input = {"#######","#E.G#.#","#.#G..#","#G.#.G#","#G..#.#","#...E.#","#######"};              //result 35*793 = 27755
//    static String[] input = {"#######","#.E...#","#.#..G#","#.###.#","#E#G#G#","#...#G#","#######"};              //result 54*536 = 28944
//    static String[] input = {"#########","#G......#","#.E.#...#","#..##..G#","#...##..#","#...#...#","#.G...G.#","#.....G.#","#########"};              //result 20*937 = 18740
    static String[] input = {"################################","##########..#####...############","##########.G..####..############","########.....##.##.#############","####G..#G#.......#.#############","#G.....#.GG...G.##.#############","#.#...G.......#.##..############","###..#.......#####......########","######.......#####..G....#######","######..GG..######.......#######","#####.GG....##.####G......G.####","###.............G...........####","###.#.........#####G..G....#####","###..#.##....#######E.....######","########....#########.#######..#","#########...#########.#######..#","########....#########..##......#","#########...#########...#...#.E#","#########...#########.......##.#","########....E#######.......#####","######........#####....E.#######","######.......E..E..G.E....######","#######.............###....#####","#######............####.E...####","#######...G....E##....##....####","#######...............##########","############.E.......###########","###########.....#....###########","###########.....#....###########","###########.....###.############","###########.#.##################","################################"};

static char[][] plan = new char[input.length][input[0].length()];
    static List<Elf> elves = new ArrayList<>();
    static List<Goblin> goblins = new ArrayList<>();
    static boolean nothingChanged = true;
    static boolean nothingChangedInLastRound = false;

    public static void main(String[] args) {
        long runTime = System.currentTimeMillis();
        int[] possibleNumbers = {4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 19, 20, 23, 25, 29, 34, 40, 50, 67, 100, 200};
        //TODO  - - sprawdzanie binarne
        for (int att : possibleNumbers) {
            elves = new ArrayList<>();
            goblins = new ArrayList<>();
            drawMap();
            System.out.println("attack:"+ att);
            if(battlePart2(att))
                break;
        }
        System.out.println("Running time: " + (double) (System.currentTimeMillis() - runTime) / 1000 + "s");




    }

    static void battlePart1() {
        int roundsCounter = 0;
        boolean noEnemies = false;
        while (!elves.isEmpty() && !goblins.isEmpty()) {
            //if nothing has changed on the map, interrupt units which couldnt move in the previous round
            nothingChanged = true;
            for (Unit unit : getSortedUnits()) {
                if (elves.isEmpty() || goblins.isEmpty()) {
                    noEnemies = true;
                    break;
                }
                if(!unit.alive) continue;
                boolean successfulAttack = unit.tryToAttack();
                if (!successfulAttack) {
                    unit.tryToMove();
                    unit.tryToAttack();
                }
            }
            if(noEnemies) break;
            roundsCounter++;
            nothingChangedInLastRound = nothingChanged;
            System.out.println(roundsCounter);
//            printSummary(roundsCounter);
//            printMap();
        }
        int result = getResult(roundsCounter);
        System.out.println(result);

    }

    static boolean battlePart2(int elvesAtt) {
        elves.forEach(e -> e.attPow = elvesAtt);
        int elvesNum = elves.size();
        int roundsCounter = 0;
        boolean noEnemies = false;
        while (elves.size()==elvesNum && !goblins.isEmpty()) {
            //if nothing has changed on the map, interrupt units which couldnt move in the previous round
            nothingChanged = true;
            for (Unit unit : getSortedUnits()) {
                if (elves.isEmpty() || goblins.isEmpty()) {
                    noEnemies = true;
                    break;
                }
                if(!unit.alive) continue;
                boolean successfulAttack = unit.tryToAttack();
                if (!successfulAttack) {
                    unit.tryToMove();
                    unit.tryToAttack();
                }
            }
            if(noEnemies) break;
            roundsCounter++;
            nothingChangedInLastRound = nothingChanged;
//            System.out.println(roundsCounter);
//            printSummary(roundsCounter);
//            printMap();
        }
        int result = getResult(roundsCounter);
        if (elves.size() == elvesNum) {

            System.out.println(result);
            return true;
        }
        return false;
    }

    static void drawMap() {
        for (int y = 0; y < input.length; y++) {
            String row = input[y];
            for (int x = 0; x < row.length(); x++) {
                char c = row.charAt(x);
                plan[y][x] = c;
                if(c=='E')
                    elves.add(new Elf(x, y));
                else if(c=='G')
                    goblins.add(new Goblin(x, y));
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

    static Set<Unit> getUnits() {
        Set<Unit> units = new HashSet<>();
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
