import java.util.Comparator;

public class Cart {
    Integer xPosition;
    Integer yPosition;
    char direction;
    private int intersectionsCounter;

    Cart(int xPosition, int yPosition, char direction) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.direction = direction;
        this.intersectionsCounter = 0;
    }

    void move() {
        switch (direction) {
            case '<':
                xPosition--;
                break;
            case '^':
                yPosition--;
                break;
            case '>':
                xPosition++;
                break;
            case 'v':
                yPosition++;
                break;
        }
    }

    void changeDirection(char trackSymbol) {
        switch (trackSymbol) {
            case '-':
                break;
            case '|':
                break;
            case '\\':
                if (direction=='<'||direction=='>') turnRight();
                else turnLeft();
                break;
            case '/':
                if (direction=='<'||direction=='>') turnLeft();
                else turnRight();
                break;
            case '+':
                if(intersectionsCounter==0) turnLeft();
                //for intersection==1 do nothing(go straight)
                else if(intersectionsCounter==2) {
                    turnRight();
                    intersectionsCounter = -1;
                }
                intersectionsCounter++;
                break;
        }

    }

    void turnLeft() {
        switch (direction) {
            case '<':
                direction='v';
                break;
            case '^':
                direction='<';
                break;
            case '>':
                direction='^';
                break;
            case 'v':
                direction='>';
                break;
        }
    }
    void turnRight() {
        switch (direction) {
            case '<':
                direction='^';
                break;
            case '^':
                direction='>';
                break;
            case '>':
                direction='v';
                break;
            case 'v':
                direction='<';
                break;
        }
    }

    public int compareTo(Cart o2) {
        int value1 = this.yPosition.compareTo(o2.yPosition);
        if (value1 == 0) {
            return this.xPosition.compareTo(o2.xPosition);
        }
        return value1;
    }

}
