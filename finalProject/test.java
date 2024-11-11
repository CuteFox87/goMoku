import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientPropertyDemo extends JFrame implements ActionListener {
    private JButton[][] buttons;

    public ClientPropertyDemo() {
        setTitle("Client Property Demo");
        setSize(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        buttons = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].putClientProperty("Player", "0"); // Default state
                buttons[i][j].addActionListener(this);
                add(buttons[i][j]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String player = (String) button.getClientProperty("Player");

        if ("0".equals(player)) {
            button.putClientProperty("Player", "1");
            button.setText("X"); // Example of marking the button
        } else {
            System.out.println("This button is already clicked by Player " + player);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClientPropertyDemo demo = new ClientPropertyDemo();
            demo.setVisible(true);
        });
    }
}
