import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
 
public class GameListener extends MouseAdapter implements ActionListener, GoData {
    public GameUI gameUI;
    public JTextField nameIn;
    Graphics g;
    String cl;
    int chessFlag = 1;
    int index = 0;
    ArrayList<Chess> chesses = new ArrayList<>();
    int[][] chessList = new int[ROW + 1][COL + 1];
    AIChess ai = new AIChess();
    Boolean isAI = false;
    Boolean isGoing = false;
    Boolean isWin = false;
    JRadioButton bt1, bt2;
 
    @Override
    public void actionPerformed(ActionEvent e) {
        String ac = e.getActionCommand();
        cl = ac;
        switch (ac) {
            case "登录" -> {
                String name = nameIn.getText();
                gameUI.showGameUI();
                gameUI.hideLoginUI();
            }
            case "退出" -> {
                gameUI.showLoginUI();
                gameUI.hideGameUI();
            }
            case "开始游戏" -> {
                isGoing = true;
                GoData.drawChessPad(g);
                bt1.setEnabled(!isGoing);
                bt2.setEnabled(!isGoing);
                JButton button = (JButton) e.getSource(); //获取事件对象，因为是按钮，所以强制转换成按钮
                button.setText("结束游戏");
            }
            case "人人对战" -> {
                if (isGoing) {
                    return;
                }
                isAI = false;
            }
            case "人机对战" -> {
                if (isGoing) {
                    return;
                }
                isAI = true;
            }
            case "结束游戏" -> {
                isWin = false;
                isGoing = false;
                if (!isAI) {
                    chessFlag = 1;
                    index = 0;
                    chesses.clear();
                    chessList = new int[ROW + 1][COL + 1];
                } else {
                    chessFlag = 1;
                    index = 0;
                    chesses.clear();
                    chessList = new int[ROW + 1][COL + 1];
                    ai.aiChess = new int[16][16];
                }
                bt1.setEnabled(true);
                bt2.setEnabled(true);
                JButton button = (JButton) e.getSource();
                button.setText("开始游戏");
            }
            case "悔棋" -> {
 
                if (chesses.isEmpty()) {
                    return;
                }
                if (isWin){
                    isWin = false;
                }
                if (!isAI) {
                    if (chesses.get(chesses.size() - 1).chessFlag == 1) { //确保悔棋后棋子颜色正确
                        chessFlag = 1;
                    } else if (chesses.get(chesses.size() - 1).chessFlag == 2) {
                        chessFlag = 2;
                    }
                    chessList[chesses.get(chesses.size() - 1).x][chesses.get(chesses.size() - 1).y] = 0;
                    chesses.remove(chesses.size() - 1);
                    GoData.drawGamePad(g, chessList);
                } else {
                    chessList[chesses.get(chesses.size() - 1).x][chesses.get(chesses.size() - 1).y] = 0;
                    chesses.remove(chesses.size() - 1);
                    chessList[chesses.get(chesses.size() - 1).x][chesses.get(chesses.size() - 1).y] = 0;
                    chesses.remove(chesses.size() - 1);
                    GoData.drawAIChesses(g, chessList);
                }
            }
        }
        System.out.println(ac);
    }
 
    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println(x + "," + y);
        if(isWin){
            return;
        }
        if (cl.equals("开始游戏") && !isAI || cl.equals("悔棋") && !isAI) {
            if (GoData.peopleSet1(x, y, chessList, chessFlag, index, chesses, g)) {
                if (chessFlag == 1) { //棋子换色
                    chessFlag = 2;
                } else if (chessFlag == 2) {
                    chessFlag = 1;
                }
                if (GoData.win(chessList, GoData.calC(x), GoData.calR(y))) {
                    JOptionPane.showMessageDialog(null, chessFlag == 1 ? "白棋胜利~" : "黑棋胜利~");
                    chessFlag = 0;
                    isWin = true;
                }
            }
        } else if (cl.equals("开始游戏") && isAI || cl.equals("悔棋") && isAI) {
            if (!GoData.isChessPad(x, y)) {
                return;
            }
            g.setColor(Color.black);
            int chessC = GoData.calC(x);
            int chessR = GoData.calR(y);
            System.out.println(chessR + "," + chessC);
            if (chessList[chessR][chessC] != 0) {
                return;
            }
            GoData.addChess(chesses, chessR, chessC, 1, index);
            chessList[chessR][chessC] = 1;
            int tx = GoData.calTX(chessC);
            int ty = GoData.calTY(chessR);
            g.fillOval(tx, ty, SIZE, SIZE);
            if (GoData.win(chessList, chessR, chessC)) {
                JOptionPane.showMessageDialog(null, "黑棋胜利~");
                isWin = true;
                return;
            }
 
            ai.getCodeArr(chessList);
            int max = 0, maxC = -1, maxR = -1;
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    if (ai.aiChess[i][j] > max) {
                        max = ai.aiChess[i][j];
                        maxC = j;
                        System.out.println(maxC);
                        maxR = i;
                        System.out.println(maxR);
                    }
                }
            }
            g.setColor(Color.white);
            g.fillOval(GoData.calTX(maxC), GoData.calTY(maxR), SIZE, SIZE);
            chesses.add(new Chess(maxR, maxC, 2, index++));
            chessList[maxR][maxC] = 2;
            if (GoData.win(chessList, maxR, maxC)) {
                JOptionPane.showMessageDialog(null, "白棋胜利~");
                isWin = true;
            }
            ai.aiChess = new int[16][16];
        }
    }
}