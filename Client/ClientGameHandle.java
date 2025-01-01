package Client;

public class ClientGameHandle {
    public String username;
    public int Online;
    public int AImode;
    public connectHandle connect;
    goMokuIconPanel goMokuIconPanel;
    
    public ClientGameHandle(goMokuIconPanel goMokuIconPanel) {
        this.goMokuIconPanel = goMokuIconPanel;
    }

    public void GameStart() {
        if (Online == 0) {
            if (AImode == 0) {
                System.out.println("Local PVP");
                goMokuIconPanel.setGameMode(GameMode.LOCAL_PVP);
            } else {
                System.out.println("Local PVE");
                goMokuIconPanel.setGameMode(GameMode.LOCAL_PVE);
            }
        } else {
            System.out.println("Online PVP");
            connect = new connectHandle("localhost", 8888);
        }

    }
}
