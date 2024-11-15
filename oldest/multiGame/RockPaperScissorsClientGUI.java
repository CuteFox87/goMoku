import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class RockPaperScissorsClientGUI extends RockPaperScissorsClient {
    private JFrame frame;
    private JLabel statusLabel;

    public RockPaperScissorsClientGUI() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Rock-Paper-Scissors");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        statusLabel = new JLabel("Welcome! Choose Rock, Paper, or Scissors to play.", SwingConstants.CENTER);
        frame.add(statusLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));

        JButton rockButton = new JButton("Rock");
        JButton paperButton = new JButton("Paper");
        JButton scissorsButton = new JButton("Scissors");

        buttonPanel.add(rockButton);
        buttonPanel.add(paperButton);
        buttonPanel.add(scissorsButton);

        frame.add(buttonPanel, BorderLayout.CENTER);

        // Add action listeners to buttons
        rockButton.addActionListener(e -> playGameWithUI("Rock"));
        paperButton.addActionListener(e -> playGameWithUI("Paper"));
        scissorsButton.addActionListener(e -> playGameWithUI("Scissors"));

        frame.setVisible(true);
    }

    private void playGameWithUI(String playerChoice) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Send player's choice to server
            out.println(playerChoice);

            // Update status and wait for server's response
            statusLabel.setText("You chose: " + playerChoice + ". Waiting for server response...");
            String serverResponse = in.readLine();

            // Show result in a pop-up
            JOptionPane.showMessageDialog(frame, serverResponse, "Game Result", JOptionPane.INFORMATION_MESSAGE);

            // Update status label to prompt for the next game
            statusLabel.setText("Choose Rock, Paper, or Scissors to play again.");
        } catch (IOException e) {
            // Show error message in a pop-up
            JOptionPane.showMessageDialog(frame, "Could not connect to server. Please try again later.",
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RockPaperScissorsClientGUI::new);
    }
}
