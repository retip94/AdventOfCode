import java.util.*;

public class Main {
    public static void main(String[] args) {
        //region input
        String[] input = {"Step P must be finished before step Z can begin.","Step E must be finished before step O can begin.","Step X must be finished before step T can begin.","Step W must be finished before step V can begin.","Step K must be finished before step Y can begin.","Step C must be finished before step M can begin.","Step S must be finished before step R can begin.","Step T must be finished before step H can begin.","Step Z must be finished before step V can begin.","Step F must be finished before step L can begin.","Step V must be finished before step A can begin.","Step I must be finished before step A can begin.","Step J must be finished before step M can begin.","Step N must be finished before step Y can begin.","Step A must be finished before step B can begin.","Step H must be finished before step Q can begin.","Step Q must be finished before step O can begin.","Step D must be finished before step O can begin.","Step Y must be finished before step O can begin.","Step G must be finished before step L can begin.","Step B must be finished before step M can begin.","Step L must be finished before step U can begin.","Step M must be finished before step O can begin.","Step O must be finished before step U can begin.","Step R must be finished before step U can begin.","Step M must be finished before step U can begin.","Step Q must be finished before step U can begin.","Step K must be finished before step U can begin.","Step D must be finished before step R can begin.","Step A must be finished before step M can begin.","Step A must be finished before step Q can begin.","Step V must be finished before step Y can begin.","Step H must be finished before step G can begin.","Step P must be finished before step K can begin.","Step N must be finished before step A can begin.","Step P must be finished before step H can begin.","Step X must be finished before step Z can begin.","Step X must be finished before step K can begin.","Step Y must be finished before step U can begin.","Step F must be finished before step Q can begin.","Step W must be finished before step M can begin.","Step B must be finished before step L can begin.","Step E must be finished before step L can begin.","Step N must be finished before step O can begin.","Step I must be finished before step G can begin.","Step J must be finished before step H can begin.","Step Z must be finished before step N can begin.","Step V must be finished before step N can begin.","Step F must be finished before step B can begin.","Step A must be finished before step Y can begin.","Step Q must be finished before step R can begin.","Step L must be finished before step O can begin.","Step H must be finished before step U can begin.","Step V must be finished before step G can begin.","Step Z must be finished before step B can begin.","Step V must be finished before step J can begin.","Step V must be finished before step O can begin.","Step T must be finished before step D can begin.","Step Y must be finished before step M can begin.","Step B must be finished before step R can begin.","Step O must be finished before step R can begin.","Step C must be finished before step V can begin.","Step W must be finished before step T can begin.","Step P must be finished before step N can begin.","Step L must be finished before step R can begin.","Step V must be finished before step U can begin.","Step C must be finished before step J can begin.","Step N must be finished before step R can begin.","Step X must be finished before step S can begin.","Step X must be finished before step A can begin.","Step G must be finished before step O can begin.","Step A must be finished before step O can begin.","Step X must be finished before step O can begin.","Step D must be finished before step Y can begin.","Step C must be finished before step G can begin.","Step K must be finished before step D can begin.","Step N must be finished before step B can begin.","Step C must be finished before step B can begin.","Step W must be finished before step F can begin.","Step E must be finished before step Z can begin.","Step S must be finished before step V can begin.","Step G must be finished before step M can begin.","Step T must be finished before step B can begin.","Step W must be finished before step C can begin.","Step D must be finished before step G can begin.","Step L must be finished before step M can begin.","Step H must be finished before step D can begin.","Step G must be finished before step R can begin.","Step T must be finished before step J can begin.","Step A must be finished before step R can begin.","Step B must be finished before step O can begin.","Step J must be finished before step R can begin.","Step G must be finished before step U can begin.","Step K must be finished before step O can begin.","Step V must be finished before step L can begin.","Step M must be finished before step R can begin.","Step D must be finished before step U can begin.","Step H must be finished before step Y can begin.","Step P must be finished before step W can begin.","Step K must be finished before step I can begin.","Step J must be finished before step G can begin."};
        //endregion

        Pairs pairs = new Pairs(input);
        List<char[]>
                availableLetters = pairs.findFirstLetters();
        StringBuilder
                correctOrder = new StringBuilder();

        while (!availableLetters.isEmpty()) {
//            Collections.sort(availableLetters, (a, b) -> Character.compare(a[1], b[1]));
            char letter = availableLetters.get(0)[1];
            correctOrder.append(letter);
            availableLetters.addAll(pairs.findNextAvailable(letter));
            pairs.remove(letter);
            availableLetters.remove(0);
        }
        System.out.println(correctOrder.toString());
    }
    }
