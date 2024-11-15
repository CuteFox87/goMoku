import java.awt.*;
import java.util.HashMap;
 
public class AIChess implements GoData {
 
    HashMap<String, Integer> map = new HashMap<>();
 
    {
        map.put("010", 10);
        map.put("0110", 100);
        map.put("01110", 1000);
        map.put("011110", 5000);
 
        map.put("020", 10);
        map.put("0220", 100);
        map.put("02220", 1000);
        map.put("022220", 5000);
 
        map.put("01", 10 / 2);
        map.put("011", 100 / 2);
        map.put("0111", 1000 / 2);
        map.put("01111", 5000 / 2);
 
        map.put("02", 10 / 2);
        map.put("022", 100 / 2);
        map.put("0222", 1000 / 2);
        map.put("02222", 5000 / 2);
    }
 
    int[][] aiChess = new int[16][16];
 
    //Text
//    public static void main(String[] args) {
//        int[][] arr = {
//                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//        };
//        AIChess aiChess1 = new AIChess();
//        aiChess1.getCodeArr(arr);
//    }
 
    //遍历棋盘
    public void getCodeArr(int[][] chessList) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int cnum = chessList[i][j];
                if (cnum == 0) {
                    //遍历八个方向
                    toLeft(chessList, i, j);
                    toRight(chessList, i, j);
                    toUp(chessList, i, j);
                    toDown(chessList, i, j);
                    toLeftUp(chessList, i, j);
                    toRightDown(chessList, i, j);
                    toRightUp(chessList, i, j);
                    toLeftDown(chessList, i, j);
                }
            }
        }
        /*for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                System.out.print(aiChess[i][j] + ",");
            }
            System.out.println();
        }*/
    }
 
    //左下
    private void toLeftDown(int[][] chessList, int r, int c) {
        if (r == 15 || c == 0) {
            return;
        }
        int cn1 = chessList[r + 1][c - 1];
        if (cn1 == 0) {
            return;
        }
        String codeStr = "0" + cn1;
        int k = 2;
        for (int i = r + 2; i <= 15; i++) {
            int j = c - k;
            if (j < 0) {
                break;
            }
            k--;
            if (chessList[i][j] == cn1) {
                codeStr += cn1;
            } else {
                if (chessList[i][j] == 0) {
                    codeStr += "0";
                }
                break;
            }
        }
        int code = map.get(codeStr);
//        System.out.println("codeStr: " + codeStr + "code: " + code);
        aiChess[r][c] += code;
    }
 
    //右上
    private void toRightUp(int[][] chessList, int r, int c) {
        if (r == 0 || c == 15) {
            return;
        }
        int cn1 = chessList[r - 1][c + 1];
        if (cn1 == 0) {
            return;
        }
        String codeStr = "0" + cn1;
        int k = 2;
        for (int i = r - 2; i >= 0; i--) {
            int j = c + k;
            if (j > 15) {
                break;
            }
            k++;
            if (chessList[i][j] == cn1) {
                codeStr += cn1;
            } else {
                if (chessList[i][j] == 0) {
                    codeStr += "0";
                }
                break;
            }
        }
        int code = map.get(codeStr);
//        System.out.println("codeStr: " + codeStr + "code: " + code);
        aiChess[r][c] += code;
    }
 
    //右下
    private void toRightDown(int[][] chessList, int r, int c) {
        if (r == 15 || c == 15) {
            return;
        }
        int cn1 = chessList[r + 1][c + 1];
        if (cn1 == 0) {
            return;
        }
        String codeStr = "0" + cn1;
        int k = 2;
        for (int i = r + 2; i <= 15; i++) {
            int j = c + k;
            if (j > 15) {
                break;
            }
            k++;
            if (chessList[i][j] == cn1) {
                codeStr += cn1;
            } else {
                if (chessList[i][j] == 0) {
                    codeStr += "0";
                }
                break;
            }
        }
        int code = map.get(codeStr);
//        System.out.println("codeStr: " + codeStr + "code: " + code);
        aiChess[r][c] += code;
    }
 
    //左上
    private void toLeftUp(int[][] chessList, int r, int c) {
        if (r == 0 || c == 0) {
            return;
        }
        int cn1 = chessList[r - 1][c - 1];
        if (cn1 == 0) {
            return;
        }
        String codeStr = "0" + cn1;
        int k = 2;
        for (int i = r - 2; i >= 0; i--) {
            int j = c - k;
            if (j < 0) {
                break;
            }
            k--;
            if (chessList[i][j] == cn1) {
                codeStr += cn1;
            } else {
                if (chessList[i][j] == 0) {
                    codeStr += "0";
                }
                break;
            }
        }
        int code = map.get(codeStr);
//        System.out.println("codeStr: " + codeStr + "code: " + code);
        aiChess[r][c] += code;
    }
 
    //向下
    private void toDown(int[][] chessList, int r, int c) {
        if (r == 15) {
            return;
        }
        int cn1 = chessList[r + 1][c];
        if (cn1 == 0) {
            return;
        }
        String codeStr = "0" + cn1;
        for (int i = r + 2; i <= 15; i++) {
            if (chessList[i][c] == cn1) {
                codeStr += cn1;
            } else {
                if (chessList[i][c] == 0) {
                    codeStr += "0";
                }
                break;
            }
        }
        int code = map.get(codeStr);
//        System.out.println("codeStr: " + codeStr + "code: " + code);
        aiChess[r][c] += code;
    }
 
    //向上
    private void toUp(int[][] chessList, int r, int c) {
        if (r == 0) {
            return;
        }
        int cn1 = chessList[r - 1][c];
        if (cn1 == 0) {
            return;
        }
        String codeStr = "0" + cn1;
        for (int i = r - 2; i >= 0; i--) {
            if (chessList[i][c] == cn1) {
                codeStr += cn1;
            } else {
                if (chessList[i][c] == 0) {
                    codeStr += "0";
                }
                break;
            }
        }
        int code = map.get(codeStr);
//        System.out.println("codeStr: " + codeStr + "code: " + code);
        aiChess[r][c] += code;
    }
 
    //向右
    private void toRight(int[][] chessList, int r, int c) {
        if (c == 15) {
            return;
        }
        int cn1 = chessList[r][c + 1];
        if (cn1 == 0) {
            return;
        }
        String codeStr = "0" + cn1;
        for (int i = c + 2; i <= 15; i++) {
            if (chessList[r][i] == cn1) {
                codeStr += cn1;
            } else {
                if (chessList[r][i] == 0) {
                    codeStr += "0";
                }
                break;
            }
        }
        int code = map.get(codeStr);
//        System.out.println("codeStr: " + codeStr + "code: " + code);
        aiChess[r][c] += code;
    }
 
    //向左计算权值
    private void toLeft(int[][] chessList, int r, int c) {
        if (c == 0) {
            return;
        }
        int cn1 = chessList[r][c - 1];
        if (cn1 == 0) {
            return;
        }
        String codeStr = "0" + cn1;
        for (int i = c - 2; i >= 0; i--) {
            if (chessList[r][i] == cn1) {
                codeStr += cn1;
            } else {
                if (chessList[r][i] == 0) {
                    codeStr += "0";
                }
                break;
            }
        }
 
        int code = map.get(codeStr);
//        System.out.println("codeStr: " + codeStr + "code: " + code);
        aiChess[r][c] += code;
    }
 
    public void playAI(int[][] chessList, Graphics g) {
 
        getCodeArr(chessList);
        int maxnum = 0, maxX = 0, maxY = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (aiChess[i][j] > maxnum) {
                    maxnum = aiChess[i][j];
                    maxX = i;
                    maxY = j;
                }
            }
        }
        g.setColor(Color.white);
        g.fillOval(GoData.calTX(maxX), GoData.calTY(maxY), SIZE, SIZE);
    }
 
 
}