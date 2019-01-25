import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
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
        List<Unit> opponentsToAttack = new ArrayList<>();
        List<Unit> units = Main.getSortedUnits();
        for (Unit u : units) {
            if (this.pos.dist(u.pos) == 1 && !this.race.equals(u.race))
                opponentsToAttack.add(u);
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
        System.out.println(this.pos.x+","+this.pos.y+" attack " + opponent.race + "(" + opponent.pos.x + "," + opponent.pos.y);
        opponent.damage(this.attPow);
    }

    void damage(int damage) {
        this.HP -= damage;
        if (this.HP <= 0) {
            this.alive = false;
            if(Main.elves.contains(this)) Main.elves.remove(this);
            else Main.goblins.remove(this);
        }
    }

    Pointt tryToMove(List<Unit> units) {
        Pointt nextPoint = nextMove(units);
        if (nextPoint != null) {
            this.pos = nextPoint;
        }
        return nextPoint;
    }

    @Nullable
    Pointt nextMove(List<Unit> units) {
        Pointt nextPoint = null;
        int minDistance = Integer.MAX_VALUE;
        for (Unit unit : units) {
            if (!this.race.equals(unit.race)) {
                List<Pointt> shortestPath = findShortestPath(unit);
                int distance = shortestPath.size()-1;
                if (distance !=-1 && distance < minDistance) {
                    minDistance = distance;
                    nextPoint = shortestPath.get(1);
                }
            }
        }
        return nextPoint;
    }


    List<Pointt> findShortestPath(Pointt currentPoint, List<Pointt> path, Unit target) {
        List<List<Pointt>> paths = new ArrayList<>();
        List<Pointt> newPath = new ArrayList<>(path);
        newPath.add(currentPoint);
        if (currentPoint.dist(target.pos)==1)
            return newPath;
        List<Pointt> availablePoints = getAvailablePoints(currentPoint, newPath);
        if (availablePoints.isEmpty())
            return new ArrayList<>();   //DEAD END
        for (Pointt p : availablePoints) {
            paths.add(findShortestPath(p, newPath, target));
        }
        int minPathLength = Integer.MAX_VALUE;
        List<Pointt> shortestPath = new ArrayList<>();
        for (List<Pointt> p : paths) {
            if (!p.isEmpty() && p.size() < minPathLength) {
                shortestPath = p;
                minPathLength = p.size();
            }
        }
        return shortestPath;
    }

    List<Pointt> findShortestPath(Unit target) {
        return findShortestPath(this.pos, new ArrayList<>(), target);
    }

    List<Pointt> getAvailablePoints(Pointt p, List<Pointt> flaggedPoints) {
        int[][] translations = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
        List<Pointt> availablePoints = new ArrayList<>();
        for (int[] t : translations) {
            Pointt tPoint = new Pointt(p.x + t[1],p.y + t[0]);
            if(Main.plan[tPoint.y][tPoint.x] == '.' && !flaggedPoints.contains(tPoint) && findUnitByPosition(tPoint.y,tPoint.x)==null)
                availablePoints.add(tPoint);
        }
        return availablePoints;
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
        List<Unit> units = Main.getUnits();
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
