package Client;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.*;
import java.text.SimpleDateFormat;
import java.io.*;

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
    private Stack<int[]> moves = new Stack<>();
    AIChess ai;

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
        this.Player1Icon = new ImageIcon(".\\src\\chess\\black_chess.png");
        this.Player2Icon = new ImageIcon(".\\src\\chess\\white_chess.png");

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
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 0));
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        playchess();

        JButton button = (JButton) e.getSource();
        if (button.getIcon() != null || winner != null) {
            return;
        }
        if (currentGameMode == null) {
            currentGameMode = GameMode.LOCAL_PVP;
        }

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (button == buttons[i][j]) {
                    moves.push(new int[]{i, j});
                }
            }
        }

        switch (currentGameMode) {
            case LOCAL_PVP -> handleLocalPVP(button);
            case LOCAL_PVE -> handleLocalPVE(button);
            case ONLINE_PVP -> handleOnlinePVP(button);
        }

        count++;
    }

    private void playchess() {
        try {
            File sound = new File("src\\sound\\chess.wav");
            AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();

            Thread.sleep(clip.getMicrosecondLength() / 100000);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleLocalPVP(JButton button) {
        if (playerXTurn) {
            button.setIcon(new ImageIcon(Player1Icon.getImage().getScaledInstance(button.getWidth() * 3 / 5, button.getHeight() * 3 / 5, Image.SCALE_SMOOTH)));
            button.setText("X");
            if (checkWin()) {
                winner = Player1;
                JOptionPane.showMessageDialog(this, winner + " wins!");
            } else if (checkTie()) {
                JOptionPane.showMessageDialog(this, "It's a tie!");
            }

            checkSpecialPatterns();
            playerXTurn = !playerXTurn;

        } else {
            button.setIcon(new ImageIcon(Player2Icon.getImage().getScaledInstance(button.getWidth() * 3 / 5, button.getHeight() * 3 / 5, Image.SCALE_SMOOTH)));
            button.setText("O");
            if (checkWin()) {
                winner = Player2;
                JOptionPane.showMessageDialog(this, winner + " wins!");
                
            } else if (checkTie()) {
                JOptionPane.showMessageDialog(this, "It's a tie!");
            }

            checkSpecialPatterns();
            playerXTurn = !playerXTurn;
        }
    }

    private void handleLocalPVE(JButton button) {
        button.setIcon(new ImageIcon(Player1Icon.getImage().getScaledInstance(button.getWidth() * 3 / 5, button.getHeight() * 3 / 5, Image.SCALE_SMOOTH)));
        button.setText("X");

        if (checkWin()) {
            winner = Player1;
            JOptionPane.showMessageDialog(this, winner + " wins!");
        } else if (checkTie()) {
            JOptionPane.showMessageDialog(this, "It's a tie!");
        }else {

            checkSpecialPatterns();

            ai = new Client.AIChess("2", "1");

            int[][] gameBoard = new int[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                if (buttons[i][j].getText().equals("X")) {
                    gameBoard[i][j] = 1;
                } else if (buttons[i][j].getText().equals("O")) {
                    gameBoard[i][j] = 2;
                } else {
                    gameBoard[i][j] = 0;
                }
                }
            }
            int[] res = ai.playAI(gameBoard);
            buttons[res[0]][res[1]].setIcon(new ImageIcon(Player2Icon.getImage().getScaledInstance(button.getWidth() * 3 / 5, button.getHeight() * 3 / 5, Image.SCALE_SMOOTH)));
            buttons[res[0]][res[1]].setText("O");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            playchess();

            moves.push(new int[]{res[0], res[1]});
            
            if (checkWin()) {
                winner = "AI";
                JOptionPane.showMessageDialog(this, winner + " wins!");
            } else if (checkTie()) {
                JOptionPane.showMessageDialog(this, "It's a tie!");
            }

            checkSpecialPatterns();
        
        }
        
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

    public int[][] dir = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};

    public boolean checkWin() {
        int len = 5;
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (buttons[i][j].getText().equals("")) {
                    continue;
                }
                for (int k = 0; k < dir.length; k++) {
                    int x = i;
                    int y = j;
                    int count = 0;
                    while (x >= 0 && x < buttons.length && y >= 0 && y < buttons[x].length &&
                        buttons[x][y].getText().equals(buttons[i][j].getText())) {
                        count++;
                        x += dir[k][0];
                        y += dir[k][1];
                    }
                    if (count == len) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkTie() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    public void checkSpecialPatterns() {

        // Check for "活三" (Live Three)
        if (checkLiveThree()) {
            GameUI.textArea.append("Live Three detected!\n");
            System.out.println("Live Three detected!");
        }

        // Check for "死四" (Dead Four)
        if (checkDeadFour()) {
            GameUI.textArea.append("Dead Four detected!\n");
            System.out.println("Dead Four detected!");
        }

        // Check for "雙活三" (Double Live Three)
        if (checkDoubleLiveThree()) {
            GameUI.textArea.append("Double Live Three detected!\n");
            System.out.println("Double Live Three detected!");
        }

        return;
    }

    private boolean checkLiveThree() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (buttons[i][j].getText().equals("")) {
                    continue;
                }
                String player = buttons[i][j].getText();
    
                for (int[] direction : dir) {
                    int count = 0;
                    int x = i, y = j;
    
                    // Check forward
                    while (x >= 0 && x < size && y >= 0 && y < size && buttons[x][y].getText().equals(player)) {
                        count++;
                        x += direction[0];
                        y += direction[1];
                    }
    
                    // Check backward
                    x = i - direction[0];
                    y = j - direction[1];
                    while (x >= 0 && x < size && y >= 0 && y < size && buttons[x][y].getText().equals(player)) {
                        count++;
                        x -= direction[0];
                        y -= direction[1];
                    }
    
                    // Check open ends
                    boolean openStart = i - direction[0] >= 0 && i - direction[0] < size &&
                            j - direction[1] >= 0 && j - direction[1] < size &&
                            buttons[i - direction[0]][j - direction[1]].getText().equals("");
    
                    boolean openEnd = x >= 0 && x < size && y >= 0 && y < size && buttons[x][y].getText().equals("");
    
                    if (count == 3 && openStart && openEnd) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    

    private boolean checkDeadFour() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (buttons[i][j].getText().equals("")) {
                    continue;
                }
                String player = buttons[i][j].getText();
    
                for (int[] direction : dir) {
                    int count = 0;
                    int x = i, y = j;
    
                    // Check forward
                    while (x >= 0 && x < size && y >= 0 && y < size && buttons[x][y].getText().equals(player)) {
                        count++;
                        x += direction[0];
                        y += direction[1];
                    }
    
                    // Check backward
                    x = i - direction[0];
                    y = j - direction[1];
                    while (x >= 0 && x < size && y >= 0 && y < size && buttons[x][y].getText().equals(player)) {
                        count++;
                        x -= direction[0];
                        y -= direction[1];
                    }
    
                    // Check one open end
                    boolean openStart = i - direction[0] >= 0 && i - direction[0] < size &&
                            j - direction[1] >= 0 && j - direction[1] < size &&
                            buttons[i - direction[0]][j - direction[1]].getText().equals("");
    
                    boolean closedEnd = !(x >= 0 && x < size && y >= 0 && y < size && buttons[x][y].getText().equals(""));
    
                    if (count == 4 && (openStart || closedEnd)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    

    private boolean checkDoubleLiveThree() {
        int liveThreeCount = 0;
    
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!buttons[i][j].getText().equals("")) {
                    continue;
                }
                String player = playerXTurn ? "X" : "O";
    
                for (int[] direction : dir) {
                    int count = 0;
                    int x = i, y = j;
    
                    // Check forward
                    while (x >= 0 && x < size && y >= 0 && y < size && buttons[x][y].getText().equals(player)) {
                        count++;
                        x += direction[0];
                        y += direction[1];
                    }
    
                    // Check backward
                    x = i - direction[0];
                    y = j - direction[1];
                    while (x >= 0 && x < size && y >= 0 && y < size && buttons[x][y].getText().equals(player)) {
                        count++;
                        x -= direction[0];
                        y -= direction[1];
                    }
    
                    // Check open ends
                    boolean openStart = i - direction[0] >= 0 && i - direction[0] < size &&
                            j - direction[1] >= 0 && j - direction[1] < size &&
                            buttons[i - direction[0]][j - direction[1]].getText().equals("");
    
                    boolean openEnd = x >= 0 && x < size && y >= 0 && y < size && buttons[x][y].getText().equals("");
    
                    if (count == 3 && openStart && openEnd) {
                        liveThreeCount++;
                    }
    
                    if (liveThreeCount >= 2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    

    public void resetGame() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setIcon(null);
            }
        }
        playerXTurn = true;
        winner = null;
        count = 0;
    }

    public void printBoard() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                System.out.print(buttons[i][j].getText() + " ");
            }
            System.out.println();
        }
    }

    public void setGameMode(GameMode gameMode) {
        this.currentGameMode = gameMode;
    }

    public void saveGame() {
        // use time stamp to save the game
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File dir = new File("saved");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = "saved/goMokuGame_" + timeStamp + ".txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            for (int i = 0; i < buttons.length; i++) {
                for (int j = 0; j < buttons[i].length; j++) {
                    if (buttons[i][j].getText().equals("")) {
                        writer.write("- ");
                    } else {
                    writer.write(buttons[i][j].getText() + " ");
                    }
                }
                writer.write("\n");
            }
            writer.write("Current turn: " + (playerXTurn ? Player1 : Player2) + "\n");
            writer.write("Winner: " + (winner != null ? winner : "None") + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stack<int[]> temp = new Stack<>();
        temp.addAll(moves);
        Stack<int[]> reversedMoves = new Stack<>();
        while (!temp.isEmpty()) {
            reversedMoves.push(temp.pop());
        }
        while (!reversedMoves.isEmpty()) {
            int[] move = reversedMoves.pop();
            try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write((playerXTurn ? Player2 : Player1) + ": " + Arrays.toString(move) + "\n");
            } catch (IOException e) {
            e.printStackTrace();
            }
            playerXTurn = !playerXTurn;
        }
        
        
    }


    public void undoGame() {
        // undo the last move
        // undo me and enemy move at the same time

        if (moves.isEmpty()) {
            return;
        }

        int[] lastMove = moves.pop();
        buttons[lastMove[0]][lastMove[1]].setText("");
        buttons[lastMove[0]][lastMove[1]].setIcon(null);

        if (currentGameMode == GameMode.LOCAL_PVE) {
            int[] lastMove2 = moves.pop();
            buttons[lastMove2[0]][lastMove2[1]].setText("");
            buttons[lastMove2[0]][lastMove2[1]].setIcon(null);
        }

        playerXTurn = !playerXTurn;
        winner = null;
    }



    public static void main(String[] args) {
        JFrame test = new JFrame();
        test.setSize(700, 700);
        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        test.add(new goMokuIconPanel());
        test.setVisible(true);
    }

}
