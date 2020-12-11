import Servers.GameServer;
import Servers.MenuServer;

public class Main {
    public static void main(String[] args) {
        try {
            MenuServer.init();
            GameServer.init();

            Thread menuServer = new Thread(MenuServer.menuServer);
            menuServer.start();

            Thread gameServer = new Thread(GameServer.gameServer);
            gameServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
