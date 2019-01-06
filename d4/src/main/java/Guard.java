import java.util.Arrays;

public class Guard {
    private int id;
    private int timeAsleep;
    private int[] minutesFrequency = new int[60];

    public Guard(int id) {
        this.id = id;
    }

    public void sleep(int fellAsleepTime, int wokeUpTime) {
        timeAsleep += (wokeUpTime - fellAsleepTime);
        for (int i = fellAsleepTime; i < wokeUpTime; i++) minutesFrequency[i]++;
    }

    public int[] getMostFrequentMinute() {
        int maxValue = 0;
        int maxKey = 0;
        for (int i = 0; i < minutesFrequency.length; i++) {
            if (minutesFrequency[i] > maxValue) {
                maxValue = minutesFrequency[i];
                maxKey = i;
            }
        }
        return new int[] {maxKey,maxValue};
    }

    public int getTimeAsleep() {
        return timeAsleep;
    }
}
