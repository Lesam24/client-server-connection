package client;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogListener implements ClientListener{
    private BufferedWriter out;

    public LogListener() throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter("logs.txt"));
        this.out = out;
    }

    @Override
    public void chatMessage(String name, String message) {
        try {
            String line = name + " said: " + message;
            out.write(line, 0, line.length());
            out.newLine();
        } catch (IOException e) {

        }
    }

    @Override
    public void connectionLost() {
        try {
            out.flush();
        } catch (IOException e) {

        }
    }

}
