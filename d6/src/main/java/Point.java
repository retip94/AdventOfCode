public class Point extends java.awt.Point {
    int x;
    int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int calculateDistanceTo(Point destination) {
        return (Math.abs(destination.x - x) + Math.abs(destination.y - y));
    }
}
