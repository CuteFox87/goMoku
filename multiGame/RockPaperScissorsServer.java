import java.io.*;
import java.net.*;
import java.util.Random;

public class RockPaperScissorsServer {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        System.out.println("Rock-Paper-Scissors Server is running...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    System.out.println("Client connected.");
                    
                    String playerChoice;
                    while ((playerChoice = in.readLine()) != null) {
                        // Handle "Exit" command
                        if (playerChoice.equalsIgnoreCase("Exit")) {
                            out.println("Goodbye! Thanks for playing!");
                            System.out.println("Client disconnected.");
                            break;
                        }

                        // Validate input
                        if (!isValidChoice(playerChoice)) {
                            out.println("Invalid choice. Please enter Rock, Paper, or Scissors:");
                            System.out.println("Invalid input from client: " + playerChoice);
                            continue;
                        }

                        // Generate AI's choice
                        String[] choices = {"Rock", "Paper", "Scissors"};
                        String aiChoice = choices[new Random().nextInt(choices.length)];
                        System.out.println("Player chose: " + playerChoice);
                        System.out.println("AI chose: " + aiChoice);

                        // Determine and send the result
                        String result = determineWinner(playerChoice, aiChoice);
                        out.println("AI chose: " + aiChoice + " | " + result);
                        out.println(result);
                    }
                } catch (IOException e) {
                    System.err.println("Error handling client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidChoice(String choice) {
        return choice.equalsIgnoreCase("Rock") ||
               choice.equalsIgnoreCase("Paper") ||
               choice.equalsIgnoreCase("Scissors");
    }

    private static String determineWinner(String player, String ai) {
        if (player.equalsIgnoreCase(ai)) {
            return "It's a tie!";
        }
        if ((player.equalsIgnoreCase("Rock") && ai.equalsIgnoreCase("Scissors")) ||
            (player.equalsIgnoreCase("Paper") && ai.equalsIgnoreCase("Rock")) ||
            (player.equalsIgnoreCase("Scissors") && ai.equalsIgnoreCase("Paper"))) {
            return "You win!";
        }
        return "You lose!";
    }
}
