package Client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AIChess {

    public String name;
    public String enemy;

    HashMap<String, Integer> map = new HashMap<>();

    public AIChess(String name, String enemy) {
        this.name = name;
        this.enemy = enemy;
        initializeMap();
    }

    private void initializeMap() {
        for (String token : new String[]{name, enemy}) {
            map.put("0" + token + "0", 10);
            map.put("0" + token + token + "0", 100);
            map.put("0" + token + token + token + "0", 1000);
            map.put("0" + token + token + token + token + "0", 5000);

            map.put("0" + token, 5);
            map.put("0" + token + token, 50);
            map.put("0" + token + token + token, 500);
            map.put("0" + token + token + token + token, 2500);
        }
    }

    int size = 15;

    int[][] ai_board = new int[size][size];

    public void getCodeArr(int[][] gameboard) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (gameboard[i][j] == 0) {
                    evaluateDirections(gameboard, i, j);
                }
            }
        }
    }

    private void evaluateDirections(int[][] gameboard, int r, int c) {
        for (int[] dir : new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}, {-1, -1}, {1, 1}, {-1, 1}, {1, -1}}) {
            checkDirection(gameboard, r, c, dir[0], dir[1]);
        }
    }

    private void checkDirection(int[][] gameboard, int r, int c, int dr, int dc) {
        StringBuilder codeStr = new StringBuilder("0");
        int cr = r + dr, cc = c + dc;
        int steps = 0;

        while (cr >= 0 && cr < size && cc >= 0 && cc < size && steps < 4) {
            int cellValue = gameboard[cr][cc];
            if (cellValue == 0) {
                codeStr.append("0");
                break;
            }
            codeStr.append(cellValue);
            cr += dr;
            cc += dc;
            steps++;
        }

        updateAIValue(codeStr.toString(), r, c);
    }

    private void updateAIValue(String codeStr, int r, int c) {
        Integer code = map.get(codeStr);
        if (code == null) {
            code = 0; // Default value if the pattern is not found
        }
        ai_board[r][c] += code;
    }

    public int[] playAI(int[][] gameboard) {
        resetAIBoard();
        getCodeArr(gameboard);
    
        int max = -1;
        int x = -1;
        int y = -1;
    
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (gameboard[i][j] == 0 && ai_board[i][j] > max) {
                    max = ai_board[i][j];
                    x = i;
                    y = j;
                }
            }
        }

        if (x == -1 || y == -1) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (gameboard[i][j] == 0) {
                        return new int[]{i, j};
                    }
                }
            }
        }
    
        return new int[]{x, y};
    }
    

    private void resetAIBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ai_board[i][j] = 0;
            }
        }
    }

    public static void printBoard(int[][] gameboard) {
        for (int[] row : gameboard) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[][] arr = new int[15][15];
        Set<String> placedPositions = new HashSet<>();

        AIChess ai1 = new AIChess("1", "2");
        AIChess ai2 = new AIChess("2", "1");

        arr[5][10] = 1;
        arr[6][10] = 1;
        arr[7][10] = 1;

        for (int i = 0; i < 10; i++) {
            int[] res = ai1.playAI(arr);
            String pos = res[0] + "," + res[1];
            if (res[0] >= 0 && arr[res[0]][res[1]] == 0 && !placedPositions.contains(pos)) {
                arr[res[0]][res[1]] = 1;
                placedPositions.add(pos);
                System.out.println("AI 1 placed at: " + res[0] + " " + res[1]);
                printBoard(arr);
            }
            else {
                System.out.println("AI 1 failed to place at: " + res[0] + " " + res[1]);
            }

            res = ai2.playAI(arr);
            pos = res[0] + "," + res[1];
            if (res[0] >= 0 && arr[res[0]][res[1]] == 0 && !placedPositions.contains(pos)) {
                arr[res[0]][res[1]] = 2;
                placedPositions.add(pos);
                System.out.println("AI 2 placed at: " + res[0] + " " + res[1]);
                printBoard(arr);
            }
            else {
                System.out.println("AI 2 failed to place at: " + res[0] + " " + res[1]);
            }
        }
    }
}
