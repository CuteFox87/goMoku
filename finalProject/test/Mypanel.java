import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
 
public class Mypanel extends JPanel implements GoData{
    GameListener gameListener ;
    @Override
    public void paint(Graphics g) {
        super.paint(g);
 
        BufferedImage img = new BufferedImage(X + COL * SIZE + SIZE, Y + ROW * SIZE + SIZE, 2);
        Graphics imgG = img.getGraphics();
 
        imgG.setColor(Color.white);
 
        GoData.drawChessPad(imgG);
        GoData.drawChesses(imgG, gameListener.chessList);
 
        g.drawImage(img, 0, 0, null);
    }
}