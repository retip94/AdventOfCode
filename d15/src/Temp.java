import java.util.HashMap;

public class Temp {
    public static void main(String[] args) {
        HashMap<Pointt, Integer> visited = new HashMap<>();
        Pointt p1 = new Pointt(1, 2);
        visited.put(p1, 10);
        Pointt p2 = new Pointt(2, 2);
        visited.put(p1, 20);
        System.out.println(visited.get(p1));
    }
}
