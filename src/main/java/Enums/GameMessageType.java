package Enums;

public enum  GameMessageType {

    INIT(0),
    TANK_POSITION(1),
    AMMO_INIT(2),
    AMMO_POSITION(3),
    AMMO_DESTROY(4),
    TANK_DESTROY(5),
    GAME_OVER(6);

    private int code;

    GameMessageType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
