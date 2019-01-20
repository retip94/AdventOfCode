import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static List<List<Character>> trackMap = new ArrayList<>();
    static List<Cart> carts = new ArrayList<>();
    static List<Cart> cartsToRemove = new ArrayList<>();
    public static void main(String[] args) {
        int ticks = 0;
        drawMap("input2.txt");
        for (Cart cart : carts) {
            System.out.println(cart.xPosition + "," + cart.yPosition);
        }
        Crash crash = null;
        //task 1

        System.out.println("***task 1***");
        while (crash == null) {
            ticks++;
            carts.sort((cart1, cart2) -> cart1.compareTo(cart2));
            for (Cart cart : carts) {
                cart.move();
                cart.changeDirection(trackMap.get(cart.yPosition).get(cart.xPosition));
                crash = checkForCrash(cart);
                if (crash != null) {
                    System.out.println("First crash:");
                    System.out.println(crash.xPosition + ", " + crash.yPosition);
                    cartsToRemove.add(crash.cart1);
                    cartsToRemove.add(crash.cart2);
                    break;
                }
            }
        }
        carts.removeAll(cartsToRemove);

        //task 2
        System.out.println("***task 2***");
        while (carts.size() > 1) {

            ticks++;
            carts.sort((cart1, cart2) -> cart1.compareTo(cart2));
            for (Cart cart : carts) {
                if (!cartsToRemove.contains(cart)) {
                    cart.move();
                    cart.changeDirection(trackMap.get(cart.yPosition).get(cart.xPosition));
                    crash = checkForCrash(cart);
                }
                if (crash != null) {
                    cartsToRemove.add(crash.cart1);
                    cartsToRemove.add(crash.cart2);
                }
            }
            carts.removeAll(cartsToRemove);
            cartsToRemove = new ArrayList<>();
        }
        if (carts.size() == 1) {
            System.out.println("The last cart standing");
            Cart lastCart = carts.get(0);
            System.out.println(lastCart.xPosition + ", " + lastCart.yPosition);
        }

        System.out.println(ticks);
    }

    static void drawMap(String inputFile) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputFile), Charset.forName("UTF-8"));
            for (int y = 0; y < lines.size(); y++) {
                trackMap.add(new ArrayList<>());
                for (int x = 0; x < lines.get(y).length(); x++) {
                    char c = lines.get(y).charAt(x);
                    if (c == '<' || c == '^' || c == '>' || c == 'v') {
                        carts.add(new Cart(x, y, c));
                        c = (c == '<' || c == '>') ? '-' : '|';
                    }
                    trackMap.get(y).add(c);
                }
            }
        } catch (Exception e) {
            System.out.println("error");
            System.out.println(e.getMessage());
        }
    }

    static void printMapWithCarts() {
        Character[][] tempMap = trackMap.stream().map(c -> c.toArray(new Character[0])).toArray(Character[][]::new);
        for (Cart cart : carts) {
            tempMap[cart.yPosition][cart.xPosition] = cart.direction;
        }
        System.out.println(Arrays.deepToString(tempMap).replace("], ", "]\n").replaceAll("(\\[|\\]|, )",""));
    }

    static Crash checkForCrash(Cart cart) {
        int x = cart.xPosition;
        int y = cart.yPosition;
        for (Cart otherCart : carts) {
            if (cart != otherCart && !cartsToRemove.contains(otherCart)) {
                if (otherCart.yPosition == y && otherCart.xPosition == x) {
                    System.out.println("crash at:" + x + "," + y);
                    return new Crash(x, y, cart, otherCart);
                }
            }
        }
        return null;
    }
}
