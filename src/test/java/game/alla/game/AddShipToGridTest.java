package game.alla.game;

import game.alla.game.exception.NoSpaceException;
import game.alla.game.helper.GridHelper;
import game.alla.game.model.Grid;
import game.alla.game.model.Ship;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static game.alla.game.exception.NoSpaceException.NO_EMPTY_SPACE;
import static game.alla.game.helper.GridHelper.*;


public class AddShipToGridTest {

    Grid grid;

    @BeforeEach
    public void setUp() {
        grid = new Grid(GridHelper.generateEmptyGridCoordinates(START, SIZE, START_CHAR, END_CHAR));
    }

    public static List<List<Map<Integer, Boolean>>> validData() {
        List<Map<Integer, Boolean>> horizontalShipsOf10Size = IntStream.range(0, 4).mapToObj(i -> Collections.singletonMap(10, true)).collect(Collectors.toList());
        return List.of(horizontalShipsOf10Size, List.of(Collections.singletonMap(5, true), Collections.singletonMap(4, false))
        );
    }

    public static List<List<Map<Integer, Boolean>>> inValidData() {
        List<Map<Integer, Boolean>> horizontalShipsOf10Size = IntStream.range(0, 10).mapToObj(i -> Collections.singletonMap(10, true)).collect(Collectors.toList());
        return List.of(horizontalShipsOf10Size, List.of(Collections.singletonMap(25, true), Collections.singletonMap(4, false))
        );
    }

    public static int[][] invalidDataArray() {
        return new int[][]{{1, 12, 1, 1}, {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10}, {5, 1, 1, 1, 1, 1, 1, 1, 10}, {20}};
    }


    public static int[][] validDataArray() {
        return new int[][]{{1, 1, 1}, {2, 2}, {1, 1, 1}};
    }

    @MethodSource(value = "validData")
    @ParameterizedTest
    void testAddValidShipsWithDirection(List<Map<Integer, Boolean>> list) {
        list.forEach(map -> grid.addShip(
                new Ship(grid.getAvailableRandomShipPointSet(map.keySet().stream().iterator().next(), map.values().stream().iterator().next()))));
        Assertions.assertEquals(list.size(), grid.getShips().size());
    }

    @MethodSource(value = "inValidData")
    @ParameterizedTest
    void testAddInValidShipsWithDirection(List<Map<Integer, Boolean>> list) {
        try {
            list.forEach(map -> grid.addShip(
                    new Ship(grid.getAvailableRandomShipPointSet(map.keySet().stream().iterator().next(), map.values().stream().iterator().next()))));
            Assertions.fail("Expected an NoSpaceException to be thrown");
        } catch (NoSpaceException e) {
            Assertions.assertEquals(NO_EMPTY_SPACE, e.getMessage());
        }
    }

    @MethodSource(value = "invalidDataArray")
    @ParameterizedTest
    void testInvalidShips(int[] data) {

        try {
            IntStream.range(0, data.length)
                    .forEach(i -> grid.addShip(new Ship(grid.getAvailableRandomShipPointSet(data[i], null))));
            Assertions.fail("Expected an NoSpaceException to be thrown");
        } catch (NoSpaceException e) {
            Assertions.assertEquals(NO_EMPTY_SPACE, e.getMessage());
        }

    }

    @MethodSource(value = "validDataArray")
    @ParameterizedTest
    void testValidShips(int[] data) {

        IntStream.range(0, data.length)
                .forEach(i -> grid.addShip(new Ship(grid.getAvailableRandomShipPointSet(data[i], null))));
        Assertions.assertEquals(data.length, grid.getShips().size());

    }



}
