package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class goMokuIconPanel extends JPanel implements ActionListener{
	
	public JButton[][] buttons;
    public int size;
    public int len;
    public int count;
    public boolean playerXTurn = true;
    public String winner = null;
    public String Player1;
    public String Player2;
    public ImageIcon Player1Icon;
    public ImageIcon Player2Icon;
    private Image background;
    public GameMode currentGameMode;

    public goMokuIconPanel() {
        this(15);
    }

    public goMokuIconPanel(int size) {
        this(size, 5);
    }

    public goMokuIconPanel(int size, int len) {
        this.size = size;
        this.len = len;
        this.Player1 = "Black";
        this.Player2 = "White";
        this.Player1Icon = new ImageIcon(".\\chess\\black_chess.png");
        this.Player2Icon = new ImageIcon(".\\chess\\white_chess.png");

        background = new ImageIcon(".\\src\\chessboard.png").getImage();

        setSize(700, 700);
        
        buttons = new JButton[size][size];

        setLayout(new GridLayout(size, size)); // Set layout for the panel
        initializeButtons(0);

        
    }

    public void initializeButtons() {
        initializeButtons(1);
    }

    public void initializeButtons(int visible) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, Math.min(750 / size, 750 / size) / 3));
                buttons[i][j].addActionListener(this);

                if (visible == 0) {
                    buttons[i][j].setOpaque(false);
                    buttons[i][j].setContentAreaFilled(false);
                    buttons[i][j].setBorderPainted(false);
                }

                add(buttons[i][j]); // Add buttons to the panel
            }
        }
    }

    public void clearButtons() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j].setIcon(null);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
public void actionPerformed(ActionEvent e) {
    JButton button = (JButton) e.getSource();
    if (button.getIcon() != null || winner != null) {
        return;
    }
    if (currentGameMode == null) {
        currentGameMode = GameMode.LOCAL_PVP;
    }

    switch (currentGameMode) {
        case LOCAL_PVP -> handleLocalPVP(button);
        case LOCAL_PVE -> handleLocalPVE(button);
        case ONLINE_PVP -> handleOnlinePVP(button);
    }

    count++;
}

private void handleLocalPVP(JButton button) {
    if (playerXTurn) {
        button.setIcon(new ImageIcon(Player1Icon.getImage().getScaledInstance(button.getWidth() * 3 / 5, button.getHeight() * 3 / 5, Image.SCALE_SMOOTH)));
    } else {
        button.setIcon(new ImageIcon(Player2Icon.getImage().getScaledInstance(button.getWidth() * 3 / 5, button.getHeight() * 3 / 5, Image.SCALE_SMOOTH)));
    }

    playerXTurn = !playerXTurn;
}

private void handleLocalPVE(JButton button) {
    button.setIcon(new ImageIcon(Player1Icon.getImage().getScaledInstance(button.getWidth() * 3 / 5, button.getHeight() * 3 / 5, Image.SCALE_SMOOTH)));
    // Add AI logic here for the opponent's move
}

private void handleOnlinePVP(JButton button) {
    // Add networking logic for online gameplay here
    if (playerXTurn) {
        button.setIcon(new ImageIcon(Player1Icon.getImage().getScaledInstance(button.getWidth() * 3 / 5, button.getHeight() * 3 / 5, Image.SCALE_SMOOTH)));
        // Send move to server or opponent
    } else {
        button.setIcon(new ImageIcon(Player2Icon.getImage().getScaledInstance(button.getWidth() * 3 / 5, button.getHeight() * 3 / 5, Image.SCALE_SMOOTH)));
        // Handle move received from server or opponent
    }

    playerXTurn = !playerXTurn;
}



    public static void main(String[] args) {
        JFrame test = new JFrame();
        test.setSize(700, 700);
        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        test.add(new goMokuIconPanel());
        test.setVisible(true);
    }

}
