import java.io.*;
import java.net.*;
import java.util.*;

public class ChessServer {
    private static ServerSocket serverSocket;
    private static final List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());
    private static Game game;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(8888);
            game = new Game();
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
            return;
        }

        System.out.println("Server started. Waiting for players...");

        while (true) {
            try {
                if (clients.size() < 2) {
                    Socket playerSocket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(playerSocket, clients, game);
                    clients.add(clientHandler);

                    if (clients.size() == 1) {
                        game.setCurrentPlayer(clientHandler);
                    }

                    System.out.println("Player " + clients.size() + " connected.");
                    new Thread(clientHandler).start();
                } else {
                    Socket tempSocket = serverSocket.accept();
                    PrintWriter writer = new PrintWriter(tempSocket.getOutputStream(), true);
                    writer.println("Server is full. Please try again later.");
                    tempSocket.close();
                }
            } catch (IOException e) {
                System.err.println("Error accepting connection: " + e.getMessage());
            }
        }
    }
}

class Game {
    private ClientHandler currentPlayer;

    public synchronized void setCurrentPlayer(ClientHandler client) {
        currentPlayer = client;
        if (currentPlayer != null) {
            currentPlayer.sendMessage("TURN:START");
        }
    }

    public synchronized boolean isPlayerTurn(ClientHandler client) {
        return currentPlayer == client;
    }

    public synchronized void switchTurn() {
        currentPlayer = currentPlayer == null ? null : currentPlayer.getOpponent();
        if (currentPlayer != null) {
            currentPlayer.sendMessage("TURN:START");
        }
    }

    public synchronized void broadcastMove(ClientHandler sender, String move) {
        if (currentPlayer != sender) {
            sender.sendMessage("TURN:END"); // Notify it's not their turn
            return;
        }

        for (ClientHandler client : sender.getClients()) {
            if (client != sender) {
                client.sendMessage("MOVE:" + move);
            }
        }

        // Switch turn after broadcasting the move
        switchTurn();
    }
}

class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final List<ClientHandler> clients;
    private final Game game;

    public ClientHandler(Socket socket, List<ClientHandler> clients, Game game) {
        this.clientSocket = socket;
        this.clients = clients;
        this.game = game;
    }

    public List<ClientHandler> getClients() {
        return clients;
    }

    public ClientHandler getOpponent() {
        return clients.stream().filter(client -> client != this).findFirst().orElse(null);
    }

    public void sendMessage(String message) {
        try {
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            writer.println(message);
        } catch (IOException e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Received from client: " + message);

                if (message.startsWith("MOVE:")) {
                    if (game.isPlayerTurn(this)) {
                        String move = message.substring(5);
                        System.out.println("Processing move: " + move);
                        game.broadcastMove(this, move);
                    } else {
                        sendMessage("TURN:END");
                        System.out.println("Invalid move - not player's turn.");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            clients.remove(this);
            System.out.println("Client disconnected.");
            if (clients.size() < 2) {
                game.setCurrentPlayer(null);
            }
        }
    }
}
