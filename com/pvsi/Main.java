package com.pvsi;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    static int port = 6666;
    static Settings settings;
    static ChatForm chat;
    static Socket tempSocket;
    static ServerSocket serverSocket;

    public static void main(String[] args) {
            System.setProperty("file.encoding","utf-8");
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                    String q = reader.readLine();
                    boolean accept = askForAccept(socket.getInetAddress().getHostAddress());
                    if (q.equals("hi")) {
                        if(accept){
                        writer.println("hey");
                        settings.dispose();
                        new ChatForm(new TcpClient(socket));
                        }
                        else writer.println("no");
                    }
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }).start();
        init();
//        chat.setVisible(true);

//        new Thread(() -> chat.setVisible(true));
    }
public static void init(){
    settings = new Settings();
    settings.setVisible(true);

}
    public static void continueConnection() {
        settings.dispose();
        chat = new ChatForm(new TcpClient(tempSocket));
//        chat.setVisible(true);
    }

    private static boolean askForAccept(String ip) {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(new JFrame(), "пользователь " + ip + " запрашивает подключение", "Чат", dialogButton);
        return dialogResult == 0;

    }
}
