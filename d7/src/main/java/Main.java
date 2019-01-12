import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //region input
//        String[] input = {"Step C must be finished before step A can begin.", "Step C must be finished before step F can begin.", "Step A must be finished before step B can begin.", "Step A must be finished before step D can begin.", "Step B must be finished before step E can begin.", "Step D must be finished before step E can begin.", "Step F must be finished before step E can begin."};
        String[] input = {"Step P must be finished before step Z can begin.","Step E must be finished before step O can begin.","Step X must be finished before step T can begin.","Step W must be finished before step V can begin.","Step K must be finished before step Y can begin.","Step C must be finished before step M can begin.","Step S must be finished before step R can begin.","Step T must be finished before step H can begin.","Step Z must be finished before step V can begin.","Step F must be finished before step L can begin.","Step V must be finished before step A can begin.","Step I must be finished before step A can begin.","Step J must be finished before step M can begin.","Step N must be finished before step Y can begin.","Step A must be finished before step B can begin.","Step H must be finished before step Q can begin.","Step Q must be finished before step O can begin.","Step D must be finished before step O can begin.","Step Y must be finished before step O can begin.","Step G must be finished before step L can begin.","Step B must be finished before step M can begin.","Step L must be finished before step U can begin.","Step M must be finished before step O can begin.","Step O must be finished before step U can begin.","Step R must be finished before step U can begin.","Step M must be finished before step U can begin.","Step Q must be finished before step U can begin.","Step K must be finished before step U can begin.","Step D must be finished before step R can begin.","Step A must be finished before step M can begin.","Step A must be finished before step Q can begin.","Step V must be finished before step Y can begin.","Step H must be finished before step G can begin.","Step P must be finished before step K can begin.","Step N must be finished before step A can begin.","Step P must be finished before step H can begin.","Step X must be finished before step Z can begin.","Step X must be finished before step K can begin.","Step Y must be finished before step U can begin.","Step F must be finished before step Q can begin.","Step W must be finished before step M can begin.","Step B must be finished before step L can begin.","Step E must be finished before step L can begin.","Step N must be finished before step O can begin.","Step I must be finished before step G can begin.","Step J must be finished before step H can begin.","Step Z must be finished before step N can begin.","Step V must be finished before step N can begin.","Step F must be finished before step B can begin.","Step A must be finished before step Y can begin.","Step Q must be finished before step R can begin.","Step L must be finished before step O can begin.","Step H must be finished before step U can begin.","Step V must be finished before step G can begin.","Step Z must be finished before step B can begin.","Step V must be finished before step J can begin.","Step V must be finished before step O can begin.","Step T must be finished before step D can begin.","Step Y must be finished before step M can begin.","Step B must be finished before step R can begin.","Step O must be finished before step R can begin.","Step C must be finished before step V can begin.","Step W must be finished before step T can begin.","Step P must be finished before step N can begin.","Step L must be finished before step R can begin.","Step V must be finished before step U can begin.","Step C must be finished before step J can begin.","Step N must be finished before step R can begin.","Step X must be finished before step S can begin.","Step X must be finished before step A can begin.","Step G must be finished before step O can begin.","Step A must be finished before step O can begin.","Step X must be finished before step O can begin.","Step D must be finished before step Y can begin.","Step C must be finished before step G can begin.","Step K must be finished before step D can begin.","Step N must be finished before step B can begin.","Step C must be finished before step B can begin.","Step W must be finished before step F can begin.","Step E must be finished before step Z can begin.","Step S must be finished before step V can begin.","Step G must be finished before step M can begin.","Step T must be finished before step B can begin.","Step W must be finished before step C can begin.","Step D must be finished before step G can begin.","Step L must be finished before step M can begin.","Step H must be finished before step D can begin.","Step G must be finished before step R can begin.","Step T must be finished before step J can begin.","Step A must be finished before step R can begin.","Step B must be finished before step O can begin.","Step J must be finished before step R can begin.","Step G must be finished before step U can begin.","Step K must be finished before step O can begin.","Step V must be finished before step L can begin.","Step M must be finished before step R can begin.","Step D must be finished before step U can begin.","Step H must be finished before step Y can begin.","Step P must be finished before step W can begin.","Step K must be finished before step I can begin.","Step J must be finished before step G can begin."};

        int workersAmount = 5;
        //endregion

        Pairs pairs = new Pairs(input);
        List<char[]>
                availableLetters = pairs.findFirstLetters();
        StringBuilder
                correctOrder = new StringBuilder();


        while (!availableLetters.isEmpty()) {
            //sort available pairs by second value
            Collections.sort(availableLetters, (a, b) -> Character.compare(a[1], b[1]));
            char letter = availableLetters.get(0)[1];
            correctOrder.append(letter);
            availableLetters.addAll(pairs.findNextAvailable(letter));
            pairs.remove(letter);
            availableLetters.remove(0);
        }
        //task 1
        System.out.println("***task 1***");
        System.out.println(correctOrder.toString());

        //task 2
        int timer = 0;
        Worker[] workers = new Worker[workersAmount];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker();
        }
        pairs = new Pairs(input);
        pairs.print();
        availableLetters = pairs.findFirstLetters();
        correctOrder = new StringBuilder();
        StringBuilder tasksSimulation = new StringBuilder();
        List<Character> finishedTasks = new ArrayList<>();

        while (pairs.getSize() != 0 || !workIsDone(workers) || !availableLetters.isEmpty()) {
            if (timer==75){
                System.out.println("break");}
            if (!availableLetters.isEmpty()) Collections.sort(availableLetters, (a, b) -> Character.compare(a[1], b[1]));
            for (Worker worker : workers) {
                if (worker.getSecondsLeft()>0){
                    worker.work();
                    if (worker.getSecondsLeft() == 0) {
                        finishedTasks.add(worker.getCurrentTask());
                    }
                } else if (worker.getSecondsLeft() == 0 && !availableLetters.isEmpty()) {
                    char newTask = availableLetters.get(0)[1];
                    worker.startWork(newTask);
                    availableLetters.remove(0);
                } else {
                    worker.stopWork();
                }
            }
            for (char task : finishedTasks) {
                correctOrder.append(task);
                availableLetters.addAll(pairs.findNextAvailable(task));
                pairs.remove(task);
            }
            finishedTasks = new ArrayList<>();
            tasksSimulation = collectData(tasksSimulation, timer, workers);
            timer++;
        }
        System.out.println(timer);
        System.out.println(correctOrder.toString());
        System.out.println(tasksSimulation.toString());

    }

    static boolean workIsDone(Worker[] workers) {
        for (Worker worker : workers) {
            if(worker.getSecondsLeft()>0) return false;
        }
        return true;
    }

    static StringBuilder collectData(StringBuilder sb, int timer, Worker[] workers) {
        sb.append(timer);
        for (Worker worker : workers) {
            sb.append(";");
            sb.append(worker.getCurrentTask());
        }
        sb.append("\n");
        return sb;
    }
    }
