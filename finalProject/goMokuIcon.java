import javax.swing.*;
import java.awt.*;
import javax.swing.JOptionPane;

class goMokuIcon extends tictactoeIcon{
    private Image background;

    public goMokuIcon(){
        super(15, 5, new ImageIcon(".\\chess\\black_chess.png"), new ImageIcon(".\\chess\\white_chess.png"), "Black", "White");
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
        new goMokuIcon();
    }
}