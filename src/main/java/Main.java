public class Main {
    public static void main(String[] args) {
        try {
            MenuServer.init();
            GameServer.init();

            MenuServer.menuServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
