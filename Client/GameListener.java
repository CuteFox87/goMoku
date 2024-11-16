package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameListener extends MouseAdapter implements ActionListener {
    
    public GameUI gameUI;
    public JTextField nameIn;
    public ClientGameHandle clientGameHandle;
    int x, y;

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "login" -> {
                String name = nameIn.getText().trim();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter your name");
                    return;
                }
                clientGameHandle.username = name;
                gameUI.initGameUI();
                gameUI.showGameUI();
                gameUI.hideLoginUI();
            }
            case "Logout" -> {
                clientGameHandle.username = null;
                gameUI.showLoginUI();
                gameUI.hideGameUI();
            }
            case "Local PVP" -> {
                clientGameHandle.Online = 0;
                clientGameHandle.AImode = 0;
                clientGameHandle.GameStart();
            }
            case "Local PVE" -> {
                clientGameHandle.Online = 0;
                clientGameHandle.AImode = 1;
                clientGameHandle.GameStart();
            }
            case "Online PVP" -> {
                clientGameHandle.Online = 1;
                clientGameHandle.AImode = 0;
                clientGameHandle.GameStart();
            }
            
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e){
        System.out.println(x + " " + y);

    }
}
