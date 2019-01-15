import java.util.List;

public class Main {
    String input = "#..#.#..##......###...###";
    public static void main(String[] args) {

    }

    boolean[] inputToArray() {
        boolean[] plantsState = new boolean[input.length()];
        for (int i = 0; i < input.length(); i++) {
            plantsState[i] = input.charAt(i) == '#';
        }
        return plantsState;
    }
}
