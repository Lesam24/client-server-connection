package server;

import network.SocketConnection;
import protocol.Protocol;
import java.io.IOException;
import java.net.Socket;

public class ServerConnection extends SocketConnection {
    private ClientHandler clientHandler;

    public ServerConnection(Socket socket) throws IOException {
        super(socket);
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    protected void handleMessage(String message) {
        if (message == null || message.isEmpty()) {
            return;
        }

        String[] parts = message.split(Protocol.SEPARATOR, 2);

        if (parts.length < 2) {
            return;
        }

        String command = parts[0];
        String content = parts[1];

        switch (command) {
            case Protocol.USER:
                clientHandler.receiveUsername(content);
                break;
            case Protocol.SAY:
                clientHandler.receiveChatMessage(content);
                break;
            default:
                break;
        }
    }

    @Override
    protected void handleDisconnect() {
        clientHandler.handleDisconnect();
    }

    public void sendChatMessage(String username, String message) {
        String protocolMessage = Protocol.FROM + Protocol.SEPARATOR +
                username + Protocol.SEPARATOR + message;

        sendMessage(protocolMessage);
    }
}
