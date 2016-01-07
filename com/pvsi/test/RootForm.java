package com.pvsi.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class RootForm extends JFrame {
    JList<String> jList;
    DefaultListModel<String> model;
    JPanel contentPanel;
    JTextField textField;
    JButton buttonSend;
    List<String> messages;

    public RootForm() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        model = new DefaultListModel<>();
        jList = new JList<>();
        jList.setModel(model);
        contentPanel = new JPanel();
        setContentPane(contentPanel);
        buttonSend = new JButton("send");
        textField = new JTextField();
        messages = new ArrayList<>();
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        contentPanel.setLayout(new BorderLayout());
        add(jList);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(textField);
        panel.add(buttonSend);
        add(panel);
        setMinimumSize(new Dimension(200,300));
        setSize(new Dimension(200, 300));
        int systemWidth =(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        textField.setMaximumSize( new Dimension(systemWidth, 30));
        pack();

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!textField.getText().replaceAll(" ", "").isEmpty()) {
                        addSendedMessage(textField.getText());
                        textField.setText("");
                    }
                }
            }
        });

        buttonSend.addActionListener(e -> {
            if (!textField.getText().replaceAll(" ", "").isEmpty()) {
                addSendedMessage(textField.getText());
                textField.setText("");
            }
        });
    }

    public void addRecivedMessage(String message) {
        model.addElement(message);
    }

    public void addSendedMessage(String message) {
        model.addElement(message);
    }
}
