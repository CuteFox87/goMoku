import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Menu{
    JFrame loginJF = new JFrame();
    JFrame gameJF = new JFrame();
    private Image background;
    
    public void initLoginUI(){
        loginJF.setTitle("GoGame");
        loginJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginJF.setLayout(new FlowLayout());
        loginJF.setSize(400, 400);
        loginJF.setLocationRelativeTo(null);
        loginJF.setResizable(false);

        JLabel nameInLa = new JLabel("Account");
        JTextField nameIn = new JTextField(30);

        JButton jButton = new JButton("Login");

        loginJF.add(nameInLa);
        loginJF.add(nameIn);
        loginJF.add(jButton);

    }

    public void initGameUI(){
        gameJF.setTitle("GoGame");
        gameJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameJF.setLayout(new BorderLayout());
        gameJF.setSize(900, 900);
        gameJF.setLocationRelativeTo(null);
        gameJF.setResizable(false);

        JPanel jp2 = new JPanel();
        jp2.setPreferredSize(new Dimension(120, 0));
        jp2.setBackground(Color.darkGray);

        background = new ImageIcon(".\\background.png").getImage();
        JPanel customPane = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(background, 50, 50, getWidth()-50, getHeight()-50, this);
            }
        };

        gameJF.add(jp2, BorderLayout.EAST);
        gameJF.add(customPane, BorderLayout.CENTER);

    }

    public void showLoginUI(){
        loginJF.setVisible(true);
    }

    public void hideLoginUI(){
        loginJF.setVisible(false);
    }

    public void showGameUI(){
        gameJF.setVisible(true);
    }

    public void hideGameUI(){
        gameJF.setVisible(false);
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.initLoginUI();
        menu.initGameUI();
        menu.showGameUI();
    }
}