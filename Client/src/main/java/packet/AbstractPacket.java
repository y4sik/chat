package packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class AbstractPacket {
    public abstract short getId();

    public abstract void read(DataInputStream in) throws IOException;

    public abstract void write(DataOutputStream out) throws IOException;

    public abstract void handle();


}
