import javax.swing.*;
import java.awt.*;
 
public class GameUI implements GoData {
    JFrame loginJF = new JFrame();
    JFrame gameJF = new JFrame();
    GameListener gameListener = new GameListener();
    Mypanel jp1 = new Mypanel();
    JPanel jp2 = new JPanel();
 
    public void initLoginUI() {
        loginJF.setTitle("登录");
        loginJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginJF.setLayout(new FlowLayout());
        loginJF.setSize(400, 400);
        loginJF.setLocationRelativeTo(null);
        JLabel nameInLa = new JLabel("账号");
        JTextField nameIn = new JTextField(30);
        JButton jButton = new JButton("登录");
        loginJF.add(nameInLa);
        loginJF.add(nameIn);
        loginJF.add(jButton);
        loginJF.setVisible(true);
 
        jButton.addActionListener(gameListener);
        gameListener.nameIn = nameIn;
        gameListener.gameUI = this;
    }
 
    public void initGameUI() {
        gameJF.setTitle("五子棋");
        gameJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameJF.setLayout(new BorderLayout());
        gameJF.setSize(900, 900);
        gameJF.setLocationRelativeTo(null);
 
        jp1.setBackground(Color.white);
        jp2.setPreferredSize(new Dimension(120, 0));
        jp2.setBackground(Color.darkGray);
 
        gameJF.add(jp1, BorderLayout.CENTER);
        gameJF.add(jp2, BorderLayout.EAST);
 
        String[] strs = {"开始游戏", "人人对战", "人机对战", "悔棋", "退出"};
 
        JButton bt1 = new JButton(strs[0]);
        jp2.add(bt1);
        bt1.addActionListener(gameListener);
 
        ButtonGroup group = new ButtonGroup();
        JRadioButton jRadioButton1 = new JRadioButton(strs[1], true);
        JRadioButton jRadioButton2 = new JRadioButton(strs[2], false);
        group.add(jRadioButton1);
        group.add(jRadioButton2);
        jp2.add(jRadioButton1);
        jp2.add(jRadioButton2);
        jRadioButton1.addActionListener(gameListener);
        jRadioButton2.addActionListener(gameListener);
        gameListener.bt1 = jRadioButton1;
        gameListener.bt2 = jRadioButton2;
 
 
        JButton bt2 = new JButton(strs[3]);
        jp2.add(bt2);
        bt2.addActionListener(gameListener);
 
        JButton bt3 = new JButton(strs[4]);
        jp2.add(bt3);
        bt3.addActionListener(gameListener);
 
        jp1.gameListener = gameListener;
        jp1.addMouseListener(gameListener);
        gameListener.g = jp1.getGraphics();
    }
 
    public void showLoginUI() {
        loginJF.setVisible(true);
    }
 
    public void showGameUI() {
        gameJF.setVisible(true);
        Graphics g = jp1.getGraphics();
        gameListener.g = g;
    }
 
    public void hideLoginUI() {
        loginJF.setVisible(false);
    }
 
    public void hideGameUI() {
        gameJF.setVisible(false);
    }
 
    public static void main(String[] args) {
        GameUI gameUI = new GameUI();
        gameUI.initLoginUI();
        gameUI.initGameUI();
 
    }
}