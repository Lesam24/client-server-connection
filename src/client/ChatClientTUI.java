package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ChatClientTUI implements ClientListener {
    private ChatClient client;
    private BufferedReader reader;
    private boolean running;

    public ChatClientTUI() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.running = true;
    }

    public void runTUI() {
        try {
            System.out.print("Enter server address (hostname or IP): ");
            String serverAddress = reader.readLine();

            System.out.print("Enter port number: ");
            int port = Integer.parseInt(reader.readLine());

            InetAddress address = InetAddress.getByName(serverAddress);
            client = new ChatClient(address, port);
            client.addListener(this);
            client.start();
            System.out.println("Connected to server");

            System.out.print("Enter your username: ");
            String username = reader.readLine();
            client.sendUsername(username);

            System.out.println("You can now start chatting. Type 'quit' to exit.");

            while (running) {
                String message = reader.readLine();

                if (message == null) {
                    break;
                }

                if (message.equalsIgnoreCase("quit")) {
                    client.close();
                    break;
                }
                client.sendChatMessage(message);
            }
            System.out.println("bye");
        } catch (UnknownHostException e) {
            System.err.println("Host error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid port number");
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }

    @Override
    public void chatMessage(String username, String message) {
        System.out.println(username + ": " + message);
    }

    @Override
    public void connectionLost() {
        System.out.println("\nConnection to server lost");
        running = false;
    }

    public static void main(String[] args) {
        ChatClientTUI tui = new ChatClientTUI();
        tui.runTUI();
    }
}