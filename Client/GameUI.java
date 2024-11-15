package Client;

import javax.swing.*;
import java.awt.*;

public class GameUI {
    JFrame loginJF = new JFrame();
    JPanel loginBar = new JPanel();
    JPanel loginPic = new JPanel();

    JFrame gameJF = new JFrame();
    JPanel gameBar = new JPanel();
    JPanel gamePic = new JPanel();
    
    public void initLoginUI() {
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
        
        loginBar.add(nameInLa);
        loginBar.add(nameIn);
        loginBar.add(jButton);

        loginPic.add(new JLabel(new ImageIcon(".\\src\\login.png")));

        loginJF.add(loginPic, BorderLayout.CENTER);
        loginJF.add(loginBar, BorderLayout.EAST);

        jButton.addActionListener(e -> {
            String name = nameIn.getText();
            hideLoginUI();
            showGameUI();
        });

    }

    public void initGameUI() {
        JFrame gameJF = new JFrame();
        gameJF.setTitle("Go Moku");
        gameJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameJF.setLayout(new BorderLayout());
        gameJF.setSize(900, 900);
        gameJF.setLocationRelativeTo(null);

        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        jp1.setBackground(Color.white);
        jp2.setPreferredSize(new Dimension(120, 0));
        jp2.setBackground(Color.darkGray);

        gameJF.add(jp1, BorderLayout.CENTER);
        gameJF.add(jp2, BorderLayout.EAST);

        String[] strs = {"Start Game", "Player vs Player", "Player vs AI", "Undo", "Exit"};

        JButton bt1 = new JButton(strs[0]);
        jp2.add(bt1);

        ButtonGroup group = new ButtonGroup();
        JRadioButton jRadioButton1 = new JRadioButton(strs[1], true);
        JRadioButton jRadioButton2 = new JRadioButton(strs[2], false);
        group.add(jRadioButton1);
        group.add(jRadioButton2);
        jp2.add(jRadioButton1);
        jp2.add(jRadioButton2);

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
        gameUI.loginJF.setVisible(true);
    }
}
