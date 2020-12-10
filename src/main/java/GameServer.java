import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;

public class GameServer {

    public static GameServer gameServer;

    public static void init() throws Exception {
        if (gameServer != null) throw new Exception("Сервер уже был создан");
        gameServer = new GameServer();
    }

    private GameServer() {
        System.out.println("Game server started...");
    }

    public void check(int messageType, SelectionKey key, byte[] array, int read) throws IOException {
        if (messageType == GameMessageType.TANK_POSITION.getCode()) {
            String token = new String(array, 16, read - 16).replaceAll("\t", "").trim();

            if(MenuServer.menuServer.getLobbies().containsKey(token)){
                MenuServer.menuServer.writeDataBroadcast(ByteBuffer.wrap(array),
                        MenuServer.menuServer.getLobbies().get(token));
            }
        }
    }
}
