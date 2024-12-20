import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*; 

public class servertest {
	private static ServerSocket serverSocket;
    

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(12345);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Server started. Waiting for players...");

        while (true) {
            try {
                Socket player1 = serverSocket.accept();
                System.out.println("Player 1 connected.");
                Socket player2 = serverSocket.accept();
                System.out.println("Player 2 connected.");

                new Thread(new GameHandler(player1, player2)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



