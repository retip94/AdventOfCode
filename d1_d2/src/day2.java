class day2 {
    private String[] input;

    day2(String[] _input) {
        this.input = _input;
    }

    int getTask1Solution() {
        return calculateChecksum();
    }

    private int calculateChecksum() {
        int[] doublesAndTriples = countStringsWithDoublesAndTriples(input);
        return doublesAndTriples[0] * doublesAndTriples[1];
    }

    private int[] countStringsWithDoublesAndTriples(String... strings) {
        boolean[] doublesAndTriples;
        int doublesCounter = 0;
        int triplesCounter = 0;
        for (String s : strings) {
            doublesAndTriples = checkStringForDoublesAndTriples(s);
            if (doublesAndTriples[0]) doublesCounter++;
            if (doublesAndTriples[1]) triplesCounter++;
        }
        return new int[]{doublesCounter, triplesCounter};
    }


    private boolean[] checkStringForDoublesAndTriples(String s) {
        boolean containDouble = false;
        boolean containtTriple = false;
        int occurence = 0;
        for (int i = 0; i < s.length(); i++) {
            occurence = check_occurence(s, s.charAt(i));
            if (occurence == 2) containDouble = true;
            else if (occurence == 3) containtTriple = true;
            //if both are found we can finish string
            if (containDouble && containtTriple) break;
        }
        return new boolean[] {containDouble, containtTriple};

    }

    private int check_occurence(String s, char c) {
        int counter = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) counter++;
        }
        return counter;
    }

    //------------------------------------------------------------------------------------------------------------------------
    //task2
    String getTask2Solution() {
        String[] similarStrings = findPairWith1CharDifference();
        return deleteDifferentLetter(similarStrings);
    }

    String deleteDifferentLetter(String[] strings) {
        String result = "";
        for (int i = 0; i < strings[0].length(); i++) {
            if(strings[0].charAt(i)==strings[1].charAt(i)) result += strings[0].charAt(i);
        }
        return result;
    }


    String[] findPairWith1CharDifference() {
        for (int i = 0; i < this.input.length - 1; i++) {
            for (int j = i + 1; j < this.input.length; j++) {
                if (calculateLevenshteinDistance(this.input[i], this.input[j]) == 1) {
                    return new String[]{this.input[i], this.input[j]};
                }
            }
        }
        return new String[]{"",""};
    }

    int calculateLevenshteinDistance (CharSequence lhs, CharSequence rhs) {
        int len0 = lhs.length() + 1;
        int len1 = rhs.length() + 1;

        // the array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++) cost[i] = i;

        // dynamically computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++) {
            // initial cost of skipping prefix in String s1
            newcost[0] = j;

            // transformation cost for each letter in s0
            for(int i = 1; i < len0; i++) {
                // matching current letters in both strings
                int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int cost_replace = cost[i - 1] + match;
                int cost_insert  = cost[i] + 1;
                int cost_delete  = newcost[i - 1] + 1;

                // keep minimum cost
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }

            // swap cost/newcost arrays
            int[] swap = cost; cost = newcost; newcost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[len0 - 1];
    }
}
