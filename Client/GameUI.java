package Client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameUI {
    JFrame loginJF;
    JPanel loginBar;
    JPanel loginPic;

    JFrame gameJF;
    JPanel gameBar;
    goMokuIconPanel gameBoard;

    ClientGameHandle clientGameHandle = new ClientGameHandle();
    GameListener gameListener;

    ArrayList<JButton> gameModeButtons = new ArrayList<>();
    ArrayList<JButton> gameControlButtons = new ArrayList<>();

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
        gameListener.clientGameHandle = clientGameHandle;
        
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
        gameBoard = new goMokuIconPanel();

        gameJF.setTitle("Go Moku Game");
        gameJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameJF.setLayout(new BorderLayout());
        gameJF.setSize(830, 700);
        gameJF.setLocationRelativeTo(null);

        gameBar.setPreferredSize(new Dimension(120, 0));
        gameBar.setBackground(Color.GRAY);

        JLabel nameLa = new JLabel("Welcome " + clientGameHandle.username + "!");
        gameBar.add(nameLa);

        String[] strs = {"Local PVP", "Local PVE", "Online PVP", "Undo", "Quit"};

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
            if (str.equals("Undo") || str.equals("Quit")) {
                gameControlButtons.add(bt);
                bt.setVisible(false);
            }
        }

        JButton logout = new JButton("Logout");
        logout.setActionCommand("Logout");
        logout.addActionListener(gameListener);

        gameModeButtons.add(logout);

        gameBar.add(logout, BorderLayout.SOUTH);

        gameJF.add(gameBoard, BorderLayout.CENTER);
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

    public static void main(String[] args) {
        GameUI gameUI = new GameUI();
        gameUI.initLoginUI();
        gameUI.showLoginUI();
    }


}
