import java.util.ArrayList;
import java.util.List;

class day1 {

    private int[] input;
    day1(int[] _input) {
        this.input = _input;
    }

    public int getTask1Solution() {
        int solution = 0;
        for (int n : this.input) {
            solution += n;
        }
        return solution;
    }

    public int getTask2Solution() {
        int j = 0;
        int freq = 0;
        List<Integer> freqList = new ArrayList<>();
        while (j < 10000000) {    //j condition is just to prevent from infinite loop
            for (int n : this.input) {
                freqList.add(freq);
                freq = freqList.get(j) + n;
                if (freqList.contains(freq)) {
                    return freq;
                }
                j++;
            }
        }
        return 0;
    }
}
