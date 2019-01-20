public class Elf {
    int scoreboardIndex;
    int currentRecipe;

    Elf(int scoreboardIndex) {
        this.scoreboardIndex = scoreboardIndex;
        setCurrentRecipe();
    }

    void move() {
        int steps = currentRecipe + 1;
        for (int i = 0; i < steps; i++) {
            scoreboardIndex ++;
            if(scoreboardIndex==Main.scoreboard.size()) scoreboardIndex=0;
        }
        setCurrentRecipe();
    }


    private void setCurrentRecipe() {
        this.currentRecipe = Main.scoreboard.get(scoreboardIndex);
    }

}
