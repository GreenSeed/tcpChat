package com.pvsi;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpClient {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public TcpClient(Socket socket) {
        this.socket = socket;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        } catch (IOException e) {
            System.out.println("не удалось подключиться");
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void openStream(ChatForm form) {
        new Thread(() -> {
            try {
                String s;
                while ((s = reader.readLine()) != null) {
                    form.addRecivedMessage(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendByOpenedStream(String msg) {

        assert (writer != null);
        writer.println(msg);
    }

    public void closeStream() throws IOException {
        writer.close();
        reader.close();
    }

    public String read() throws IOException {
        return reader.readLine();
    }


}
