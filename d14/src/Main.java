import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<Integer> scoreboard = new ArrayList<>();
    static Elf[] elves = new Elf[2];
    static int puzzleInput = 50376100;
    static String inputString = "503761";

    public static void main(String[] args) {
        long runTime = System.currentTimeMillis();
        //input
        scoreboard.add(3);
        scoreboard.add(7);
        elves[0] = new Elf(0);
        elves[1] = new Elf(1);
        //task 1
        while(scoreboard.size()<puzzleInput+10) {
            int recipesSum = elves[0].currentRecipe + elves[1].currentRecipe;
            if (recipesSum >= 10) {
                scoreboard.add(1);
                scoreboard.add(recipesSum % 10);
            } else {
                scoreboard.add(recipesSum);
            }
            elves[0].move();
            elves[1].move();
        }
        System.out.println("*** task 1***");
        System.out.println(getScores());
        System.out.println("Running time: " + (double) (System.currentTimeMillis() - runTime) / 1000 + "s");

        //task 2
        System.out.println("***task 2***");
        int left = findScores();
        System.out.println(left);
        System.out.println("Running time: " + (double) (System.currentTimeMillis() - runTime) / 1000 + "s");


    }

    static String getScores() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(scoreboard.get(puzzleInput + i));
        }
        return sb.toString();
    }

    static int findScores() {
//        String inputString = Integer.toString(puzzleInput);
        for (int i = 0; i < scoreboard.size() - inputString.length(); i++) {
            for (int j = 0; j < inputString.length(); j++) {
                if (inputString.charAt(j) != Integer.toString(scoreboard.get(i + j)).charAt(0) ) break;
                if(j==inputString.length()-1) return i;
            }
        }
        return 0;
    }
}
