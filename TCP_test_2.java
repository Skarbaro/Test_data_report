import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
public class TCPConnection {
    private final Socket socket;
    public TCPConnection(TCPConnectionListener eventListener, String ipAddr, int port) throws IOException {
        this(eventListener, new Socket(ipAddr, port));
    }
    public synchronized void sendString(String value) {
        try {
            out.write(value + "\r\n");
            out.flush();
        } catch (IOException e) {
            eventListener.onException(TCPConnection.this, e);
            disconnect();
        }
    }
    public synchronized void disconnect() {
        rxThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            eventListener.onException(TCPConnection.this, e);
        }
    }
    @Override
    public String toString() {
        return "TCPConnection: " + socket.getInetAddress() + ": " + socket.getPort();
    }
}