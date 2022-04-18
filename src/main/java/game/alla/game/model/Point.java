package game.alla.game.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
@Getter
@Setter
@ToString
public class Point {

    private Integer x;
    private String y;


    public Point(Integer x, String y) {
        this.x = x;
        this.y = y;
    }

    public String getYWithShift(int i){
        return String.valueOf((char)(y.charAt(0)+i));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x.equals(point.x) && y.equals(point.y);
    }


    public int hashCode() {
        return Objects.hash(x, y);
    }



}
