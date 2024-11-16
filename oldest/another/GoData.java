import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
 
public interface GoData {
    int X = 70, Y = 70, SIZE = 40, COL = 15, ROW = 15;
 
    public static boolean win(int[][] cl, int c, int d) {
        if (col(cl, c, d) >= 5 || row(cl, c, d) >= 5 || left(cl, c, d) >= 5 || right(cl, c, d) >= 5) {
            return true;
        }
        return false;
    }
 
    public static int col(int[][] cl, int c, int d) {
        int n = 1;
        for (int i = d + 1; i <= ROW; i++) {//左右
            if (cl[c][i] == cl[c][d]) {
                n++;
            } else {
                break;
            }
        }
        for (int i = d - 1; i >= 0; i--) {
            if (cl[c][i] == cl[c][d]) {
                n++;
            } else {
                break;
            }
        }
        return n;
    }
 
    public static int row(int[][] cl, int c, int d) {
        int n = 1;
        for (int i = c + 1; i <= COL; i++) {//上下
            if (cl[c][d] == cl[i][d]) {
                n++;
            } else {
                break;
            }
        }
        for (int i = c - 1; i >= 0; i--) {
            if (cl[c][d] == cl[i][d]) {
                n++;
            } else {
                break;
            }
        }
        return n;
    }
 
    public static int left(int[][] cl, int c, int d) {
        int n = 1, k = 1;
        for (int i = c + 1; i <= ROW; i++) {
            int j = d + k;
            if (j > COL)
                break;
            if (cl[c][d] == cl[i][j]) {
                n++;
            } else {
                break;
            }
            k++;
        }
        k = 1;
        for (int i = c - 1; i >= 0; i--) {
            int j = d - k;
            if (j < 0)
                break;
            if (cl[c][d] == cl[i][j]) {
                n++;
            } else {
                break;
            }
            k++;
        }
        return n;
    }
 
    public static int right(int[][] cl, int c, int d) {
        int n = 1, k = 1;
        for (int i = c + 1; i <= ROW; i++) {
            int j = d - k;
            if (j < 0)
                break;
            if (cl[c][d] == cl[i][j]) {
                n++;
            } else {
                break;
            }
            k++;
        }
        k = 1;
        for (int i = c - 1; i >= 0; i--) {
            int j = d + k;
            if (j > COL)
                break;
            if (cl[c][d] == cl[i][j]) {
                n++;
            } else {
                break;
            }
            k++;
        }
        return n;
    }
 
    public static void drawBackGround(Graphics g, int[][] cl) {
        BufferedImage img = new BufferedImage(X + COL * SIZE + SIZE, Y + ROW * SIZE + SIZE, 2);
        Graphics imgG = img.getGraphics();
 
        imgG.setColor(Color.white);
//        imgG.fillRect(0, 0, jf.getWidth(), jf.getHeight());
 
        drawChessPad(imgG);
        drawChesses(imgG, cl);
 
        g.drawImage(img, 0, 0, null);
    }
 
    public static void drawGamePad(Graphics g, int[][] cl) {
        drawChessPad(g);
        drawChesses(g, cl);
    }
 
    public static void drawChessPad(Graphics g) { //绘制棋盘
        g.setColor(Color.orange);
        g.fillRect(X - 20, Y - 20, SIZE * (COL + 1), SIZE * (ROW + 1));
 
        g.setColor(Color.black);
        for (int i = 0; i <= 15; i++) {
            g.drawLine(X, Y + (i * SIZE), X + (ROW * SIZE), Y + (i * SIZE));
            g.drawLine(X + (i * SIZE), Y, X + (i * SIZE), Y + (COL * SIZE));
        }
    }
 
    public static void drawChesses(Graphics g, int[][] cl) {
        for (int i = 0; i <= ROW; i++) {
            for (int j = 0; j <= COL; j++) {
                if (cl[i][j] == 1) {
                    g.setColor(Color.black);
                } else if (cl[i][j] == 2) {
                    g.setColor(Color.white);
                } else {
                    continue;
                }
                int chessX = i * SIZE + X - SIZE / 2;
                int chessY = j * SIZE + Y - SIZE / 2;
                g.fillOval(chessX, chessY, SIZE, SIZE);
            }
        }
    }
 
    public static void drawAIChesses(Graphics g, int[][] cl){
        drawChessPad(g);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (cl[i][j] == 1){
                    g.setColor(Color.black);
                    g.fillOval(calTX(j),calTY(i),SIZE,SIZE);
                } else if (cl[i][j] == 2) {
                    g.setColor(Color.white);
                    g.fillOval(calTX(j),calTY(i),SIZE,SIZE);
                }
            }
        }
    }
 
    public static void addChess(ArrayList<Chess> chesses, int r, int c, int chessFlag, int index){
        Chess chess = new Chess(r, c, chessFlag, index++);
        chesses.add(chess);
    }
 
    public static int calC(int x) {
        int c = (x - X + SIZE / 2) / SIZE;
        return c;
    }
 
    public static int calR(int y) {
        int r = (y - Y + SIZE / 2) / SIZE;
        return r;
    }
 
    public static boolean isChessPad(int x, int y) {
        if (x < X - SIZE / 2 || x > X - SIZE / 2 + (COL + 1) * SIZE || y < Y - SIZE / 2 || y > Y - SIZE / 2 + (ROW + 1) * SIZE) { //确保棋子不出棋盘
            return false;
        }
        return true;
    }
 
    public static int calTX(int c) {
        int x = c * SIZE + X - SIZE / 2;
        return x;
    }
 
    public static int calTY(int r) {
        int y = r * SIZE + Y - SIZE / 2;
        return y;
    }
 
    public static boolean peopleSet1(int x, int y, int[][] chessList, int chessFlag, int index, ArrayList<Chess> chesses, Graphics g) {
        if (!GoData.isChessPad(x, y)) { //确保棋子不出棋盘
            return false;
        }
        int c = GoData.calC(x); //c,d为棋子在棋盘上的格子坐标
        int d = GoData.calR(y);
 
        if (chessList[c][d] != 0) { //判断棋子是否重叠
            return false;
        }
 
        chessList[c][d] = chessFlag; //棋子坐标和颜色
        Chess chess = new Chess(c, d, chessFlag, index++);
        chesses.add(chess); //棋子顺序
 
        int chessX = GoData.calTX(c); //格子坐标换算窗口坐标
        int chessY = GoData.calTY(d);
 
        if (chessFlag == 1) { //棋子换色
            g.setColor(Color.black);
        } else if (chessFlag == 2) {
            g.setColor(Color.white);
        }
 
        g.fillOval(chessX, chessY, SIZE, SIZE);
        return true;
    }
 
 
    public static boolean peopleSet2(int x, int y, int[][] chessList, int chessFlag, int index, ArrayList<Chess> chesses, Graphics g) {
        if (!GoData.isChessPad(x, y)) { //确保棋子不出棋盘
            return false;
        }
        int c = GoData.calC(x); //c,d为棋子在棋盘上的格子坐标
        int d = GoData.calR(y);
 
        if (chessList[c][d] != 0) { //判断棋子是否重叠
            return false;
        }
        g.setColor(Color.black);
        chessList[c][d] = chessFlag; //棋子坐标和颜色
        Chess chess = new Chess(c, d, chessFlag, index++);
        chesses.add(chess); //棋子顺序
 
        int chessX = GoData.calTX(c); //格子坐标换算窗口坐标
        int chessY = GoData.calTY(d);
 
        g.fillOval(chessX, chessY, SIZE, SIZE);
        return true;
    }
 
 
}