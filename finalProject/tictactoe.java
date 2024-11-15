import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class tictactoe extends JFrame implements ActionListener{
    public JButton[][] buttons;
    public int size;
    public int len;
    public int count;
    public boolean playerXTurn = true;
    public String winner = null;
    public int windowsize = 750;
    public String Player1;
    public String Player2;

    public tictactoe(){
        this(3);
    }

    public tictactoe(int size){
        this(size, 3, "X", "O");
    }


    public tictactoe(int size, int len, String Player1, String Player2){
        this.size = size;
        this.len = len;
        this.Player1 = Player1;
        this.Player2 = Player2;
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
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, Math.min(getWidth(), getHeight()) / size / 3) );
                buttons[i][j].addActionListener(this);

                if(visible == 0){
                    buttons[i][j].setOpaque(false);
                    buttons[i][j].setContentAreaFilled(false);
                    buttons[i][j].setBorderPainted(false);
                }

                add(buttons[i][j]);
            }
        }
    }

    public int [][] dir = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};

    public boolean checkWin(){
        for(int i=0; i<buttons.length; i++){
            for(int j=0; j<buttons[i].length; j++){
                if(buttons[i][j].getText().equals("")){
                    continue;
                }
                for(int k=0; k<dir.length; k++){
                    int x = i;
                    int y = j;
                    int count = 0;
                    while(x >= 0 && x < buttons.length && y >= 0 && y < buttons[x].length && buttons[x][y].getText().equals(buttons[i][j].getText())){
                        count++;
                        x += dir[k][0];
                        y += dir[k][1];
                    }
                    if(count == len){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkTie(){
        for(int i = 0; i < buttons.length; i++){
            for(int j = 0; j < buttons[i].length; j++){
                if(buttons[i][j].getText().equals("")){
                    return false;
                }
            }
        }
        return true;
    }

    public void resetGame(){
        for(int i=0; i<buttons.length; i++){
            for(int j=0; j<buttons[i].length; j++){
                buttons[i][j].setText("");
            }
        }
        playerXTurn = true;
        winner = null;
        count = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        JButton clickedButton = (JButton) e.getSource();

        if(!clickedButton.getText().equals("") || winner != null){
            return;
        }

        if(playerXTurn){
            clickedButton.setText(Player1);
        }else{
            clickedButton.setText(Player2);
        }
        count++;

        if(checkWin()){
            if(playerXTurn){
                winner = Player1;
            }else{
                winner = Player2;
            }
            JOptionPane.showMessageDialog(null, "Player " + winner + " wins!");
            resetGame();
        }else if(checkTie()){
            JOptionPane.showMessageDialog(null, "It's a tie!");
            resetGame();
        }

        playerXTurn = !playerXTurn;
    }

    public static void main(String[] args){
        new tictactoe();
    }
}