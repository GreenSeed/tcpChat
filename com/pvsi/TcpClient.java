package com.pvsi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpClient {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public TcpClient(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }
    public void openStream(ChatForm form){
        new Thread(()->{
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String s;
                while((s=reader.readLine())!=null){
                    form.addRecivedMessage(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                writer = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public void sendByOpenedStream(String msg){
        if(writer!=null) writer.println(msg);
    }
    public void closeStream(){
        writer.close();
    }
    public void send(String msg) throws IOException {
        writer = new PrintWriter(socket.getOutputStream(), true);
        writer.println(msg);
        writer.close();
    }

    public String read() throws IOException {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String a =reader.readLine();
        reader.close();
        return a;
    }


}
