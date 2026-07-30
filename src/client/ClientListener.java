package client;

public interface ClientListener {
    void chatMessage(String user, String message);

    void connectionLost();
}
