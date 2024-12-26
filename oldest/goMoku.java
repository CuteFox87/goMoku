import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;

public class goMoku extends tictactoe{

    private Image background;

    public goMoku(){
        super(15, 5, "O", "X");
        setTitle("Go Moku");

        background = new ImageIcon(".\\background\\background.png").getImage();

        JPanel customPane = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };

        customPane.setLayout(new GridLayout(15, 15));
        setContentPane(customPane);

        initializeButtons();
        setVisible(true); 


    }

    public void initializeButtons(){
        initializeButtons(0);
    }

    public static void main(String[] args){
        new goMoku();
    }
}