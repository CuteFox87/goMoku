import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*; // Use a JSON library like org.json

public class jsonServer {
    private static final int PORT = 12345;
    private static ServerSocket serverSocket;
    private static Socket player1Socket;
    private static Socket player2Socket;
    private static PrintWriter logWriter;

    private static String[][] chessboard = new String[8][8]; // 8x8 chessboard
    private static int step = 0;
    private static boolean gameOver = false;
    private static String turn = "player1"; // Player1 starts
    private static String winner = null;
    private static boolean askDraw = false;
    private static boolean player1AcceptDraw = false;
    private static boolean player2AcceptDraw = false;

    private static String player1Name = "Player1";
    private static String player2Name = "Player2";
    private static long startTime;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. Waiting for players...");
            
            // Accept player 1 in a separate thread
            Thread player1Thread = new Thread(() -> {
                try {
                    player1Socket = serverSocket.accept();
                    System.out.println("Player 1 connected.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            player1Thread.start();
            
            // Accept player 2 in a separate thread
            Thread player2Thread = new Thread(() -> {
                try {
                    player2Socket = serverSocket.accept();
                    System.out.println("Player 2 connected.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            player2Thread.start();
            
            // Wait for both players to connect
            player1Thread.join();
            player2Thread.join();

            // Start the game
            startTime = System.currentTimeMillis();
            logWriter = new PrintWriter(new FileWriter("game.log"), true);
            logWriter.println("Game started at: " + new Date(startTime));
            
            handleGame();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    private static void handleGame() throws IOException {
        BufferedReader input1 = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
        PrintWriter output1 = new PrintWriter(player1Socket.getOutputStream(), true);
        BufferedReader input2 = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));
        PrintWriter output2 = new PrintWriter(player2Socket.getOutputStream(), true);

        while (!gameOver) {
            // Notify both players of the current status
            sendStatus(output1);
            sendStatus(output2);
            
            // Handle turn
            if (turn.equals("player1")) {
                handlePlayerTurn(input1, output1, player1Name);
                turn = "player2";
            } else {
                handlePlayerTurn(input2, output2, player2Name);
                turn = "player1";
            }
            
            checkGameOver();
        }
        
        // Send final result
        sendFinalResult(output1);
        sendFinalResult(output2);
        logEndGame();
    }

    private static void handlePlayerTurn(BufferedReader input, PrintWriter output, String playerName) throws IOException {
        output.println("Your turn");
        String jsonString = input.readLine();
        JSONObject request = new JSONObject(jsonString);

        step++;
        logWriter.println(step + ": " + playerName + " made a move");

        if (request.has("askDraw") && request.getBoolean("askDraw")) {
            askDraw = true;
            logWriter.println(step + ": " + playerName + " asked for a draw.");
            return;
        }
        
        // Update chessboard based on received changes
        int[] change = {request.getJSONArray("change").getInt(0), request.getJSONArray("change").getInt(1)};
        chessboard[change[0]][change[1]] = playerName;
    }

    private static void sendStatus(PrintWriter output) {
        JSONObject response = new JSONObject();
        response.put("step", step);
        response.put("turn", turn);
        response.put("winner", winner);
        response.put("askDraw", askDraw);
        response.put("chessboard", chessboard);

        output.println(response.toString());
    }

    private static void checkGameOver() {
        if (askDraw && player1AcceptDraw && player2AcceptDraw) {
            gameOver = true;
            winner = "draw";
        }
        // Additional logic to check for winners can be added here
    }

    private static void sendFinalResult(PrintWriter output) {
        JSONObject result = new JSONObject();
        result.put("winner", winner);
        result.put("step", step);
        output.println(result.toString());
    }

    private static void logEndGame() {
        long endTime = System.currentTimeMillis();
        logWriter.println("Game ended at: " + new Date(endTime));
        logWriter.println("Total steps: " + step);
        logWriter.println("Winner: " + (winner == null ? "None" : winner));
    }

    private static void closeConnections() {
        try {
            if (player1Socket != null) player1Socket.close();
            if (player2Socket != null) player2Socket.close();
            if (logWriter != null) logWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
