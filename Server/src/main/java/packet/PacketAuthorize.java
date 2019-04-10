package packet;

import chat.ServerLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketAuthorize extends AbstractPacket {
    private static final Logger LOGGER = LogManager.getLogger(PacketAuthorize.class);

    private String username;

    public PacketAuthorize() {
    }

    public PacketAuthorize(String username) {
        this.username = username;
    }

    public short getId() {
        return 1;
    }

    public void read(DataInputStream in) throws IOException {
        username = in.readUTF();
    }

    public void write(DataOutputStream out) throws IOException {
    }

    @Override
    public void handle() {
        ServerLoader.getClientHandler(getSocket()).setUsername(username);
        System.out.println("Authorized new socket with username: " + username);
    }
}
