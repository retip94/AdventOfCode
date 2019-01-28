public class Main {
    public static void main(String[] args) {
        int[] registerBefore = {3, 2, 1, 1};
        int[] sample = {9, 2, 1, 2};
        int[] registerAfter = {3, 2, 2, 1};

        int opcodeMatcher = 0;
        int opCode = 0;
        int A = 1;
        int B = 2;
        int C = 3;

        //addr
        if((registerBefore[sample[A]]+registerBefore[sample[B]]) == registerAfter[sample[C]]) opcodeMatcher++;
        //addi
        if((registerBefore[sample[A]]+sample[B])==registerAfter[sample[C]]) opcodeMatcher++;
        //mulr
        if((registerBefore[sample[A]]*registerBefore[sample[B]]) == registerAfter[sample[C]]) opcodeMatcher++;
        //muli
        if((registerBefore[sample[A]]+sample[B])==registerAfter[sample[C]]) opcodeMatcher++;
        //banr
        //bani
        //borr
        //bori
        //setr
        if(registerBefore[sample[A]]==registerAfter[sample[C]]) opcodeMatcher++;
        //seti
        if(sample[A]==registerAfter[sample[C]]) opcodeMatcher++;
        //gtir

        System.out.println(opcodeMatcher);

    }
}
