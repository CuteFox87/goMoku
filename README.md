# GoMoku

GoMoku is a classic board game also known as Five in a Row. The objective of the game is to be the first player to get an unbroken row of five stones horizontally, vertically, or diagonally.

## Game Mode

- Local PVP
- Local PVE (with AI)
- Online PVP

## Features

1. **登入介面**: 輸入自己喜歡的暱稱來遊玩

2. **遊戲大廳**: 選擇想要遊玩的模式

3. **本地 PVP**: 與身邊同學對戰

4. **本地 PVE**: 與高智商下棋**AI**對戰

5. **背景音樂**: 輕鬆愉快的音樂協助思考

6. **背景棋盤圖片**: 顯示棋盤

7. **目錄按鈕音效**: 很吵

8. **下棋音效**: 帶入真實下棋情境

9. **悔棋按鈕**: 當不小心下錯棋時，就可以悔棋

10. **特殊棋面檢測**: 檢測活三、死四、雙活三

11. **清除版面**(~~翻桌~~): 不爽對手時，使出翻桌即可重玩

12. **儲存按鈕**: 將此棋局儲存到本地資料夾 saved 中



## Requirements

- Java 8 or higher

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/CuteFox87/goMoku
    ```
2. Navigate to the project directory:
    ```sh
    cd goMoku
    ```
3. Compile and Run the Server:
    ```sh
    javac ChessServer.java
    java ChessServer
    ```
3. Compile and Run the Client:
    ```sh
    cd Client
    javac -d bin *.java
    java -cp bin Client/GameUI
    ```
4. Enjoy your goMoku Time

## How to Play

1. The game is played on a 15x15 board.
2. Players take turns placing a stone of their color (black or white) on an empty cell.
3. The first player to get five stones in a row (horizontally, vertically, or diagonally) wins the game.

## Contributing

~~Contributions are welcome! Please fork the repository and submit a pull request.~~

## License

~~This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.~~

## Contact

For any questions or suggestions, please open an issue or contact the project maintainer at leo945394dj6@gmail.com.
