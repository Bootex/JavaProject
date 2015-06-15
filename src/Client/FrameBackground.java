package Client;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

class FrameBackGround extends JPanel {
    Image image;
    public FrameBackGround() {
        image = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("../img/mainHud_back.png"));
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, this);
    }
}