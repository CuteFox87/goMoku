package Client;

import java.io.*;
import java.net.*;

public class connectHandle {
    public interface MessageListener {
        void onTurnUpdate(boolean isPlayerTurn);
        void onMoveReceived(String move);
    }

    private Socket clientSocket;
    private PrintWriter out;
    private MessageListener listener;
    private boolean isPlayerTurn;

    public connectHandle(String host, int port) {
        try {
            clientSocket = new Socket(host, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            new Thread(() -> {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    String response;
                    while ((response = in.readLine()) != null) {
                        if (response.startsWith("TURN:")) {
                            isPlayerTurn = response.equals("TURN:START");
                            if (listener != null) {
                                listener.onTurnUpdate(isPlayerTurn);
                            }
            
                            // Unlock move if it's the player's turn again
                            if (isPlayerTurn) {
                                unlockMove();
                            }
                        } else if (response.startsWith("MOVE:")) {
                            if (listener != null) {
                                listener.onMoveReceived(response.substring(5));
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean moveLocked = false;

    public boolean isMoveLocked() {
        return moveLocked;
    }

    public void lockMove() {
        moveLocked = true;
    }

    public void unlockMove() {
        moveLocked = false;
    }


    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public void sendMove(int x, int y) {
        if (out != null) {
            out.println("MOVE:" + x + "," + y);
        }
    }

    public void setMessageListener(MessageListener listener) {
        this.listener = listener;
    }

    public void close() {
        try {
            if (clientSocket != null) {
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
