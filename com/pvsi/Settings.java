package com.pvsi;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Settings extends JFrame {
    String ip;
    Socket socket;
    private JLabel errorLabel;
    private JLabel ourIpLabel;
    private JTextField textField1;
    private JButton connectButton;
    private JPanel content;

    public Settings() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(content);
        errorLabel.setVisible(false);
        try {
            ourIpLabel.setText("Ваш ip: "+ InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        connectButton.addActionListener(e -> onClick());
        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) onClick();
            }
        });

        setLocationRelativeTo(null);
        pack();
    }

    public void onClick() {
        new SwingWorker(){
            @Override
            protected Object doInBackground() throws Exception {

                if (textField1.getText().isEmpty()) return null;
                ip = textField1.getText();
                try {
                    socket = new Socket();
                    socket.connect(new InetSocketAddress(ip,Main.port),2000);
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    writer.println("hi");
                    JFrame frame = new JFrame();
                    frame.add(new JLabel("waiting..."));
                    frame.setLocationRelativeTo(null);
                    frame.pack();
                    new Thread(()-> {
                        frame.setVisible(true);
                    }).start();
                    String a = reader.readLine();
                    frame.dispose();
                    if (!a.equals("hey")) {
                        if(a.equals("no")) {
                            error("Собесеник отклонил запрос");
                        }
                        else error("не удалось подключиться");
                        return null;
                    }
                    Main.tempSocket = socket;
                    Main.continueConnection();
                } catch (SocketTimeoutException e){
                    error("не удалось подключиться");
                } catch (IOException e1) {
                    error("неверно введены данные");
                }
                return null;
            }
        }.execute();
    }

    private void error(String e) {
        errorLabel.setText(e);
        errorLabel.setVisible(true);
    }
}
