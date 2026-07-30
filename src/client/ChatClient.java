package client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class ChatClient {
    private ClientConnection connection;
    private List<ClientListener> listeners;

    public ChatClient(InetAddress address, int port) throws IOException {
        this.connection = new ClientConnection(address, port);
        this.connection.setChatClient(this);
        this.listeners = new ArrayList<>();
        LogListener listener1 = new LogListener();
        addListener(listener1);
    }

    public void start() {
        connection.startConnection();
    }

    public void close() {
        connection.closeConnection();
    }

    public void sendUsername(String username) {
        connection.sendUsername(username);
    }

    public void sendChatMessage(String message) {
        connection.sendChatMessage(message);
    }

    public void addListener(ClientListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ClientListener listener) {
        listeners.remove(listener);
    }

    public void receiveChatMessage(String username, String message) {
        for (ClientListener listener : listeners) {
            listener.chatMessage(username, message);
        }
    }

    public void handleDisconnect() {
        for (ClientListener listener : listeners) {
            listener.connectionLost();
        }
    }
}
