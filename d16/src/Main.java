import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static final int opCode = 0;
    static final int A = 1;
    static final int B = 2;
    static final int C = 3;

    public static void main(String[] args) {
        int[] registerBefore = {3, 2, 1, 1};
        int[] sample = {9, 2, 1, 2};
        int[] registerAfter = {3, 2, 2, 1};

        System.out.println(getOpcodesMatches(registerBefore, sample, registerAfter));
        System.out.println(Arrays.deepToString(getInput().get(getInput().size()-1)));

    }

    static int getOpcodesMatches(int[] registerBefore, int[] sample, int[] registerAfter) {
        int opcodeMatcher = 0;
        //addr
        if ((registerBefore[sample[A]] + registerBefore[sample[B]]) == registerAfter[sample[C]]) opcodeMatcher++;
        //addi
        if ((registerBefore[sample[A]] + sample[B]) == registerAfter[sample[C]]) opcodeMatcher++;
        //mulr
        if ((registerBefore[sample[A]] * registerBefore[sample[B]]) == registerAfter[sample[C]]) opcodeMatcher++;
        //muli
        if ((registerBefore[sample[A]] * sample[B]) == registerAfter[sample[C]]) opcodeMatcher++;
        //banr
        if ((registerBefore[sample[A]] & registerBefore[sample[B]]) == registerAfter[sample[C]]) opcodeMatcher++;
        //bani
        if ((registerBefore[sample[A]] & sample[B]) == registerAfter[sample[C]]) opcodeMatcher++;
        //borr
        if ((registerBefore[sample[A]] | registerBefore[sample[B]]) == registerAfter[sample[C]]) opcodeMatcher++;
        //bori
        if ((registerBefore[sample[A]] | sample[B]) == registerAfter[sample[C]]) opcodeMatcher++;
        //setr
        if (registerBefore[sample[A]] == registerAfter[sample[C]]) opcodeMatcher++;
        //seti
        if (sample[A] == registerAfter[sample[C]]) opcodeMatcher++;
        //gtir
        if ((sample[A] > registerBefore[sample[B]] && registerAfter[sample[C]] == 1) || (sample[A] <= registerBefore[sample[B]] && registerAfter[sample[C]] == 0))
            opcodeMatcher++;
        //gtri
        if ((registerBefore[sample[A]] > sample[B] && registerAfter[sample[C]] == 1) || (registerBefore[sample[A]] <= sample[B] && registerAfter[sample[C]] == 0))
            opcodeMatcher++;
        //gtrr
        if ((registerBefore[sample[A]] > registerBefore[sample[B]] && registerAfter[sample[C]] == 1) || (registerBefore[sample[A]] <= registerBefore[sample[B]] && registerAfter[sample[C]] == 0))
            opcodeMatcher++;
        //eqir
        if ((sample[A] == registerBefore[sample[B]] && registerAfter[sample[C]] == 1) || (sample[A] != registerBefore[sample[B]] && registerAfter[sample[C]] == 0))
            opcodeMatcher++;
        //eqri
        if ((registerBefore[sample[A]] == sample[B] && registerAfter[sample[C]] == 1) || (registerBefore[sample[A]] != sample[B] && registerAfter[sample[C]] == 0))
            opcodeMatcher++;
        //eqrr
        if ((registerBefore[sample[A]] == registerBefore[sample[B]] && registerAfter[sample[C]] == 1) || (registerBefore[sample[A]] != registerBefore[sample[B]] && registerAfter[sample[C]] == 0))
            opcodeMatcher++;

        return opcodeMatcher;
    }

    static List<int[][]> getInput() {
        List<int[][]> inputs = new ArrayList<>();
        try {
            List<String> txtInput = Files.readAllLines(Paths.get("input.txt"), Charset.forName("UTF-8"));
            for (int i = 0; i < txtInput.size()-2; i+=4) {
                int[][] input = new int[3][];
                input[0] = getRegister(txtInput.get(i));
                input[1] = getSample(txtInput.get(i + 1));
                input[2] = getRegister(txtInput.get(i + 2));
                inputs.add(input);
            }
        } catch (Exception e) {
            System.out.println("error");
            System.out.println(e.getMessage());
        }
        return inputs;
    }

    static int[] getRegister(String string) {
        string = string.replace("Before: [","").replace("After:  [","").replace("]","");
        return Arrays.stream(string.split(", ")).mapToInt(Integer::parseInt).toArray();
    }
    static int[] getSample(String string) {
        return Arrays.stream(string.split(" ")).mapToInt(Integer::parseInt).toArray();
    }
}
