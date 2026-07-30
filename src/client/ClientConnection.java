package client;

import network.SocketConnection;
import protocol.Protocol;
import java.io.IOException;
import java.net.InetAddress;

public class ClientConnection extends SocketConnection {
    private ChatClient chatClient;

    public ClientConnection(InetAddress address, int port) throws IOException {
        super(address, port);
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    protected void handleMessage(String message) {
        if (message == null || message.isEmpty()) {
            return;
        }
        String[] parts = message.split(Protocol.SEPARATOR, 3);
        if (parts.length < 3) {
            return;
        }

        String command = parts[0];

        if (Protocol.FROM.equals(command)) {
            String username = parts[1];
            String messageContent = parts[2];
            chatClient.receiveChatMessage(username, messageContent);
        }
    }

    @Override
    protected void handleDisconnect() {
        chatClient.handleDisconnect();
    }

    public void sendUsername(String username) {
        String message = Protocol.USER + Protocol.SEPARATOR + username;
        sendMessage(message);
    }

    public void sendChatMessage(String message) {
        String protocolMessage = Protocol.SAY + Protocol.SEPARATOR + message;
        sendMessage(protocolMessage);
    }
}
