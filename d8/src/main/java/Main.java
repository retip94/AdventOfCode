import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Main {
    static List<Integer> input = new ArrayList<>();
    static int index = 0;
    static int metasSum = 0;

    public static void main(String[] args) {
        getInput();
        System.out.println(input);
        //task 1
        System.out.println("***task 1***");
        int rootValue = nextNode();
        System.out.println(metasSum);
        //task 2
        System.out.println("***task 2***");
        System.out.println(rootValue);
    }

    static int nextNode() {
        int childrenAmount = nextInt();
        int[] childrenValues = new int[childrenAmount];
        int metasAmount = nextInt();
        int[] metas = new int[metasAmount];
        int value = 0;

        for (int i = 0; i < childrenAmount; i++) {
            childrenValues[i] = nextNode();
        }
        for (int i = 0; i < metasAmount; i++) {
            int meta = nextInt();
            metasSum += meta;
            metas[i] = meta;
        }
        if (childrenAmount == 0) {
            value = IntStream.of(metas).sum();
        } else {
            for (int i = 0; i < metasAmount; i++) {
                int childrenRef = metas[i]-1;
                if (childrenRef<childrenAmount) value += childrenValues[childrenRef];
            }
        }
        return value;
    }

    static int nextInt() {
        index++;
        return input.get(index - 1);
    }

    static void getInput(){
        try {
            List<String> txtInput = Files.readAllLines(Paths.get("input.txt"), Charset.forName("UTF-8"));
            for (String str : txtInput.get(0).split(" ")) {
                input.add(Integer.parseInt(str));
            }
        } catch (Exception e) {
            System.out.println("error");
        }
    }
}
