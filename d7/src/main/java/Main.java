import java.lang.reflect.Array;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        //region input
        String[] input = {"Step C must be finished before step A can begin.", "Step C must be finished before step F can begin.", "Step A must be finished before step B can begin.", "Step A must be finished before step D can begin.", "Step B must be finished before step E can begin.", "Step D must be finished before step E can begin.", "Step F must be finished before step E can begin."};
//        String[] input = {"Step P must be finished before step Z can begin.","Step E must be finished before step O can begin.","Step X must be finished before step T can begin.","Step W must be finished before step V can begin.","Step K must be finished before step Y can begin.","Step C must be finished before step M can begin.","Step S must be finished before step R can begin.","Step T must be finished before step H can begin.","Step Z must be finished before step V can begin.","Step F must be finished before step L can begin.","Step V must be finished before step A can begin.","Step I must be finished before step A can begin.","Step J must be finished before step M can begin.","Step N must be finished before step Y can begin.","Step A must be finished before step B can begin.","Step H must be finished before step Q can begin.","Step Q must be finished before step O can begin.","Step D must be finished before step O can begin.","Step Y must be finished before step O can begin.","Step G must be finished before step L can begin.","Step B must be finished before step M can begin.","Step L must be finished before step U can begin.","Step M must be finished before step O can begin.","Step O must be finished before step U can begin.","Step R must be finished before step U can begin.","Step M must be finished before step U can begin.","Step Q must be finished before step U can begin.","Step K must be finished before step U can begin.","Step D must be finished before step R can begin.","Step A must be finished before step M can begin.","Step A must be finished before step Q can begin.","Step V must be finished before step Y can begin.","Step H must be finished before step G can begin.","Step P must be finished before step K can begin.","Step N must be finished before step A can begin.","Step P must be finished before step H can begin.","Step X must be finished before step Z can begin.","Step X must be finished before step K can begin.","Step Y must be finished before step U can begin.","Step F must be finished before step Q can begin.","Step W must be finished before step M can begin.","Step B must be finished before step L can begin.","Step E must be finished before step L can begin.","Step N must be finished before step O can begin.","Step I must be finished before step G can begin.","Step J must be finished before step H can begin.","Step Z must be finished before step N can begin.","Step V must be finished before step N can begin.","Step F must be finished before step B can begin.","Step A must be finished before step Y can begin.","Step Q must be finished before step R can begin.","Step L must be finished before step O can begin.","Step H must be finished before step U can begin.","Step V must be finished before step G can begin.","Step Z must be finished before step B can begin.","Step V must be finished before step J can begin.","Step V must be finished before step O can begin.","Step T must be finished before step D can begin.","Step Y must be finished before step M can begin.","Step B must be finished before step R can begin.","Step O must be finished before step R can begin.","Step C must be finished before step V can begin.","Step W must be finished before step T can begin.","Step P must be finished before step N can begin.","Step L must be finished before step R can begin.","Step V must be finished before step U can begin.","Step C must be finished before step J can begin.","Step N must be finished before step R can begin.","Step X must be finished before step S can begin.","Step X must be finished before step A can begin.","Step G must be finished before step O can begin.","Step A must be finished before step O can begin.","Step X must be finished before step O can begin.","Step D must be finished before step Y can begin.","Step C must be finished before step G can begin.","Step K must be finished before step D can begin.","Step N must be finished before step B can begin.","Step C must be finished before step B can begin.","Step W must be finished before step F can begin.","Step E must be finished before step Z can begin.","Step S must be finished before step V can begin.","Step G must be finished before step M can begin.","Step T must be finished before step B can begin.","Step W must be finished before step C can begin.","Step D must be finished before step G can begin.","Step L must be finished before step M can begin.","Step H must be finished before step D can begin.","Step G must be finished before step R can begin.","Step T must be finished before step J can begin.","Step A must be finished before step R can begin.","Step B must be finished before step O can begin.","Step J must be finished before step R can begin.","Step G must be finished before step U can begin.","Step K must be finished before step O can begin.","Step V must be finished before step L can begin.","Step M must be finished before step R can begin.","Step D must be finished before step U can begin.","Step H must be finished before step Y can begin.","Step P must be finished before step W can begin.","Step K must be finished before step I can begin.","Step J must be finished before step G can begin."};
        //endregion

        List<char[]>
                pairs = translateInputToPairs(input);
        Set<Character>
                availableLetters = findFirstLetters(pairs);
        StringBuilder
                correctOrder = new StringBuilder();

        System.out.println(Arrays.toString(input));


        while (!availableLetters.isEmpty()) {
            char letter = availableLetters.iterator().next();
            correctOrder.append(letter);
            availableLetters.remove(letter);
            availableLetters.addAll(findNextAvailable(pairs, letter));
        }
        System.out.println(availableLetters);
        System.out.println(correctOrder.toString());
    }

    static List<char[]> translateInputToPairs(String[] input) {
        List<char[]> pairs = new ArrayList<>();
        for (int i = 0; i < input.length; i++) {
            input[i] = input[i].replace("Step ", "");
            input[i] = input[i].replace(" must be finished before step ", "");
            input[i] = input[i].replace(" can begin.", "");
            char[] pair = {input[i].charAt(0), input[i].charAt(1)};
            pairs.add(pair);
        }
        return pairs;
    }

//    static char[][] translateInputToPairs(String[] input) {
//        char[][] pairs = new char[input.length][2];
//        for (int i = 0; i < input.length; i++) {
//            input[i] = input[i].replace("Step ", "");
//            input[i] = input[i].replace(" must be finished before step ", "");
//            input[i] = input[i].replace(" can begin.", "");
//            pairs[i][0] = input[i].charAt(0);
//            pairs[i][1] = input[i].charAt(1);
//        }
//        return pairs;
//    }

    static Set<Character> findFirstLetters(List<char[]> pairs) {
        //if letter doesnt appear at 2nd place in any pair -> it is first letter
        Set<Character> firstLetters = new HashSet<>();
        for (char[] pair1:pairs) {
            boolean isFirstLetter = true;
            for (char[] pair2 : pairs) {
                if (pair1[0] == pair2[1]) {
                    isFirstLetter = false;
                }
            }
            if(isFirstLetter) firstLetters.add(pair1[0]);
        }
        return firstLetters;
    }

    static Set<Character> findNextAvailable(List<char[]> pairs, char recentLetter) {
        Set<Character> availableLetters = new HashSet<>();
        for (char[] pair : pairs) {
            if (recentLetter == pair[0]) availableLetters.add(pair[1]);
        }
        return availableLetters;
    }

    }
