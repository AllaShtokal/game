package game.alla.game;

import game.alla.game.helper.GridHelper;
import game.alla.game.model.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;

class GridHelperTest {

    public static List<Map<Point, Set<Point>>> data() {
        List<Map<Point, Set<Point>>> list = new ArrayList<>();
        Map<Point, Set<Point>> map = new HashMap<>();
        map.put(new Point(3, "C"), Set.of(new Point(2, "B"),
                new Point(2, "C"),
                new Point(2, "D"),
                new Point(3, "D"),
                new Point(4, "D"),
                new Point(4, "C"),
                new Point(4, "B"),
                new Point(3, "B")
        ));
        list.add(map);
        return list;
    }

    public static List<Point> validPoints() {
        return List.of(new Point(5, "J"), new Point(1, "A"), new Point(10, "E"));
    }

    public static List<Point> inValidPoints() {
        return List.of(new Point(11, "A"), new Point(1, "Z"), new Point(22, "A"));
    }


    @MethodSource(value = "data")
    @ParameterizedTest
    void testGetPointsAroundPoint(Map<Point, Set<Point>> map) {
        Set<Point> pointsAroundPoint = GridHelper.getPointsAroundPoint(map.keySet().iterator().next());
        Assertions.assertTrue(map.values().iterator().next().containsAll(pointsAroundPoint));
    }

    @MethodSource(value = "validPoints")
    @ParameterizedTest
    void isValidPoint(Point point) {
        Assertions.assertTrue(GridHelper.isValidPoint(point));
    }

    @MethodSource(value = "inValidPoints")
    @ParameterizedTest
    void isInValidPoint(Point point) {
        Assertions.assertFalse(GridHelper.isValidPoint(point));
    }
}