package Client;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;


class FrameImgClock extends JPanel implements Runnable {
    Image img[] = new Image[4];
    int i = 2;
    public FrameImgClock() {
        img[1] = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("../img/cl1.png"));
        img[2] = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("../img/cl2.png"));
        img[3] = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("../img/cl3.png"));
        img[0] = img[1];
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(img[0], 0, 0, this);
    }
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10000);
                switch (i) {
                case 1:
                    img[0] = img[i];
                    i++;
                    repaint();
                    break;
                case 2:
                    img[0] = img[i];
                    i++;
                    repaint();
                    break;
                case 3:
                    img[0] = img[i];
                    i = 1;
                    repaint();
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}