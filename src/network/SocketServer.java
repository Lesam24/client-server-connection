package network;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;


public abstract class SocketServer {
    private final ServerSocket serverSocket;

    protected SocketServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    protected int getPort() {
        return serverSocket.getLocalPort();
    }

    protected void acceptConnections() throws IOException {
        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                handleConnection(socket);
            } catch (IOException ignored) {
                // Ignore since socket is closing.
            }
        }
    }

    protected synchronized void closeSocket() {
        try {
            if (!serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException ignored) {
            // Ignore.
        }
    }

    protected abstract void handleConnection(Socket socket);
}
