package com.pvsi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatForm extends JFrame {
    TcpClient client;
    private JPanel panel1;
    private JTextField textField;
    private JButton sendButton;
    private JList<java.lang.String> listMessages;
    private JScrollPane scrollPane;
    private DefaultListModel<String> model = new DefaultListModel<>();

    public ChatForm() {
        init();
    }

    public ChatForm(TcpClient client) {
        this.client = client;
        init();
    }

    private void init() {
        setName("Chat");
        setContentPane(panel1);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(200, 300));
        sendButton.addActionListener(e -> onSend());
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) onSend();
            }
        });
        listMessages.setModel(model);
        pack();
        client.openStream(this);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new SwingWorker(){
                    @Override
                    protected Object doInBackground() throws Exception {
                        client.closeStream();
                        Main.init();
                        return null;
                    }
                }.execute();
            }
        });
        setVisible(true);
    }

    private void onSend() {
        String text = textField.getText();
        if (!text.replaceAll(" ", "").isEmpty()) {
            client.sendByOpenedStream(text);
            addSendedMessage(text);
            textField.setText("");
        }
    }

    public void addRecivedMessage(String message) {
        model.addElement("Собеседник:    "+message);
        JScrollBar bar = scrollPane.getVerticalScrollBar();
        bar.setValue(bar.getMaximum());
    }

    public void addSendedMessage(String message) {
        model.addElement("Вы:   "+message);
        JScrollBar bar = scrollPane.getVerticalScrollBar();
        bar.setValue(bar.getMaximum());
    }
}
