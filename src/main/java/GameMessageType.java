public enum  GameMessageType {

    TANK_POSITION(16);

    private int code;

    GameMessageType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
