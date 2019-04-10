package chat;

import packet.AbstractPacket;
import packet.PacketAuthorize;
import packet.PacketMessage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientLoader {
    private static Socket socket;

    private static boolean sendUsername = false;

    public static void main(String[] args) {
        connectToServer();
        handle();
        disconnect();
    }


    private static void connectToServer() {
        try {
            socket = new Socket("localhost", 4000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendPacket(AbstractPacket packet) {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeShort(packet.getId());
            packet.write(out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handle() {
        ServerHandler serverHandler = new ServerHandler(socket);
        serverHandler.start();
        readChat();
    }

    private static void readChat() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals("/end")) {
                    disconnect();
                }
                if (!sendUsername) {
                    sendUsername = true;
                    sendPacket(new PacketAuthorize(line));
                    continue;
                }
                sendPacket(new PacketMessage(null, line));
            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
