package chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import packet.AbstractPacket;
import packet.PacketManager;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {
    private static final Logger LOGGER = LogManager.getLogger(ClientHandler.class);
    private final Socket client;
    private String username = "unknown";


    public ClientHandler(Socket client) {
        this.client = client;
        start();
    }

    @Override
    public void run() {
        while (true) {
            readData();
            if (!readData()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean readData() {
        try {
            DataInputStream in = new DataInputStream(client.getInputStream());
            if (in.available() <= 0) {
                return false;
            }
            short id = in.readShort();
            AbstractPacket packet = PacketManager.getPacket(id);
            packet.setSocket(client);
            packet.read(in);
            packet.handle();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void remove() {
        ServerLoader.remove(client);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
