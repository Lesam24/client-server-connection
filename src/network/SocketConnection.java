package network;

import java.net.InetAddress;
import java.net.Socket;
import java.io.*;

public abstract class SocketConnection {
    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    private boolean started = false;

    protected SocketConnection(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    protected SocketConnection(InetAddress host, int port) throws IOException {
        this(new Socket(host, port));
    }

    private void receiveMessages() {
        handleStart();
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                handleMessage(inputLine);
            }
        } catch (IOException e) {
            // Close the connection.
        } finally {
            closeConnection();
            handleDisconnect();
        }
    }

    protected boolean sendMessage(String message) {
        try {
            out.write(message);
            out.newLine();
            out.flush();
            return true;
        } catch (IOException e) {
            closeConnection();
            return false;
        }
    }

    public void startConnection() {
        if (started) {
            throw new IllegalStateException("Cannot start a SocketConnection more than once");
        }
        started = true;
        Thread thread = new Thread(this::receiveMessages);
        thread.start();
    }

    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException ignored) {
            // do nothing.
        }
    }

    protected void handleStart() {};

    protected abstract void handleMessage(String message);

    protected abstract void handleDisconnect();
}
