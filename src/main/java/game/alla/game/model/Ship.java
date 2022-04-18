package game.alla.game.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class Ship {

    Grid grid;
    Set<Point> points;
    Boolean isAlive = true;

    public Ship(Set<Point> points) {
        this.points = points;
    }

    public  Set<GridPoint> getGridPoints() {
        return grid.getGridPoints().stream().filter(gp -> points.contains(gp.getPoint())).collect(Collectors.toSet());
    }

    public boolean isShipValid() {
        return this.getPoints().stream().allMatch(p -> grid.isGridPointAvailable(p));
    }

}
