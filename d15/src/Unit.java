import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class Unit {
    Pointt pos;
    int HP;
    int attPow;
    boolean alive;
    String race;
    char sign;
    boolean didntMoveLastRound;

    Unit(int xPos, int yPos) {
        pos = new Pointt(xPos, yPos);
        this.HP = 200;
        this.attPow = 3;
        this.alive = true;
        this.race = "";
        this.didntMoveLastRound = false;
    }


    String getInfo() {
        return race+"     ("+pos.x+","+pos.y+") att:"+attPow+", HP:"+HP;
    }

    List<Unit> checkForOpponentsToAttack(Pointt p) {
        int[][] translations = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
        List<Unit> opponentsToAttack = new ArrayList<>();
        for (int[] t : translations) {
            Pointt tPoint = new Pointt(p.x + t[1],p.y + t[0]);
            char c = Main.plan[tPoint.y][tPoint.x];
            if((c=='G' && this.sign=='E')||(c=='E'&&this.sign=='G'))
                opponentsToAttack.add(findUnitByPosition(tPoint));
        }
        return opponentsToAttack;
    }

    boolean tryToAttack() {
        List<Unit> opponentsToAttack = checkForOpponentsToAttack(this.pos);
        if (opponentsToAttack.size() > 0) {
            opponentsToAttack.sort(Unit::compareByHP);
            attack(opponentsToAttack.get(0));
            return true;
        }
        return false;
    }

    void attack(Unit opponent) {
//        System.out.println(this.pos.x+","+this.pos.y+" attack " + opponent.race + "(" + opponent.pos.x + "," + opponent.pos.y);
        opponent.damage(this.attPow);
    }

    void damage(int damage) {
        this.HP -= damage;
        if (this.HP <= 0)
            this.die();
    }

    void die() {
        Main.nothingChanged = false;
        this.alive = false;
        Main.plan[this.pos.y][this.pos.x] = '.';
        if(Main.elves.contains(this)) Main.elves.remove(this);
        else Main.goblins.remove(this);
    }

    Pointt tryToMove() {
//        System.out.println(this.getInfo());
        Pointt nextPoint = nextMove();
//        if (Main.nothingChanged && this.didntMoveLastRound) {
//            System.out.println("Running time: " + (double) (System.currentTimeMillis() - runTime) / 1000 + "s");
//            return nextPoint;
//        }

        if (nextPoint != null) {
            didntMoveLastRound = false;
            Main.nothingChanged = false;
            Main.plan[this.pos.y][this.pos.x] = '.';
            this.pos = nextPoint;
            Main.plan[this.pos.y][this.pos.x] = this.sign;
//            System.out.println("move to: " + nextPoint);
        } else
            didntMoveLastRound = true;
        return nextPoint;
    }

    @Nullable
    Pointt nextMove() {
        HashMap<Pointt, Integer> visited = new HashMap<>();
        visited.put(this.pos, 0);
        Pointt nextPoint = null;
        if (Main.nothingChanged && Main.nothingChangedInLastRound && this.didntMoveLastRound)
            return nextPoint;
        int minDistance = Integer.MAX_VALUE;
        List<Pointt> neighborPoints = getAvailablePoints(this.pos);
        Deque<QueueObj> movesQueue = new LinkedList<>();
        if (neighborPoints.isEmpty()) {
            return nextPoint;
        }
        for (Pointt neighborPoint : neighborPoints) {
            movesQueue.add(new QueueObj(neighborPoint,1,neighborPoint));
//            int distance = findShortestPath(neighborPoint);
//            if (distance != 0 && distance < minDistance) {
//                minDistance = distance;
//                nextPoint = neighborPoint;
//            }
        }
        while (!movesQueue.isEmpty()) {
            QueueObj currentMove = movesQueue.removeLast();
            Pointt currentPoint = currentMove.currentPoint;
            int distance = currentMove.distance;
            if(distance>minDistance)
                continue;
            visited.put(currentPoint, distance);
            if (!checkForOpponentsToAttack(currentPoint).isEmpty())
                if (distance < minDistance) {
                    minDistance = distance;
                    nextPoint = currentMove.firstMove;
                }
            for (Pointt p : getAvailablePoints(currentPoint)) {
                Integer existingDistance = visited.get(p);
                if ((existingDistance == null)||(distance < existingDistance)) {
                    movesQueue.add(new QueueObj(p, distance + 1, currentMove.firstMove));
                }
            }
        }
        return nextPoint;
    }


    Set<Pointt> findShortestPath(Pointt currentPoint, Set<Pointt> path) {
//        System.out.println(System.nanoTime() - Main.timer);
//        System.out.println(currentPoint);
//        Main.timer = System.nanoTime();
        Set<Set<Pointt>> paths = new HashSet<>();
        Set<Pointt> newPath = new HashSet<>(path);
        newPath.add(currentPoint);
        if(!checkForOpponentsToAttack(currentPoint).isEmpty())
            return newPath;
        List<Pointt> availablePoints = getAvailablePoints(currentPoint, newPath);
        if (availablePoints.isEmpty()){
            return new HashSet<>();   //DEAD END}
        }
        for (Pointt p : availablePoints) {
            paths.add(findShortestPath(p, newPath));
        }
        int minPathLength = Integer.MAX_VALUE;
        Set<Pointt> shortestPath = new HashSet<>();
        for (Set<Pointt> p : paths) {
            if (!p.isEmpty() && p.size() < minPathLength) {
                shortestPath = p;
                minPathLength = p.size();
            }
        }
        return shortestPath;
        }

    int findShortestPath(Pointt firstMove) {
        Set<Pointt> tempSet = new HashSet<>();
        tempSet.add(this.pos);
        return findShortestPath(firstMove, tempSet).size();
    }

    List<Pointt> getAvailablePoints(Pointt p) {
        int[][] translations = {{1, 0},{0, 1},{0, -1},{-1, 0}};
        List<Pointt> availablePoints = new ArrayList<>();
        for (int[] t : translations) {
            Pointt tPoint = new Pointt(p.x + t[1],p.y + t[0]);
            char c = Main.plan[tPoint.y][tPoint.x];
                if(c=='.')
                    availablePoints.add(tPoint);
            }
        return availablePoints;
    }


    List<Pointt> getAvailablePoints(Pointt p, Set<Pointt> flaggedPoints) {
//        int[][] translations = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
        int[][] translations = {{1, 0},{0, 1},{0, -1},{-1, 0}};
        List<Pointt> availablePoints = new ArrayList<>();
        for (int[] t : translations) {
            Pointt tPoint = new Pointt(p.x + t[1],p.y + t[0]);
            char c = Main.plan[tPoint.y][tPoint.x];
            if(c != '#' && !flaggedPoints.contains(tPoint)){
                if((c=='.')||(c=='G' && this.sign=='E')||(c=='E' && this.sign=='G'))
                    availablePoints.add(tPoint);
            }
        }
        return availablePoints;
    }
//    List<Pointt> getAvailablePoints(Pointt p) {return getAvailablePoints(p, new HashSet<>()); }

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

    int compareByDist(Unit o2, Unit target) {
        int dist1 = this.pos.dist(target.pos);
        int dist2 = o2.pos.dist(target.pos);
        return dist1 - dist2;
    }

    static Unit findUnitByPosition(int y, int x) {
        Set<Unit> units = Main.getUnits();
        for (Unit unit : units) {
            if(unit.pos.y==y)
                if(unit.pos.x==x)
                    return unit;
        }
        return null;
    }
    static Unit findUnitByPosition(Pointt p) {
        Set<Unit> units = Main.getUnits();
        for (Unit unit : units) {
            if(unit.pos.y==p.y)
                if(unit.pos.x==p.x)
                    return unit;
        }
        return null;
    }


}

class Elf extends Unit {
    Elf(int xPos, int yPos) {
        super(xPos, yPos);
        super.race = "elf";
        super.sign = 'E';
    }

}

class Goblin extends Unit {
    Goblin(int xPos, int yPos) {
        super(xPos, yPos);
        super.race = "goblin";
        super.sign = 'G';
    }

}
