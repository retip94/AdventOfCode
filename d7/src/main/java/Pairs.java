import java.util.*;

public class Pairs {
    private List<char[]> pairsList = new ArrayList<>();

    Pairs(String[] input) {
        for (int i = 0; i < input.length; i++) {
            input[i] = input[i].replace("Step ", "");
            input[i] = input[i].replace(" must be finished before step ", "");
            input[i] = input[i].replace(" can begin.", "");
            char[] pair = {input[i].charAt(0), input[i].charAt(1)};
            pairsList.add(pair);
        }
    }

    List<char[]> findFirstLetters() {
        //if letter doesnt appear at 2nd place in any pair -> it is first letter
        List<Character> letters = new ArrayList<>();    //list used to avoid duplications
        List<char[]> firstLetters = new ArrayList<>();
        for (char[] pair1: pairsList) {
            boolean isFirstLetter = true;
            for (char[] pair2 : pairsList) {
                if (pair1[0] == pair2[1]) {
                    isFirstLetter = false;
                    break;
                }
            }
            if(isFirstLetter && !letters.contains(pair1[0])){
                char[] newPair = {0, pair1[0]};
                firstLetters.add(newPair);
                letters.add(pair1[0]);
            }
        }
        return firstLetters;
    }

    List<char[]> findNextAvailable(char letter) {
        List<char[]> nextLetters = new ArrayList<>();
        for (char[] pair : pairsList) {
            //find those connected with recent letter
            if (letter == pair[0]){
                boolean allConditionsMet = true;
                for (char[] pair2 : pairsList) {
                    //if one of the pairsList is still not done ->dont add this letter
                    if ((pair[1] == pair2[1]) && (pair2[0] != letter)) {
                        allConditionsMet = false;
                    }
                }
                if (allConditionsMet) nextLetters.add(pair);
            }
        }
        return nextLetters;
    }

    void remove(char letter) {
        for (int i = pairsList.size()-1; i >= 0; i--) {
            if(pairsList.get(i)[0]==letter){
                pairsList.remove(i);
            }
        }
    }

    void print() {
        for (char[] pair : pairsList) {
            System.out.print(pair[0]+"." + pair[1] + ", ");
        }
    }

}
