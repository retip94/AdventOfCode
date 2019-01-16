import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static String pots = "";
    static List<String> patterns = new ArrayList<>();
    public static void main(String[] args) {
        getInput();
        String oldPots = "..."+pots+"....";
        System.out.println(oldPots);
        int nGenerations = 1000;
        for (int gen = 1; gen <= nGenerations; gen++) {
            StringBuilder newPots = new StringBuilder();
            newPots.append(checkNextGeneration(".." + oldPots.substring(0, 3)));
            newPots.append(checkNextGeneration("." + oldPots.substring(0, 4)));
            for (int i = 2; i < (oldPots.length() - 2); i++) {
                newPots.append(checkNextGeneration(oldPots.substring(i - 2, i + 3)));
            }
            newPots.append("....");
            oldPots = newPots.toString();
        }
        int potsValue = getPotsValue(oldPots);
        System.out.println(potsValue);
        /*for task 2 ive noticed the pattern
        * 1000gens ->32328
        * 2000gens ->64328
        * 10000gens->320328
        * so its easy to calculate that 50000000000gens will give us value 1600000000328
        */
    }

    static char checkNextGeneration(String subPots) {
        for (String pattern : patterns) {
            if (subPots.equals(pattern)) {
                return '#';
            }
        }
        return '.';
    }

    static int getPotsValue(String pots) {
        int totalValue = 0;
        for (int i = 0; i < pots.length(); i++) {
            if(pots.charAt(i)=='#') totalValue += (i - 3);
        }
        return totalValue;
    }

    static void getInput() {
        String inputFile = "input.txt";
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputFile), Charset.forName("UTF-8"));
            pots = lines.get(0).replace("initial state: ", "");
            for (int i = 1; i < lines.size(); i++) {
                if (lines.get(i).contains("> #")) patterns.add(lines.get(i).replace(" => #", ""));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
