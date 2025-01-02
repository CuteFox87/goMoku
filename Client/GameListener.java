package Client;

import javax.swing.*;
import javax.sound.sampled.*;
import java.io.*;
import java.awt.event.*;

public class GameListener extends MouseAdapter implements ActionListener, connectHandle.MessageListener {
    
    public GameUI gameUI;
    public JTextField nameIn;
    int x, y;
    int gameStatus = 0;

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        playding(); 
        
        switch (action) {
            case "login" -> handleLogin();
            case "Logout" -> handleLogout();
            case "Local PVP" -> startLocalPVP();
            case "Local PVE" -> startLocalPVE();
            case "Online PVP" -> startOnlinePVP();
            case "Undo" -> gameUI.game.undoGame();
            case "Clear" -> gameUI.game.resetGame();
            case "Save" -> gameUI.game.saveGame();
            case "Quit" -> handleQuit();
        }
    }

    private void playding() {
        try {
            File sound = new File("src\\sound\\bellding-254774.wav");
            AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();

            Thread.sleep(clip.getMicrosecondLength() / 100000);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleLogin() {
        String name = nameIn.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter your name");
            return;
        }
        gameUI.username = name;
        gameUI.initGameUI();
        gameUI.showGameUI();
        gameUI.hideLoginUI();
    }

    private void handleLogout() {
        gameUI.username = null;
        gameUI.showLoginUI();
        gameUI.hideGameUI();
    }

    private connectHandle networkHandler;

    private void startOnlinePVP() {
        gameUI.game.currentGameMode = GameMode.ONLINE_PVP;
        gameUI.game.resetGame();

        hideGameModeButtons();
        showGameControlButtons();

        networkHandler = new connectHandle("localhost", 8888);
        networkHandler.setMessageListener(this);
        gameUI.game.setNetworkHandler(networkHandler);
    }

    @Override
    public void onTurnUpdate(boolean isPlayerTurn) {
        if (isPlayerTurn) {
            // JOptionPane.showMessageDialog(null, "Your turn!");
        } else {
            // JOptionPane.showMessageDialog(null, "Waiting for opponent...");
        }
    }

    @Override
    public void onMoveReceived(String move) {
        gameUI.game.processOpponentMove(move);
    }

    private void startLocalPVP() {
        if (gameStatus == 1) {
            return;
        }
        gameUI.game.currentGameMode = GameMode.LOCAL_PVP;
        gameUI.game.resetGame();
        hideGameModeButtons();
        showGameControlButtons();
        gameUI.Online = 0;
        gameUI.AImode = 0;
        gameUI.GameStart();
        gameStatus = 1;
    }

    private void startLocalPVE() {
        if (gameStatus == 1) {
            return;
        }
        gameUI.game.currentGameMode = GameMode.LOCAL_PVE;
        gameUI.game.resetGame();
        hideGameModeButtons();
        showGameControlButtons();
        gameUI.Online = 0;
        gameUI.AImode = 1;
        gameUI.GameStart();
        gameStatus = 1;
    }

    private void handleQuit() {
        //clientGameHandle.GameEnd();

        gameStatus = 0;
        showGameModeButtons();
        hideGameControlButtons();
        if (gameUI.Online == 1) {
            gameUI.connect.close();
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
