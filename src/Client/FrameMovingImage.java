package Client;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

class FrameMovingImage extends JPanel implements Runnable {
        private Image img;
        private int i = 1;
        private int sleep = 25;
        private int sx = 73, sy=0;
 
        public FrameMovingImage() {
        		img = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("../img/starDdong.png"));
        
        }
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if(img!=null) g.drawImage(img, sx, sy, this);
        }
 
        @Override
        public void run() {
            try {
                do {
                    Thread.sleep(sleep);
                    switch (i) {
                    case 1: sy+=2; if(sy>791) i=2; break;
                    case 2: sx+=2; if(sx>1420) i=3;  break;
                    case 3: sy-=2; if(sy<53) i=4; break;
                    case 4: sx-=2; if(sx<77) i=1; break;
                    }
                    repaint();
                } while (true);
            } catch (Exception e) {
                System.out.println("무슨 에런지참. ");
            }
        }
 
}