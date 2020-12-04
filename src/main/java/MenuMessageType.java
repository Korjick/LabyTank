public enum MenuMessageType {
    DELETE_LOBBY_ERROR(11),

    DISCONNECTED_FROM_LOBBY_ERROR(8),

    CONNECT_TO_LOBBY_ERROR(5),

    CREATE_LOBBY_ERROR(2),

    // ^
    // |   Ошибки
    // |

    CREATE_LOBBY(0),

    // |
    // |   Успех/действия
    // ˅

    SUCCESSFUL_CREATED_LOBBY(1),

    CONNECT_TO_LOBBY(3),
    SUCCESSFUL_CONNECTED_TO_LOBBY(4),

    DISCONNECT_FROM_LOBBY(6),
    SUCCESSFUL_DISCONNECTED_FROM_LOBBY(7),

    DELETE_LOBBY(9),
    SUCCESSFUL_DELETE_LOBBY(10);

    private int code;
    MenuMessageType(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

