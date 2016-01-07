package com.pvsi;

public class Message {
    private String nick;
    private String message;

    public Message(String nick, String message) {
        this.nick = nick;
        this.message = message;
    }

    public Message(String s) {

    }

    @Override
    public String toString() {
        return nick + ":  " + message;
    }
}
