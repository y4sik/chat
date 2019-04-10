package packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketAuthorize extends AbstractPacket {
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
    }

    public void write(DataOutputStream out) throws IOException {
        out.writeUTF(username);
    }

    public void handle() {

    }
}
