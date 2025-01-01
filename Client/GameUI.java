package Client;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class GameUI {
    JFrame loginJF;
    JPanel loginBar;
    JPanel loginPic;

    JFrame gameJF;
    JPanel gameBar;
    goMokuIconPanel game;
    GameListener gameListener;
    
    public connectHandle connect;
    public int Online;
    public int AImode;
    public String username;
    public static JTextArea textArea = new JTextArea();
    
    ArrayList<JButton> gameModeButtons = new ArrayList<>();
    ArrayList<JButton> gameControlButtons = new ArrayList<>();

    public GameUI() {
        Online = 0;
        AImode = 0;

        try {
            File audioFile = new File(".\\src\\music\\Lucid Dreamer.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            clip.loop(Clip.LOOP_CONTINUOUSLY);

            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initLoginUI() {

        loginJF = new JFrame();
        loginBar = new JPanel();
        loginPic = new JPanel();

        loginJF.setTitle("Go Moku Login");
        loginJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginJF.setLayout(new BorderLayout());
        loginJF.setSize(820, 700);
        loginJF.setLocationRelativeTo(null);

        loginPic.setBackground(Color.white);
        loginBar.setPreferredSize(new Dimension(120, 0));
        loginBar.setBackground(Color.GRAY);

        JLabel nameInLa = new JLabel("Account");
        JTextField nameIn = new JTextField(10);
        JButton jButton = new JButton("Login");

        gameListener = new GameListener();
        gameListener.nameIn = nameIn;
        gameListener.gameUI = this;
        
        loginBar.add(nameInLa);
        loginBar.add(nameIn);
        loginBar.add(jButton);

        loginPic.add(new JLabel(new ImageIcon(".\\src\\login.png")));

        loginJF.add(loginPic, BorderLayout.CENTER);
        loginJF.add(loginBar, BorderLayout.EAST);

        jButton.setActionCommand("login");
        jButton.addActionListener(gameListener);

        nameIn.setActionCommand("login");
        nameIn.addActionListener(gameListener);

        loginJF.setVisible(false);
    }

    public void initGameUI() {
        gameJF = new JFrame();
        gameBar = new JPanel();
        game = new goMokuIconPanel();

        gameJF.setTitle("Go Moku Game");
        gameJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameJF.setLayout(new BorderLayout());
        gameJF.setSize(830, 700);
        gameJF.setLocationRelativeTo(null);

        gameBar.setPreferredSize(new Dimension(120, 0));
        gameBar.setBackground(Color.GRAY);

        textArea.setPreferredSize(new Dimension(100, 500));
        textArea.setEditable(false);

        JLabel nameLa = new JLabel("Welcome " + username + "!");
        gameBar.add(nameLa);

        String[] strs = {"Local PVP", "Local PVE", "Online PVP", "Undo", "Clear", "Save", "Quit"};

        for (String str : strs) {
            JButton bt = new JButton(str);
            gameBar.add(bt);
            bt.setActionCommand(str);
            bt.addActionListener(gameListener);
            
            // Store only game mode buttons
            if (str.equals("Local PVP") || str.equals("Local PVE") || str.equals("Online PVP")) {
                gameModeButtons.add(bt);
            }

            // Store only game control buttons
            if (str.equals("Undo") || str.equals("Clear") || str.equals("Save") || str.equals("Quit")) {
                gameControlButtons.add(bt);
                bt.setVisible(false);
            }
        }

        JButton logout = new JButton("Logout");
        logout.setActionCommand("Logout");
        logout.addActionListener(gameListener);

        gameModeButtons.add(logout);

        gameBar.add(logout, BorderLayout.SOUTH);
        gameBar.add(textArea, BorderLayout.SOUTH);

        gameJF.add(game, BorderLayout.CENTER);
        gameJF.add(gameBar, BorderLayout.EAST);
        

        gameJF.setVisible(false);
    }

    public void showLoginUI() {
        loginJF.setVisible(true);
    }

    public void hideLoginUI() {
        loginJF.setVisible(false);
    }

    public void showGameUI() {
        gameJF.setVisible(true);
    }

    public void hideGameUI() {
        gameJF.setVisible(false);
    }

    public void GameStart() {
        if (Online == 0) {
            if (AImode == 0) {
                System.out.println("Local PVP");
                game.currentGameMode = GameMode.LOCAL_PVP;
            } else {
                System.out.println("Local PVE");
                game.currentGameMode = GameMode.LOCAL_PVE;
            }
        } else {
            System.out.println("Online PVP");
            connect = new connectHandle("localhost", 8888);
        }

    }

    public static void main(String[] args) {
        GameUI gameUI = new GameUI();
        gameUI.initLoginUI();
        gameUI.showLoginUI();
    }


}
