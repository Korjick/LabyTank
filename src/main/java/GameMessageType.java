public enum  GameMessageType {

    INIT(0),
    TANK_POSITION(1);

    private int code;

    GameMessageType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
