package packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketMessage extends AbstractPacket {
    private String sender;
    private String message;

    public PacketMessage() {
    }

    public PacketMessage(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public short getId() {
        return 2;
    }

    public void read(DataInputStream in) throws IOException {
        sender = in.readUTF();
        message = in.readUTF();

    }

    public void write(DataOutputStream out) throws IOException {
        out.writeUTF(message);
    }

    public void handle() {
        System.out.println(String.format("[%s] %s", sender, message));
    }
}
