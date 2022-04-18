package game.alla.game.exception;

public class NoSpaceException extends RuntimeException {

    public static final String NO_EMPTY_SPACE = "No empty space for this size ship!";

    public NoSpaceException() {
        super(NO_EMPTY_SPACE);
    }

}
