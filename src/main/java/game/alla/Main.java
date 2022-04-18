package game.alla;

import game.alla.game.helper.GridHelper;
import game.alla.game.model.Grid;
import game.alla.game.model.Point;
import game.alla.game.model.Ship;

import java.util.*;

import static game.alla.game.helper.GridHelper.*;


public class Main {

    public static final String START_GAME = "****** Start of the game: ";
    public static final String SHIPS_ON_A_GRID = "ships on a grid *****";
    public static final String END_GAME = "****** End of the game: you have sunk all of the ships!  *****";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Grid grid = new Grid(GridHelper.generateEmptyGridCoordinates(START, SIZE, START_CHAR, END_CHAR));
        List<Integer> sizes = List.of(5, 4, 4);
        sizes.forEach(s -> grid.addShip(new Ship(grid.getAvailableRandomShipPointSet(s, null))));
        //grid.addShip(new Ship(Set.of(new Point(2,"B"), new Point(2,"A"),new Point(2,"C"))));
        System.out.println(grid);
        System.out.println(START_GAME + sizes.size() + SHIPS_ON_A_GRID);
        do {
            Integer x = null;
            while (Objects.isNull(x)) {
                System.out.println("x: ");
                try {
                    x = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println(NO_SUCH_POINT);
                    sc.next();
                }
            }
            System.out.println("y: ");
            String y = String.valueOf(sc.next().charAt(0)).toUpperCase(Locale.ROOT);
            String hitResult = hit(grid, new Point(x, y));

            if (!hitResult.equals(NO_SUCH_POINT)) {
                System.out.println(grid);
            }
            System.out.println(hitResult);
        } while (!grid.getAllAliveSHips().isEmpty());
        System.out.println(END_GAME);


    }
}
