import java.io.*;
import java.net.*;
import java.util.Scanner;

public class RockPaperScissorsClient {
    protected static final String SERVER_ADDRESS = "localhost";
    protected static final int PORT = 12345;

    public void playGame() {
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to Rock-Paper-Scissors Server.");

            // Receive welcome message
            System.out.println(in.readLine());

            // Get player's choice
            System.out.print("Your choice (Rock, Paper, Scissors): ");
            String playerChoice = scanner.nextLine().trim();

            // Send player's choice to server
            out.println(playerChoice);

            // Receive and print the result from server
            System.out.println(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error: Could not connect to server.");

            System.out.println("Wait 5 sec to reconnect...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            playGame();
        }
    }

    public static void main(String[] args) {
        RockPaperScissorsClient client = new RockPaperScissorsClient();
        while (true) {
            client.playGame();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Do you want to play again? (y/n): ");
            String playAgain = scanner.nextLine().trim();
            if (!playAgain.equalsIgnoreCase("y")) {
                break;
            }
        }
    }
}
