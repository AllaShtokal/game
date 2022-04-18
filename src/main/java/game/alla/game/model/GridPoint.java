package game.alla.game.model;

import game.alla.game.enums.StatusForUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GridPoint {

    private Point point;
    private StatusForUser statusForUser;
    private Boolean availableToPutShip;

    public GridPoint(Point point, StatusForUser statusForUser, boolean availableToPutShip) {
        this.point = point;
        this.statusForUser = statusForUser;
        this.availableToPutShip = availableToPutShip;
    }

}
