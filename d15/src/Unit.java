import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public abstract class Unit {
    Point pos;
    int HP;
    int attPow;
    boolean alive;
    String race;

    Unit(int xPos, int yPos) {
        pos = new Point(xPos, yPos);
        this.HP = 200;
        this.attPow = 3;
        this.alive = true;
        this.race = "";
    }

    int compareTo(Unit o2) {
        int value1 = Integer.compare(this.pos.y, o2.pos.y);
        if (value1 == 0) {
            return Integer.compare(this.pos.x, o2.pos.x);
        }
        return value1;
    }

    String getInfo() {
        return race+"     ("+pos.x+","+pos.y+") att:"+attPow+", HP:"+HP;
    }

    List<Unit> checkForOpponentsToAttack() {
        int[][] translations = {{1, 0}, {0, -1}, {0, 1}, {-1, 0}};
        List<Unit> opponentsToAttack = new ArrayList<>();
        Point p = this.pos;
        char opponent = this.race.equals("elf") ? 'G' : 'E';
        for (int[] t : translations) {
            if(Main.plan[p.y+t[0]][p.x+t[1]] == opponent)
                opponentsToAttack.add(findUnitByPosition(p.y+t[0],p.x+t[1]));
        }
        return opponentsToAttack;
    }

//    Unit findClosestOpponent(List<Unit> units) {
//        for (Unit unit : units) {
//            if (!this.race.equals(unit.race)) {
//
//            }
//        }
//    }

    static Unit findUnitByPosition(int y, int x) {
        List<Unit> units = Main.elves;
        units.addAll(Main.goblins);
        for (Unit unit : units) {
            if(unit.pos.y==y)
                if(unit.pos.x==x)
                    return unit;
        }
        return null;
    }

}

class Elf extends Unit {
    Elf(int xPos, int yPos) {
        super(xPos, yPos);
        super.race = "elf";
    }

}

class Goblin extends Unit {
    Goblin(int xPos, int yPos) {
        super(xPos, yPos);
        super.race = "goblin";
    }

}
