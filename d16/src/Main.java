import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


//TESTING GIT in Intellij IDEA
public class Main {
    static final int opcode = 0;
    static final int A = 1;
    static final int B = 2;
    static final int C = 3;
    static List<String>[] opcodesDecoder = new ArrayList[16];
    static String[] decodedOpcodes = new String[16];
    public static void main(String[] args) {
        //task 1
        Arrays.setAll(opcodesDecoder, ArrayList::new);
        int[] lastRegister = new int[4];
        System.out.println("***task 1***");
        int samplesWhichBehaveLikeThreeOrMoreOpcodes = 0;
        for (int[][] input : getInput()) {
            if (getOpcodesMatches(input[0], input[1], input[2])>=3) {
                samplesWhichBehaveLikeThreeOrMoreOpcodes++;
            }
            lastRegister = input[2];
        }
        System.out.println(samplesWhichBehaveLikeThreeOrMoreOpcodes);

        //task 2
        System.out.println("*** task 2***");
        System.out.println(Arrays.toString(opcodesDecoder));
        //too make sure it will not get stuck in infinite loop
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < opcodesDecoder.length; j++) {
                List<String> opcodeList = opcodesDecoder[j];
                //eliminate opcodes by finding Lists with just single possibility
                if (opcodeList.size() == 1) {
                    String foundOpcodeName = opcodeList.get(0);
                    System.out.println(j + " -> " + foundOpcodeName);
                    decodedOpcodes[j] = foundOpcodeName;
                    Arrays.stream(opcodesDecoder).forEach((op) -> op.remove(foundOpcodeName));
                }
            }
        }
        for (int[] sample : getTestInput()) {
            lastRegister = executeOpcode(sample, lastRegister);
        }
        System.out.println(Arrays.toString(lastRegister));

    }

    static int getOpcodesMatches(int[] registerBefore, int[] sample, int[] registerAfter) {
        List<String> matchedOpcodes = new ArrayList<>();
        //addr
        if ((registerBefore[sample[A]] + registerBefore[sample[B]]) == registerAfter[sample[C]]) matchedOpcodes.add("addr");
        //addi
        if ((registerBefore[sample[A]] + sample[B]) == registerAfter[sample[C]]) matchedOpcodes.add("addi");
        //mulr
        if ((registerBefore[sample[A]] * registerBefore[sample[B]]) == registerAfter[sample[C]]) matchedOpcodes.add("mulr");
        //muli
        if ((registerBefore[sample[A]] * sample[B]) == registerAfter[sample[C]]) matchedOpcodes.add("muli");
        //banr
        if ((registerBefore[sample[A]] & registerBefore[sample[B]]) == registerAfter[sample[C]]) matchedOpcodes.add("banr");
        //bani
        if ((registerBefore[sample[A]] & sample[B]) == registerAfter[sample[C]]) matchedOpcodes.add("bani");
        //borr
        if ((registerBefore[sample[A]] | registerBefore[sample[B]]) == registerAfter[sample[C]]) matchedOpcodes.add("borr");
        //bori
        if ((registerBefore[sample[A]] | sample[B]) == registerAfter[sample[C]]) matchedOpcodes.add("bori");
        //setr
        if (registerBefore[sample[A]] == registerAfter[sample[C]]) matchedOpcodes.add("setr");
        //seti
        if (sample[A] == registerAfter[sample[C]]) matchedOpcodes.add("seti");
        //gtir
        if ((sample[A] > registerBefore[sample[B]] && registerAfter[sample[C]] == 1) || (sample[A] <= registerBefore[sample[B]] && registerAfter[sample[C]] == 0))
            matchedOpcodes.add("gtir");
        //gtri
        if ((registerBefore[sample[A]] > sample[B] && registerAfter[sample[C]] == 1) || (registerBefore[sample[A]] <= sample[B] && registerAfter[sample[C]] == 0))
            matchedOpcodes.add("gtri");
        //gtrr
        if ((registerBefore[sample[A]] > registerBefore[sample[B]] && registerAfter[sample[C]] == 1) || (registerBefore[sample[A]] <= registerBefore[sample[B]] && registerAfter[sample[C]] == 0))
            matchedOpcodes.add("gtrr");
        //eqir
        if ((sample[A] == registerBefore[sample[B]] && registerAfter[sample[C]] == 1) || (sample[A] != registerBefore[sample[B]] && registerAfter[sample[C]] == 0))
            matchedOpcodes.add("eqir");
        //eqri
        if ((registerBefore[sample[A]] == sample[B] && registerAfter[sample[C]] == 1) || (registerBefore[sample[A]] != sample[B] && registerAfter[sample[C]] == 0))
            matchedOpcodes.add("eqri");
        //eqrr
        if ((registerBefore[sample[A]] == registerBefore[sample[B]] && registerAfter[sample[C]] == 1) || (registerBefore[sample[A]] != registerBefore[sample[B]] && registerAfter[sample[C]] == 0))
            matchedOpcodes.add("eqrr");
        if (opcodesDecoder[sample[opcode]].isEmpty()) {
            opcodesDecoder[sample[opcode]].addAll(matchedOpcodes);
        } else {
            opcodesDecoder[sample[opcode]].retainAll(matchedOpcodes);
        }

        return matchedOpcodes.size();
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

    static List<int[]> getTestInput() {
        List<int[]> testInputs = new ArrayList<>();
        try {
            Stream<String> textStream = Files.lines(Paths.get("input2.txt"), Charset.forName("UTF-8"));
            textStream.forEach((line) -> testInputs.add(getSample(line)));
        } catch (Exception e) {
            System.out.println("error");
            System.out.println(e.getMessage());
        }
        return testInputs;
    }

    static int[] getRegister(String string) {
        string = string.replace("Before: [","").replace("After:  [","").replace("]","");
        return Arrays.stream(string.split(", ")).mapToInt(Integer::parseInt).toArray();
    }
    static int[] getSample(String string) {
        return Arrays.stream(string.split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    static int[] executeOpcode(int[] sample, int[] reg) {
        switch (decodedOpcodes[sample[opcode]]) {
            case "addr":
                reg[sample[C]] = reg[sample[A]] + reg[sample[B]];
                break;
            case "addi":
                reg[sample[C]] = reg[sample[A]] + sample[B];
                break;
            case "mulr":
                reg[sample[C]] = reg[sample[A]] * reg[sample[B]];
                break;
            case "muli":
                reg[sample[C]] = reg[sample[A]] * sample[B];
                break;
            case "banr":
                reg[sample[C]] = reg[sample[A]] & reg[sample[B]];
                break;
            case "bani":
                reg[sample[C]] = reg[sample[A]] & sample[B];
                break;
            case "borr":
                reg[sample[C]] = reg[sample[A]] | reg[sample[B]];
                break;
            case "bori":
                reg[sample[C]] = reg[sample[A]] | sample[B];
                break;
            case "setr":
                reg[sample[C]] = reg[sample[A]];
                break;
            case "seti":
                reg[sample[C]] = sample[A];
                break;
            case "gtir":
                reg[sample[C]] = sample[A] > reg[sample[B]] ? 1 : 0;
                break;
            case "gtri":
                reg[sample[C]] = reg[sample[A]] > sample[B] ? 1 : 0;
                break;
            case "gtrr":
                reg[sample[C]] = reg[sample[A]] > reg[sample[B]] ? 1 : 0;
                break;
            case "eqir":
                reg[sample[C]] = sample[A] == reg[sample[B]] ? 1 : 0;
                break;
            case "eqri":
                reg[sample[C]] = reg[sample[A]] == sample[B] ? 1 : 0;
                break;
            case "eqrr":
                reg[sample[C]] = reg[sample[A]] == reg[sample[B]] ? 1 : 0;
                break;
        }
        return reg;
    }
}
