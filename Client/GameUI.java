package Client;

import javax.swing.*;
import java.awt.*;

public class GameUI {
    JFrame loginJF;
    JPanel loginBar;
    JPanel loginPic;

    JFrame gameJF;
    JPanel gameBar;
    JPanel gamePic;

    ClientGameHandle clientGameHandle = new ClientGameHandle();
    GameListener gameListener;

    public void initLoginUI() {

        loginJF = new JFrame();
        loginBar = new JPanel();
        loginPic = new JPanel();

        loginJF.setTitle("Go Moku Login");
        loginJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginJF.setLayout(new BorderLayout());
        loginJF.setSize(700, 700);
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
        gamePic = new JPanel();

        gameJF.setTitle("Go Moku Game");
        gameJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameJF.setLayout(new BorderLayout());
        gameJF.setSize(700, 700);
        gameJF.setLocationRelativeTo(null);

        gameBar.setPreferredSize(new Dimension(120, 0));
        gameBar.setBackground(Color.GRAY);
        gamePic.setBackground(Color.white);
        
        
        JLabel nameLa = new JLabel("Welcome " + clientGameHandle.username +"!");
        gameBar.add(nameLa);

        String[] strs = {"Local PVP", "Local PVE", "Online PVP", "Undo", "Quit"};

        for(String str: strs){
            JButton bt = new JButton(str);
            gameBar.add(bt);
            bt.setActionCommand(str);
            bt.addActionListener(gameListener);
        }

        JButton logout = new JButton("Logout");
        logout.setActionCommand("Logout");
        logout.addActionListener(gameListener);
        
        gameBar.add(logout, BorderLayout.SOUTH);
        
        gamePic.add(new JLabel(new ImageIcon(".\\src\\game.png")));
        
        gameJF.add(gamePic, BorderLayout.CENTER);
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
