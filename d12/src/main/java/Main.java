import java.util.List;

public class Main {
    static String pots = "#..#.#..##......###...###";
    static String[] patterns = {"...##","..#..",".#...",".#.#.",".#.##",".##..",".####","#.#.#","#.###","##.#.","##.##","###..","###.#","####."};
    public static void main(String[] args) {
        String oldPots = "..."+pots+"....";
        System.out.println(oldPots);
        int nGenerations = 20;
        for (int gen = 1; gen <= nGenerations; gen++) {
            StringBuilder newPots = new StringBuilder();
            newPots.append(checkNextGeneration(".." + oldPots.substring(0, 3)));
            newPots.append(checkNextGeneration("." + oldPots.substring(0, 4)));
            for (int i = 2; i < (oldPots.length() - 2); i++) {
                newPots.append(checkNextGeneration(oldPots.substring(i - 2, i + 3)));
            }
            newPots.append("....");
            oldPots = newPots.toString();
            System.out.println(oldPots);
        }
        int potsValue = getPotsValue(oldPots);
        System.out.println(potsValue);

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
}
