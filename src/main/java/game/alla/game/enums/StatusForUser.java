package game.alla.game.enums;


import java.util.List;

public enum StatusForUser {

    UNKNOWN(" U "),
    HIT(" # "),
    MISS(" * "),
    SUNK(" R "); // RIP
    private final String value;


    StatusForUser(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static List<StatusForUser> getKnownStatuses() {
        return List.of(HIT, MISS, SUNK);
    }


}

