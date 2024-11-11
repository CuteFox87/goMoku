import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class tictactoeIcon extends tictactoe{

    private ImageIcon Player1icon;
    private ImageIcon Player2icon;
    public String Player1 = "X";
    public String Player2 = "O";

    public tictactoeIcon(int size, int len , ImageIcon Player1, ImageIcon Player2){
        this.size = size;
        this.len = len;
        this.Player1icon = new ImageIcon(Player1.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        this.Player2icon = new ImageIcon(Player2.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));

        buttons = new JButton[size][size];
        setTitle("Tic Tac Toe");
        setSize(windowsize, windowsize);
        setLayout(new GridLayout(size, size));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeButtons();
        setVisible(true);

    }

    public void initializeButtons(){
        initializeButtons(1);
    }

    public void initializeButtons(int visible){
        getContentPane().removeAll();
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, Math.min(getWidth(), getHeight()) / size / 3) );
                buttons[i][j].addActionListener(this);
                buttons[i][j].putClientProperty("Player", "0");

                if(visible == 0){
                    buttons[i][j].setOpaque(false);
                    buttons[i][j].setContentAreaFilled(false);
                    buttons[i][j].setBorderPainted(false);
                }

                add(buttons[i][j]);
            }
        }
        revalidate();
        repaint();
    }

    public void actionPerformed(ActionEvent e){
        JButton button = (JButton) e.getSource();
        if(button.getIcon() != null || winner != null){
            return;
        }

        if(playerXTurn){
            //button.setText(Player1);
            button.setIcon(Player1icon);
            button.putClientProperty("Player", "1");
        }else{
            // button.setText(Player2);
            button.setIcon(Player2icon);
            button.putClientProperty("Player", "2");
        }

        count++;
        if(checkWin()){
            winner = playerXTurn ? Player1 : Player2;
            JOptionPane.showMessageDialog(this, winner + " wins!");
            resetGame();
        }else if(checkTie()){
            JOptionPane.showMessageDialog(this, "It's a draw!");
            resetGame();
        }
        playerXTurn = !playerXTurn;
        
    }

    @Override
    public boolean checkWin() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (buttons[i][j].getClientProperty("Player").equals("0")) {
                    continue;
                }
                for (int k = 0; k < dir.length; k++) {
                    int x = i;
                    int y = j;
                    int count = 0;
                    while (x >= 0 && x < size && y >= 0 && y < size &&
                        buttons[x][y].getClientProperty("Player").equals(buttons[i][j].getClientProperty("Player"))) {
                        x += dir[k][0];
                        y += dir[k][1];
                        count++;
                    }
                    if (count >= len) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    @Override
    public boolean checkTie() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (buttons[i][j].getClientProperty("Player").equals("0")) {
                    return false;
                }
            }
        }
        return true;
    }



    public void resetGame(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                buttons[i][j].setIcon(null);
                buttons[i][j].putClientProperty("Player", "0");
            }
        }
        playerXTurn = true;
        winner = null;
        count = 0;
    }

    public static void main(String[] args){
        ImageIcon Player1 = new ImageIcon(".\\chess\\black_chess.png");
        ImageIcon Player2 = new ImageIcon(".\\chess\\white_chess.png");
        new tictactoeIcon(3, 3, Player1, Player2);
    }


}

