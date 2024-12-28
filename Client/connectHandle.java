package Client;

import java.net.*;
import java.io.*;

public class connectHandle {
    public static Socket clientSocket;
    String latestMES = null;

    connectHandle(String host, int port){
        System.out.println("Connecting to server...");
        try {
            clientSocket = new Socket(host, port);
            System.out.println("Connected to server.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                    latestMES = response;
                }
            } catch (IOException ex) {
                System.out.println("Error receiving data from server: " + ex.getMessage());
            }
        
        }).start();

    }

    public void close() {
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException ex) {
            System.err.println("Error closing connection: " + ex.getMessage());
        }
    }


    public static void main(String[] args) {
        new connectHandle("localhost", 8888);
    }
}
