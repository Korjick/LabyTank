package Servers;

import Enums.GameMessageType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

public class GameServer implements Runnable {

    public static GameServer gameServer;
    private DatagramChannel serverDatagramChannel;

    private final ByteBuffer buffer;
    private final Properties properties;

    private Map<String, List<SocketAddress>> lobbies;

    public static void init() throws Exception {
        if (gameServer != null) throw new Exception("Сервер уже был создан");
        gameServer = new GameServer();
    }

    private GameServer() throws IOException {
        properties = new Properties();
        properties.load(new FileReader(new File("src/main/resources/Server.properties")));

        serverDatagramChannel = DatagramChannel.open();

        serverDatagramChannel.bind(new InetSocketAddress(
                properties.getProperty("host"),
                Integer.parseInt(properties.getProperty("gamePort"))));
        serverDatagramChannel.configureBlocking(false);

        buffer = ByteBuffer.allocate(Integer.parseInt(properties.getProperty("bufferSize")));
        lobbies = new HashMap<>();
        System.out.println("Game server started...");
    }

    @Override
    public void run() {
        while (true) {
            try {
                SocketAddress socketAddress;
                buffer.clear();
                if ((socketAddress = serverDatagramChannel.receive(buffer)) != null) {

                    buffer.clear();
                    byte[] data = buffer.array();

                    if (data.length <= 0) continue;

                    if (data[0] == GameMessageType.INIT.getCode()) {
                        String token = new String(data, 1, data.length - 1).replaceAll("\t", "").trim();
                        if (lobbies.containsKey(token)) lobbies.get(token).add(socketAddress);
                    }
                    if (data[0] == GameMessageType.TANK_POSITION.getCode() && data.length >= 44) {
                        String token = new String(data, 38, data.length - 38).replaceAll("\t", "").trim();
                        if (lobbies.containsKey(token))
                            for (SocketAddress address : lobbies.get(token))
                                writeData(ByteBuffer.wrap(data), address);
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void writeData(ByteBuffer buffer, SocketAddress socketAddress) throws IOException {
        serverDatagramChannel.send(buffer, socketAddress);
    }

    public Map<String, List<SocketAddress>> getLobbies() {
        return lobbies;
    }
}


