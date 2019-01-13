import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @Test
    void knownGames() {
        long highscore = Main.playGame(9, 25);
        assertEquals(32, highscore);

        highscore = Main.playGame(10,1618);
        assertEquals(8317, highscore);

        highscore = Main.playGame(13,7999);
        assertEquals(146373, highscore);

        highscore = Main.playGame(17,1104);
        assertEquals(2764, highscore);

        highscore = Main.playGame(21,6111);
        assertEquals(54718, highscore);

        highscore = Main.playGame(30,5807);
        assertEquals(37305, highscore);
    }
}