package Server;

public class ClientHandler {
    private ServerConnection connection;
    private ChatServer server;
    private String username;


    public ClientHandler(ServerConnection connection, ChatServer server) {
        this.connection = connection;
        this.server = server;
        this.username = null;
    }

    public String getUsername() {
        return username;
    }

    public void receiveUsername(String username) {
        if (this.username == null) {
            this.username = username;
            server.addClient(this);
        }
        // Ignore subsequent USER messages
    }

    public void receiveChatMessage(String message) {
        if (username != null) {
            server.handleChatMessage(this, message);
        }
        // Ignore messages from clients without a username
    }

    public void handleDisconnect() {
        if (username != null) {
            server.removeClient(this);
        }
    }
    public void sendChatMessage(String username, String message) {
        connection.sendChatMessage(username, message);
    }
}
