package Client;

public class ClientGameHandle {
    public String username;
    public int Online;
    public int AImode;
    public connectHandle connect;
    public GameHandle gameHandle;

    public void GameStart() {
        if (Online == 0) {
            if (AImode == 0) {
                System.out.println("Local PVP");
                gameHandle = new GameHandle(GameMode.LOCAL_PVP);
            } else {
                System.out.println("Local PVE");
                gameHandle = new GameHandle(GameMode.LOCAL_PVE);
            }
        } else {
            System.out.println("Online PVP");
            connect = new connectHandle("localhost", 8888);
        }
        gameHandle.resetGame();

    }
}
