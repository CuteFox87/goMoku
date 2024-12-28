package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameListener extends MouseAdapter implements ActionListener {
    
    public GameUI gameUI;
    public JTextField nameIn;
    public ClientGameHandle clientGameHandle;
    int x, y;
    int gameStatus = 0;

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "login" -> handleLogin();
            case "Logout" -> handleLogout();
            case "Local PVP" -> startLocalPVP();
            case "Local PVE" -> startLocalPVE();
            case "Online PVP" -> startOnlinePVP();
            case "Undo" -> handleUndo();
            case "Quit" -> handleQuit();
        }
    }

    private void handleLogin() {
        String name = nameIn.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter your name");
            return;
        }
        clientGameHandle.username = name;
        gameUI.initGameUI();
        gameUI.showGameUI();
        gameUI.hideLoginUI();
    }

    private void handleLogout() {
        clientGameHandle.username = null;
        gameUI.showLoginUI();
        gameUI.hideGameUI();
    }

    private void startLocalPVP() {
        if (gameStatus == 1) {
            return;
        }
        gameUI.gameBoard.currentGameMode = GameMode.LOCAL_PVP;
        gameUI.gameBoard.clearButtons();
        hideGameModeButtons();
        showGameControlButtons();
        clientGameHandle.Online = 0;
        clientGameHandle.AImode = 0;
        clientGameHandle.GameStart();
        gameStatus = 1;
    }

    private void startLocalPVE() {
        if (gameStatus == 1) {
            return;
        }
        gameUI.gameBoard.currentGameMode = GameMode.LOCAL_PVE;
        gameUI.gameBoard.clearButtons();
        hideGameModeButtons();
        showGameControlButtons();
        clientGameHandle.Online = 0;
        clientGameHandle.AImode = 1;
        clientGameHandle.GameStart();
        gameStatus = 1;
    }

    private void startOnlinePVP() {
        if (gameStatus == 1) {
            return;
        }
        gameUI.gameBoard.currentGameMode = GameMode.ONLINE_PVP;
        gameUI.gameBoard.clearButtons();
        hideGameModeButtons();
        showGameControlButtons();
        clientGameHandle.Online = 1;
        clientGameHandle.AImode = 0;
        clientGameHandle.GameStart();
        gameStatus = 1;
    }

    private void handleUndo() {
        // Undo last move
    }

    private void handleQuit() {
        //clientGameHandle.GameEnd();

        gameStatus = 0;
        showGameModeButtons();
        hideGameControlButtons();
        if (clientGameHandle.Online == 1) {
            clientGameHandle.connect.close();
        }
    }

    private void hideGameModeButtons() {
        for (JButton button : gameUI.gameModeButtons) {
            button.setVisible(false);
        }
    }


    private void showGameModeButtons() {
        for (JButton button : gameUI.gameModeButtons) {
            button.setVisible(true);
        }
    }


    private void hideGameControlButtons() {
        for (JButton button : gameUI.gameControlButtons) {
            button.setVisible(false);
        }
    }

    private void showGameControlButtons() {
        for (JButton button : gameUI.gameControlButtons) {
            button.setVisible(true);
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e){
        System.out.println(x + " " + y);

    }
}
