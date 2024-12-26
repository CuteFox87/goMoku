import java.io.*;
import java.net.*;
import java.util.*;
//import org.json.*; 

public class ChessServer {
	private static ServerSocket serverSocket;
    private static final List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());
    private static int player_number = 0;
    

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(8888);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Server started. Waiting for players...");

        while (true) {

            if (clients.size() > 1) {
                try {
                    Socket player = serverSocket.accept();
                    PrintWriter writer = new PrintWriter(player.getOutputStream(), true);
                    writer.println("Server is full. Please try again later.");
                    player.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                continue;
            }

            try {
                player_number = clients.size();
                if ( player_number == 0 ) {
                    Socket player1 = serverSocket.accept();
                    ClientHandler clientHandler1 = new ClientHandler(player1, clients);
                    clients.add(clientHandler1);
                    int hash1 = clientHandler1.hashCode();
                    System.out.println("Player 1 connected. (" + hash1 + ")");
                    new Thread(clientHandler1).start();
                    player_number = 1;
                }
                else {
                    Socket player2 = serverSocket.accept();
                    ClientHandler clientHandler2 = new ClientHandler(player2, clients);
                    clients.add(clientHandler2);
                    int hash2 = clientHandler2.hashCode();
                    System.out.println("Player 2 connected. (" + hash2 + ")");
                    new Thread(clientHandler2).start();
                    player_number = 0;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private final List<ClientHandler> clients;
    
    public ClientHandler(Socket socket, List<ClientHandler> clients) {
        this.clientSocket = socket;
        this.clients = clients;
    }

    @Override
    public void run() {
        try (
            InputStream in = clientSocket.getInputStream();
            // OutputStream out = clientSocket.getOutputStream();
        ) {
            int data;
            StringBuilder messageBuffer = new StringBuilder();

            while ((data = in.read()) != -1) {
                if (data == '\n') { 
                    String message = messageBuffer.toString();
                    messageBuffer.setLength(0);
                    System.out.println(this.hashCode() + ": " + message);

                    synchronized (clients) {
                        for (ClientHandler client : clients) {
                            if (client == this) {
                                continue;
                            }
                            System.out.print("Send to " + client.hashCode() + ": " + message + "\n");
                            PrintWriter writer = new PrintWriter(client.clientSocket.getOutputStream(), true);
                            writer.println(message);
                        }
                    }
                } else {
                    messageBuffer.append((char) data);
                }
            }

            System.out.println("Client disconnected.");
            clients.remove(this);
            clients.forEach(client -> System.out.println(client.hashCode() + " is still connected."));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class GameHandler {
    
}
