package game.alla.game;

import game.alla.game.helper.GridHelper;
import game.alla.game.model.Grid;
import game.alla.game.model.Point;
import game.alla.game.model.Ship;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;

import static game.alla.game.helper.GridHelper.*;
import static game.alla.game.helper.GridHelper.END_CHAR;

public class ShipTest {

    Grid grid;

    public static List<Set<Point>> data() {
        return List.of(Set.of(new Point(1, "A"), new Point(1, "B"), new Point(1, "C")),
                Set.of(new Point(2, "A"), new Point(2, "B"), new Point(2, "C")),
                Set.of(new Point(1, "A"), new Point(2, "C")));
    }

    @BeforeEach
    public void setUp() {
        grid = new Grid(GridHelper.generateEmptyGridCoordinates(START, SIZE, START_CHAR, END_CHAR));
    }

    @MethodSource(value = "data")
    @ParameterizedTest
    void isShipValid(Set<Point> points) {
        Ship ship = new Ship(points);
        ship.setGrid(grid);
        Assertions.assertTrue(ship.isShipValid());
    }

    @AfterEach
    public void tearDown() {

    }


}
