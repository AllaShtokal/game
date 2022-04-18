package game.alla.game.helper;

import game.alla.game.model.Grid;
import game.alla.game.model.GridPoint;
import game.alla.game.model.Point;
import game.alla.game.model.Ship;
import game.alla.game.enums.StatusForUser;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class GridHelper {

    public static final int SIZE = 10;
    public static final int START = 1;
    public static final char START_CHAR = 'A';
    public static final char END_CHAR = 'J';
    public static final String NO_SUCH_POINT = "No such point!";
    public static final Random random = new Random();

    public static Set<GridPoint> generateEmptyGridCoordinates(int start, int size, char startChar, char endChar) {
        final Set<Integer> numbers = IntStream.rangeClosed(start, size)
                .boxed()
                .collect(Collectors.toSet());

        final Set<String> letters = IntStream.rangeClosed(startChar, endChar)
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.toSet());

        return numbers.stream()
                .map(n -> letters.stream()
                        .map(l -> new GridPoint(new Point(n, l), StatusForUser.UNKNOWN, true))
                        .collect(Collectors.toSet()))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }


    public static String hit(Grid grid, Point point) {
        GridPoint gpoint = grid.getGridPoints().stream()
                .filter(gp -> gp.getPoint().equals(point))
                .findAny()
                .orElse(null);
        if (Objects.nonNull(gpoint)) {
            if (StatusForUser.getKnownStatuses().contains(gpoint.getStatusForUser())) {
                return gpoint.getStatusForUser().name();
            } else {
                Optional<Ship> any = grid.getShips().stream()
                        .filter(s -> s.getPoints().contains(point))
                        .findAny();
                if (any.isPresent()) {
                    updateGrid(grid, any.get(), gpoint);
                    return gpoint.getStatusForUser().name();

                } else {
                    gpoint.setStatusForUser(StatusForUser.MISS);
                    return StatusForUser.MISS.name();
                }
            }
        } else {
            return NO_SUCH_POINT;
        }
    }

    private static void updateGrid(Grid grid, Ship ship, GridPoint gp) {
        Set<GridPoint> shipGridPoints = ship.getGridPoints();
        if (ifToBeSunk(shipGridPoints)) {
            Set<Point> pointsAroundShip = getPointsAroundShip(ship.getPoints());
            grid.getGridPoints().stream()
                    .filter(p -> pointsAroundShip.contains(p.getPoint()))
                    .forEach(p -> p.setStatusForUser(StatusForUser.MISS));
            shipGridPoints.forEach(p -> p.setStatusForUser(StatusForUser.SUNK));
            ship.setIsAlive(false);
        } else {
            gp.setStatusForUser(StatusForUser.HIT);
        }

    }

    private static boolean ifToBeSunk(Set<GridPoint> shipGridPoints) {
        return shipGridPoints.stream()
                .filter(p -> p.getStatusForUser().equals(StatusForUser.HIT))
                .collect(Collectors.toSet()).size() == shipGridPoints.size() - 1;
    }

    public static Set<Point> getPointsAroundShip(Set<Point> points) {
        return points.stream()
                .map(GridHelper::getPointsAroundPoint)
                .flatMap(Set::stream)
                .filter(GridHelper::isValidPoint)
                .collect(Collectors.toSet());
    }


    public static boolean isValidPoint(Point point) {
        Integer x = point.getX();
        char y = point.getY().charAt(0);
        return x <= SIZE && x >= START && y >= START_CHAR && y <= END_CHAR;
    }

    public static Set<Point> getPointsAroundPoint(Point p) {
        return new HashSet<>(List.of(
                new Point(p.getX() + 1, p.getYWithShift(1)),
                new Point(p.getX() - 1, p.getYWithShift(-1)),
                new Point(p.getX() - 1, p.getYWithShift(1)),
                new Point(p.getX() + 1, p.getYWithShift(-1)),
                new Point(p.getX() + 1, p.getY()),
                new Point(p.getX() - 1, p.getY()),
                new Point(p.getX(), p.getYWithShift(1)),
                new Point(p.getX(), p.getYWithShift(-1))));
    }


}
