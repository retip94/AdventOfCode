import java.util.*;


//In this task I can learn a lot about data structures
//At first I designed game table to be ArrayList, what was not a good Idea (33%progress in 10minutes)
//Then I replaced it with Deque (actually my implementation of CircleDeque) what decreased running time to 1s!!!!!!!!!!!

public class Main {
    public static void main(String[] args) {
        long runningTime = System.currentTimeMillis();

        int nPlayers = 403;
        int lastMarble = 7192000;
        System.out.println(playGame(nPlayers, lastMarble));
        System.out.println("Running time:" + (System.currentTimeMillis() - runningTime)/1000+"s");
        }

    static long playGame(int nPlayers, int lastMarble) {
        CircleDeque<Integer> circle = new CircleDeque<>();
        long[] scores = new long[nPlayers];
        circle.add(0);

        for (int marble = 1;marble<=lastMarble;marble++) {
            if (marble % 23 != 0) {
                circle.rotate(2);
                circle.addLast(marble);

            } else {
                circle.rotate(-7);
                scores[marble % nPlayers] += marble + circle.pop();
            }
        }
        return Arrays.stream(scores).max().getAsLong();
    }


}
