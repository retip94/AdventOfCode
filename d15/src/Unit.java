public class Unit {
    int HP;
    int xPos;
    int yPos;
    int attPow;

    Unit(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.HP = 200;
        this.attPow = 3;
    }

}

class Elf extends Unit {
    Elf(int xPos, int yPos) {
        super(xPos, yPos);
    }
}

class Goblin extends Unit {
    Goblin(int xPos, int yPos) {
        super(xPos, yPos);
    }
}
