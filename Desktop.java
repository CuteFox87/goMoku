import javax.swing.*;
import java.awt.*;

public class MainWindow {
    public static void main(String[] args) {
        // Create the main JFrame
        JFrame mainFrame = new JFrame("Main Window");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(900, 900);
        mainFrame.setLayout(new BorderLayout());

        // Create a JDesktopPane to hold internal frames
        JDesktopPane desktop = new JDesktopPane();
        mainFrame.add(desktop, BorderLayout.CENTER);

        // Create the first JInternalFrame
        JInternalFrame internalFrame1 = new JInternalFrame("First Internal Frame", true, true, true, true);
        internalFrame1.setSize(300, 300);
        internalFrame1.setLocation(50, 50);
        internalFrame1.setVisible(true);

        // Add content to the first internal frame
        JPanel panel1 = new tictactoeJPanel();
        internalFrame1.add(panel1); // Add the panel to the internal frame

        // Create the second JInternalFrame
        JInternalFrame internalFrame2 = new JInternalFrame("Second Internal Frame", true, true, true, true);
        internalFrame2.setSize(300, 300);
        internalFrame2.setLocation(400, 50);
        internalFrame2.setVisible(true);

        // Add content to the second internal frame
        JPanel panel2 = new JPanel();
        panel2.add(new JLabel("This is the second internal frame."));
        internalFrame2.add(panel2); // Add the panel to the internal frame

        // Add the internal frames to the desktop pane
        desktop.add(internalFrame1);
        desktop.add(internalFrame2);

        // Add a text panel at the bottom
        JPanel textPanel = new JPanel();
        textPanel.add(new JLabel("Welcome to the internal frames demo!"));
        mainFrame.add(textPanel, BorderLayout.SOUTH);

        // Make the main frame visible
        mainFrame.setVisible(true);
    }
}
