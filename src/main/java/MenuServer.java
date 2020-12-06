import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class MenuServer {

    private static MenuServer menuServer;
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    private ByteBuffer buffer;
    private Properties properties;

    private Map<String, List<SelectionKey>> lobbies;

    public static void init() throws Exception {
        if (menuServer != null) throw new Exception("Сервер уже был создан");
        menuServer = new MenuServer();
        menuServer.start();
    }

    private MenuServer() throws Exception {
        properties = new Properties();
        properties.load(new FileReader(new File("src/main/resources/Server.properties")));

        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.bind(new InetSocketAddress(
                properties.getProperty("host"),
                Integer.parseInt(properties.getProperty("port"))));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, serverSocketChannel.validOps());

        buffer = ByteBuffer.allocate(Integer.parseInt(properties.getProperty("bufferSize")));
        System.out.println("Server started...");
        lobbies = new HashMap<>();
    }

    private void start() throws Exception {
        while (true) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();

                if (key.isAcceptable()) {
                    registerPlayer(selector, serverSocketChannel);
                }

                if (key.isWritable()) {
                    readData(buffer, key);
                }

                iter.remove();
            }
        }
    }

    private void readData(ByteBuffer buffer, SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();

        buffer.clear();

        int read = client.read(buffer);

        if (read <= 0) return;

        buffer.clear();

        int messageType = buffer.get();
        if (messageType == MenuMessageType.CREATE_LOBBY.getCode()) {
            createLobby(key);
        }
        if (messageType == MenuMessageType.DELETE_LOBBY.getCode()) {
            deleteLobby(key, buffer.array(), read);
        }
        if (messageType == MenuMessageType.CONNECT_TO_LOBBY.getCode()) {
            connectToLobby(key, buffer.array(), read);
        }
        if (messageType == MenuMessageType.DISCONNECT_FROM_LOBBY.getCode()) {
            disconnectFromLobby(key, buffer.array(), read);
        }
    }

    private void writeData(ByteBuffer buffer, SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        buffer.clear();
        client.write(buffer);
    }

    private void writeDataBroadcast(ByteBuffer buffer, List<SelectionKey> keys) throws IOException {
        for (SelectionKey key : keys) writeData(buffer, key);
    }

    private void registerPlayer(Selector selector, ServerSocketChannel serverSocketChannel) throws IOException {
        SocketChannel client = serverSocketChannel.accept();
        client.configureBlocking(false);
        client.register(selector, client.validOps());
    }

    private void createLobby(SelectionKey key) throws IOException {
        try {
            String passSymbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            Random rnd = new Random();
            StringBuilder stringResult = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                stringResult.append(passSymbols.charAt(rnd.nextInt(passSymbols.length())));
            }

            byte[] token = stringResult.toString().getBytes();
            ByteBuffer send = ByteBuffer.allocate(token.length + 1);
            send.put((byte) MenuMessageType.SUCCESSFUL_CREATED_LOBBY.getCode());

            lobbies.put(stringResult.toString(), new LinkedList<>());
            lobbies.get(stringResult.toString()).add(key);

            send.put(token);
            writeData(send, key);
        } catch (Exception e) {
            writeData(ByteBuffer.wrap(BigInteger.valueOf(MenuMessageType.CREATE_LOBBY_ERROR.getCode()).toByteArray()), key);
        }
    }

    private void deleteLobby(SelectionKey key, byte[] buffer, int read) throws IOException {
        try {
            String token = new String(buffer, 1, read - 1).replaceAll("\t", "").trim();

            if (lobbies.containsKey(token)) {
                writeDataBroadcast(ByteBuffer.wrap(BigInteger.valueOf(MenuMessageType.SUCCESSFUL_DELETE_LOBBY.getCode()).toByteArray()), lobbies.get(token));
                lobbies.remove(token);
            } else {
                writeData(ByteBuffer.wrap(BigInteger.valueOf(MenuMessageType.DELETE_LOBBY_ERROR.getCode()).toByteArray()), key);
            }
        } catch (Exception e) {
            writeData(ByteBuffer.wrap(BigInteger.valueOf(MenuMessageType.DELETE_LOBBY_ERROR.getCode()).toByteArray()), key);
        }
    }

    private void connectToLobby(SelectionKey key, byte[] buffer, int read) throws IOException {
        try {
            String token = new String(buffer, 1, read - 1).replaceAll("\t", "").trim();

            if (lobbies.containsKey(token) && lobbies.get(token).size() < 4) {
                lobbies.get(token).add(key);

                ByteBuffer success = ByteBuffer.allocate(2 + token.getBytes().length);
                success.put((byte) MenuMessageType.SUCCESSFUL_CONNECTED_TO_LOBBY.getCode());
                success.put((byte) lobbies.get(token).size());
                success.put(token.getBytes());

                writeDataBroadcast(success, lobbies.get(token));
            } else {
                writeData(ByteBuffer.wrap(BigInteger.valueOf(MenuMessageType.CONNECT_TO_LOBBY_ERROR.getCode()).toByteArray()), key);
            }
        } catch (Exception e) {
            writeData(ByteBuffer.wrap(BigInteger.valueOf(MenuMessageType.CONNECT_TO_LOBBY_ERROR.getCode()).toByteArray()), key);
        }
    }

    private void disconnectFromLobby(SelectionKey key, byte[] buffer, int read) throws IOException {
        try {
            String token = new String(buffer, 1, read - 1).replaceAll("\t", "").trim();

            boolean success = lobbies.get(token).remove(key);
            if (success) {
                ByteBuffer disconnect = ByteBuffer.allocate(2);
                disconnect.put((byte) MenuMessageType.SUCCESSFUL_DISCONNECTED_FROM_LOBBY.getCode());
                disconnect.put((byte) 1);

                writeData(disconnect, key);
                writeDataBroadcast(ByteBuffer.wrap(
                        BigInteger.valueOf(MenuMessageType.SUCCESSFUL_DISCONNECTED_FROM_LOBBY.getCode()).toByteArray()),
                        lobbies.get(token));
            } else {
                writeData(ByteBuffer.wrap(BigInteger.valueOf(MenuMessageType.DISCONNECTED_FROM_LOBBY_ERROR.getCode()).toByteArray()), key);
            }
        } catch (Exception e) {
            writeData(ByteBuffer.wrap(BigInteger.valueOf(MenuMessageType.DISCONNECTED_FROM_LOBBY_ERROR.getCode()).toByteArray()), key);
        }
    }

    public static void main(String[] args) {
        try {
            MenuServer.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

