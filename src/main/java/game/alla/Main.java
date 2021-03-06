package game.alla;

import game.alla.game.helper.GridHelper;
import game.alla.game.model.Grid;
import game.alla.game.model.Point;
import game.alla.game.model.Ship;

import java.util.*;

import static game.alla.game.helper.GridHelper.*;


public class Main {

    public static final String START_GAME = "****** Start of the game: ";
    public static final String SHIPS_ON_A_GRID = " ships on a grid *****";
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
            String y = null;
            while (Objects.isNull(x) || Objects.isNull(y)) {
                System.out.println("Input coordinate,Ex. 2B : ");
                try {
                    String input = sc.nextLine().replaceAll("\\s+", "");
                    if (input.startsWith("10")) {
                        x = SIZE;
                        y = String.valueOf(input.charAt(2)).toUpperCase(Locale.ROOT);
                    } else {
                        x = Integer.parseInt(String.valueOf(input.charAt(0)));
                        y = String.valueOf(input.charAt(1)).toUpperCase(Locale.ROOT);
                    }

                } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                    System.out.println(NO_SUCH_POINT);
                }
            }
            String hitResult = hit(grid, new Point(x, y));

            if (!hitResult.equals(NO_SUCH_POINT)) {
                System.out.println(grid);
            }
            System.out.println(hitResult);
        } while (!grid.getAllAliveShips().isEmpty());
        System.out.println(END_GAME);


    }
}
