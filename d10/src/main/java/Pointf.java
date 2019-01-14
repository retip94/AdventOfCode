public class Pointf extends java.awt.Point {
    int velX;
    int velY;
    Pointf(int[] data) {
        super.x = data[0];
        super.y = data[1];
        this.velX = data[2];
        this.velY = data[3];
    }

    void move() {
        super.x += velX;
        super.y += velY;
    }

    void moveBack() {
        super.x -= velX;
        super.y -= velY;
    }
}
