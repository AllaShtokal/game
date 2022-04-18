package game.alla.game;

import game.alla.game.model.Point;

import java.util.List;
import java.util.Set;

public interface GridInterface {
    List<Set<Point>> getAvailableRandomShipPointSetList(final int size, final boolean isHorizontal);
}
