package game.alla.game.model;

import game.alla.game.exception.NoSpaceException;
import game.alla.game.helper.GridHelper;
import game.alla.game.GridInterface;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static game.alla.game.helper.GridHelper.*;

@Getter
public class Grid implements GridInterface {

    Set<GridPoint> gridPoints;
    List<Ship> ships = new ArrayList<>();

    public Grid(Set<GridPoint> gridPoints) {
        this.gridPoints = gridPoints;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("  ");
        str.append(IntStream.rangeClosed(START_CHAR, END_CHAR)
                .mapToObj(c -> String.valueOf((char) c))
                .sorted().collect(Collectors.toList()).stream()
                .map(Object::toString).collect(Collectors.joining("  "))).append("\n");
        Set<Integer> numbers = IntStream.rangeClosed(START, SIZE).boxed().collect(Collectors.toSet());

        numbers.forEach(n ->
                str.append(n).append(this.getGridPoints().stream()
                        .filter(gp -> gp.getPoint().getX().equals(n))
                        .sorted(Comparator.comparingInt(p -> p.getPoint().getY().charAt(0)))
                        .map(gpoint -> gpoint.getStatusForUser().getValue()).collect(Collectors.joining())).append("\n")
        );
        return str.toString();
    }

    Set<Point> getAvailablePoints() {
        return this.gridPoints.stream().filter(GridPoint::getAvailableToPutShip).map(GridPoint::getPoint).collect(Collectors.toSet());
    }

    public List<Ship> getAllAliveSHips() {
        return ships.stream().filter(s -> s.getIsAlive().equals(true)).collect(Collectors.toList());
    }

    public void addShip(Ship ship) {
        ship.setGrid(this);
        if (!ship.isShipValid()) {
            throw new NoSpaceException();
        }
        this.ships.add(ship);
        updateGridPointsByShip(ship);
    }

    private void updateGridPointsByShip(Ship ship) {
        HashSet<Point> points = new HashSet<>(ship.getPoints());
        Set<Point> collect = GridHelper.getPointsAroundShip(points);
        collect.addAll(points);
        this.getGridPoints().stream().filter(gp -> collect.contains(gp.getPoint())).forEach(gp -> gp.setAvailableToPutShip(false));
    }


    public boolean isGridPointAvailable(Point point) {
        return this.getGridPoints().stream()
                .filter(gp -> gp.getPoint().equals(point))
                .findFirst()
                .map(GridPoint::getAvailableToPutShip).orElse(false);
    }


    public Set<Point> getAvailableRandomShipPointSet(final int size, final Boolean isHorizontal) {
        List<Set<Point>> list = Objects.nonNull(isHorizontal) ? getAvailableRandomShipPointSetList(size, isHorizontal) : getAvailableRandomShipPointSetList(size);
        return list.get(random.ints(0, list.size()).iterator().nextInt());
    }

    private List<Set<Point>> getAvailableRandomShipPointSetList(final int size) {
        return getAvailableRandomShipPointSetList(size, random.nextBoolean());
    }

    @Override
    public List<Set<Point>> getAvailableRandomShipPointSetList(final int size, final boolean isHorizontal) {
        Set<Point> availablePoints = this.getAvailablePoints();
        if (availablePoints.size() < size) {
            throw new NoSpaceException();
        }
        List<Set<Point>> collect = availablePoints.stream()
                .map(point -> generateSingleShipPointsSet(availablePoints, point, isHorizontal, size))
                .filter(s -> !s.isEmpty()).distinct().collect(Collectors.toList());
        if (collect.isEmpty()) {
            throw new NoSpaceException();
        }
        return collect;
    }

    private Set<Point> generateSingleShipPointsSet(Set<Point> availablePoints, Point startPoint, boolean isHorizontal, int size) {
        Set<Point> pointsSet = new LinkedHashSet<>();
        pointsSet.add(startPoint);
        int i = START;
        int incremented;
        while (i < size) {
            incremented = isHorizontal ? (startPoint.getY().charAt(0)) + i : startPoint.getX() + i;
            if (!isOutOfRange(incremented, isHorizontal)) {
                Point e = isHorizontal ? new Point(startPoint.getX(), String.valueOf((char) incremented)) : new Point(incremented, startPoint.getY());
                if (availablePoints.contains(e)) {
                    pointsSet.add(e);
                }
            }
            i++;
        }
        return pointsSet.size() == size ? pointsSet : Collections.emptySet();

    }

    private boolean isOutOfRange(int incremented, boolean isHorizontal) {
        return isHorizontal ? incremented > END_CHAR : incremented > SIZE;
    }


}
