import java.awt.*;

public class PointF extends Point {
    int squareSize;

    PointF(int x, int y, int squareSize) {
        super.x = x;
        super.y = y;
        this.squareSize = squareSize;
    }
    PointF() {
    }

    public int getSquareSize() {
        return squareSize;
    }
}

