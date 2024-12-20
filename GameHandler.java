import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*; 

public class GameHandler implements Runnable {
    private Socket player1;
    private Socket player2;

    public GameHandler(Socket player1, Socket player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void run() {
        try {
            BufferedReader player1In = new BufferedReader(new InputStreamReader(player1.getInputStream()));
            PrintWriter player1Out = new PrintWriter(player1.getOutputStream(), true);
            BufferedReader player2In = new BufferedReader(new InputStreamReader(player2.getInputStream()));
            PrintWriter player2Out = new PrintWriter(player2.getOutputStream(), true);

            System.out.println("Game started.");
            
            int turn = 1;
            while (true) {

                if (turn == 1) {
                    player1Out.println("Player 1's Turn.");
                    String message = player1In.readLine();
                    if (message == null) {
                        break;
                    }
                    System.out.println("Player 1: " + message);
                    player2Out.println(message);
                    turn = 2;
                } else {
                    player2Out.println("Player 2's Turn.");
                    String message = player2In.readLine();
                    if (message == null) {
                        break;
                    }
                    System.out.println("Player 2: " + message);
                    player1Out.println(message);
                    turn = 1;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                player1.close();
                player2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}