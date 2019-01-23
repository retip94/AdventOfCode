import java.awt.*;

public class Pointt extends Point {
    Pointt(int x, int y) {
        super(x, y);
    }

    int dist(Pointt p) {
        return Math.abs(super.x - p.x) + Math.abs(super.y - p.y);
    }
}
