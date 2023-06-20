package com.gradingsystem.utils;

import com.gradingsystem.controllers.LoginController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerConnection {
    private String serverAddress;
    private int serverPort;
    private Socket socket;
    private OutputStream outputStream;
    private BufferedReader reader;
    private static final Logger logger = LogManager.getLogger(ServerConnection.class);

    public ServerConnection(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void connect() {
        try {
            socket = new Socket(serverAddress, serverPort);
            outputStream = socket.getOutputStream();
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        } catch (Exception e) {
            logger.debug("Cannot connect to server");
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
            logger.debug("Cannot disconnect from server");
            e.printStackTrace();
        }
    }

    public String sendRequest(String request) {
        String response = null;
        try {
            outputStream.write(request.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            response = reader.readLine();
            System.out.println(response);
        } catch (Exception e) {
            logger.debug("Cannot send request");
            e.printStackTrace();
        }
        return response;
    }
    
}
