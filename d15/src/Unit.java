import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public abstract class Unit {
    Pointt pos;
    int HP;
    int attPow;
    boolean alive;
    String race;

    Unit(int xPos, int yPos) {
        pos = new Pointt(xPos, yPos);
        this.HP = 200;
        this.attPow = 3;
        this.alive = true;
        this.race = "";
    }


    String getInfo() {
        return race+"     ("+pos.x+","+pos.y+") att:"+attPow+", HP:"+HP;
    }

    List<Unit> checkForOpponentsToAttack() {
        int[][] translations = {{1, 0}, {0, -1}, {0, 1}, {-1, 0}};
        List<Unit> opponentsToAttack = new ArrayList<>();
        Pointt p = this.pos;
        char opponent = this.race.equals("elf") ? 'G' : 'E';
        for (int[] t : translations) {
            if(Main.plan[p.y+t[0]][p.x+t[1]] == opponent)
                opponentsToAttack.add(findUnitByPosition(p.y+t[0],p.x+t[1]));
        }
        return opponentsToAttack;
    }

    boolean tryToAttack() {
        List<Unit> opponentsToAttack = checkForOpponentsToAttack();
        if (opponentsToAttack.size() > 0) {
            opponentsToAttack.sort(Unit::compareByHP);
            attack(opponentsToAttack.get(0));
            return true;
        }
        return false;
    }

    void attack(Unit opponent) {
        opponent.damage(this.attPow);
    }

    void damage(int damage) {
        this.HP -= damage;
    }

    void tryToMove(List<Unit> units) {
        
        findClosestOpponent(units);           <--------- check for nulls
        move///
    }

    @Nullable
    Unit findClosestOpponent(List<Unit> units) {
        Unit closestUnit = null;
        int minDistance = Integer.MAX_VALUE;
        for (Unit unit : units) {
            if (!this.race.equals(unit.race)) {
                int distance = findWayToOpponent(unit);
                if(distance==0)
                    continue;   //target is not reachable
                else if (distance < minDistance) {
                    minDistance = distance;
                    closestUnit = unit;
                }
                System.out.println(unit);
            }
        }
        return closestUnit;
    }

    Integer findWayToOpponent(Unit opponent) {
        Integer distance = 0;
        Pointt currentPoint = this.pos;
        Pointt targetPoint = opponent.pos;
        List<Pointt> flaggedPoints = new ArrayList<>();
        while (currentPoint.dist(targetPoint) > 1) {
            List<Pointt> availablePoints = getAvailablePoints(currentPoint, flaggedPoints);
            if(availablePoints.isEmpty())
                return 0;   //target is not reachable
            Pointt nextMove = getClosestPoint(targetPoint, availablePoints);
            flaggedPoints.add(currentPoint);
            currentPoint = nextMove;
            distance++;
        }
        return distance;
    }

    List<Pointt> getAvailablePoints(Pointt p, List<Pointt> flaggedPoints) {
        int[][] translations = {{1, 0}, {0, -1}, {0, 1}, {-1, 0}};
        List<Pointt> availablePoints = new ArrayList<>();
        for (int[] t : translations) {
            Pointt tPoint = new Pointt(p.x + t[1],p.y + t[0]);
            if(Main.plan[tPoint.y][tPoint.x] == '.' && !flaggedPoints.contains(tPoint))
                availablePoints.add(tPoint);
        }
        return availablePoints;
    }

    Pointt getClosestPoint(Pointt target, List<Pointt> points) {
        Pointt closestPoint = points.get(0);
        int minDist = Integer.MAX_VALUE;
        for (Pointt p : points) {
            int dist = target.dist(p);
            if (dist < minDist) {
                minDist = dist;
                closestPoint = p;
            }
        }
        return closestPoint;
    }

    int compareByPosition(Unit o2) {
        int value1 = this.pos.y - o2.pos.y;
        if (value1 == 0) {
            return this.pos.x - o2.pos.x;
        }
        return value1;
    }

    int compareByHP(Unit o2) {
        return this.HP - o2.HP;
    }

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
