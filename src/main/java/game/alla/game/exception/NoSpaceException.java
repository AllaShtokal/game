package game.alla.game.exception;

public class NoSpaceException extends RuntimeException {

    public static final String NO_EMPTY_SPACE = "No empty Spase for this size ship!";

    public NoSpaceException() {
        super(NO_EMPTY_SPACE);
    }

}
