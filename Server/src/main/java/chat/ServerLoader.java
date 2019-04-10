package chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import packet.AbstractPacket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ServerLoader {
    private static final Logger LOGGER = LogManager.getLogger(ServerLoader.class);

    private static ServerSocket serverSocket;
    public static Map<Socket, ClientHandler> clientHandlers = new HashMap<>();
    private static ServerHandler serverHandler;

    public static void main(String[] args) {
        startServer();
        handleClient();
        shutDownServer();
    }


    private static void startServer() {
        try {
            serverSocket = new ServerSocket(4000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient() {
        serverHandler = new ServerHandler(serverSocket);
        readChat();
    }

    public static void sendPacket(Socket receiver, AbstractPacket packet) {
        try {
            DataOutputStream out = new DataOutputStream(receiver.getOutputStream());
            out.writeShort(packet.getId());
            packet.write(out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readChat() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals("/end")) {
                    shutDownServer();
                } else {
                    System.out.println("Unknown command!");
                }
            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void shutDownServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public static ClientHandler getClientHandler(Socket socket) {
        return clientHandlers.get(socket);
    }

    public static ServerHandler getServerHandler(Socket socket) {
        return serverHandler;
    }

    public static void remove(Socket socket) {
        clientHandlers.remove(socket);
    }

}
