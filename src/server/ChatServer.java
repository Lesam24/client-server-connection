package server;

import network.SocketServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatServer extends SocketServer {
    private Set<ClientHandler> clients;

    public ChatServer(int port) throws IOException {
        super(port);
        this.clients = new HashSet<>();
    }

    @Override
    public int getPort() {
        return super.getPort();
    }

    @Override
    public void acceptConnections() throws IOException {
        super.acceptConnections();
    }

    @Override
    public synchronized void closeSocket() {
        super.closeSocket();
    }

    @Override
    protected void handleConnection(Socket socket) {
        try {
            ServerConnection connection = new ServerConnection(socket);

            ClientHandler handler = new ClientHandler(connection, this);

            connection.setClientHandler(handler);

            connection.startConnection();
        } catch (IOException e) {
            System.err.println("Error with connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public synchronized void addClient(ClientHandler client) {
        clients.add(client);
        System.out.println("Client added: " + client.getUsername());
    }

    public synchronized void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("Client removed: " + client.getUsername());
    }

    public synchronized void handleChatMessage(ClientHandler sender, String message) {
        System.out.println("Message from " + sender.getUsername() + ": " + message);

        for (ClientHandler client : clients) {
            client.sendChatMessage(sender.getUsername(), message);
        }
    }

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Enter port number (0 for random port): ");
            int port = Integer.parseInt(reader.readLine());

            ChatServer server = new ChatServer(port);
            System.out.println("Server started on port: " + server.getPort());

            server.acceptConnections();
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Invalid port number");
        }
    }
}
