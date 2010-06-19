package com.hr.registor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class RegistClient extends Thread {
    private String host = null;

    private int port = 0;

    public RegistClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String testConnection() {
        try {
            InetAddress address = InetAddress.getByName(this.host);
            Socket clientSocket = new Socket(address, this.port);

            if (clientSocket == null)
                return ":C101";

            BufferedInputStream clientIn = new BufferedInputStream(clientSocket.getInputStream());
            BufferedOutputStream clientOut = new BufferedOutputStream(clientSocket
                    .getOutputStream());

            if (clientOut == null)
                return ":C101";
            if (clientIn == null)
                return ":C101";

            clientOut.write(":T221".getBytes());
            clientOut.flush();

            byte[] buffer = new byte[256];
            clientIn.read(buffer, 0, buffer.length);

            String message = new String(buffer).trim();

            clientOut.close();
            clientIn.close();
            clientSocket.close();
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ":C101";
    }

    public String registConnection(String registMessage) {
        try {
            InetAddress address = InetAddress.getByName(this.host);
            Socket clientSocket = new Socket(address, this.port);

            if (clientSocket == null)
                return ":C101";

            BufferedInputStream clientIn = new BufferedInputStream(clientSocket.getInputStream());
            BufferedOutputStream clientOut = new BufferedOutputStream(clientSocket
                    .getOutputStream());

            if (clientOut == null)
                return ":C101";
            if (clientIn == null)
                return ":C101";
            byte[] buffer = new byte[2048];
            buffer = registMessage.getBytes();

            clientOut.write(buffer);
            clientOut.flush();

            buffer = new byte[1024];
            clientIn.read(buffer, 0, buffer.length);

            String message = new String(buffer).trim();
            clientOut.close();
            clientIn.close();
            clientSocket.close();

            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ":R301";
    }

    public String activeConnection(String activeMessage) {
        InetAddress address = null;
        Socket clientSocket = null;
        BufferedInputStream clientIn = null;
        BufferedOutputStream clientOut = null;
        try {
            address = InetAddress.getByName(this.host);
            clientSocket = new Socket(address, this.port);

            if (clientSocket == null)
                return ":C101";

            clientIn = new BufferedInputStream(clientSocket.getInputStream());
            clientOut = new BufferedOutputStream(clientSocket.getOutputStream());

            if (clientOut == null)
                return ":C101";
            if (clientIn == null)
                return ":C101";

            byte[] buffer = new byte[2048];
            String messageA = ":A821#" + activeMessage;
            buffer = messageA.getBytes();
            clientOut.write(buffer);
            clientOut.flush();
        } catch (Exception e) {
            return ":C101";
        }

        try {
            byte[] buffer = new byte[2048];
            clientIn.read(buffer, 0, buffer.length);
            String message = new String(buffer).trim();

            if ((message == null) || (message.length() < 7)) {
                clientOut.close();
                clientIn.close();
                clientSocket.close();
                return message;
            }
            clientOut.close();
            clientIn.close();
            clientSocket.close();
            return message;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ":A801";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.registor.RegistClient JD-Core Version: 0.5.4
 */