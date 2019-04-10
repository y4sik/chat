package chat;

import packet.AbstractPacket;
import packet.PacketManager;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerHandler extends Thread {
    private Socket socket;

    public ServerHandler() {
    }

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        while (true) {
            try {
                DataInputStream in = new DataInputStream(socket.getInputStream());
                if (in.available() <= 0) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();/////////////////////
                    }
                    continue;
                }
                short id = in.readShort();
                AbstractPacket packet = PacketManager.getPacket(id);
                packet.read(in);
                packet.handle();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
