package Client;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
 
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
 
public class FrameMakeTable extends JPanel {
    private BufferedImage img = null;
    JLabel[] label = new JLabel[4];
    private int numSeat;
     
     
    public FrameMakeTable(int numSeat) {
        this.numSeat = numSeat;
        img("tableEmpty3");
        setLayout(null);
 
        JPanel panImg = new InnerPanel();
        panImg.setBounds(0, 0, 99, 99);
        panImg.setOpaque(false);
                 
        //상태정보 패널
        JPanel panContent = new JPanel();
        panContent.setLayout(null);
        panContent.setBounds(0, 0, 99, 99);
        int posLabel = 15;
        for (int i = 0; i < 4; i++) {
            if (i == 0)
                label[i] = new JLabel((numSeat + 1) + ". 빈자리");
            else
                label[i] = new JLabel("");
 
            label[i].setBounds(20, posLabel, 80, 15);
            posLabel += 16;
            label[i].setForeground(new Color(36, 205, 198));
            label[i].setFont(new Font("배달의민족 한나", 1, 12));
            panContent.add(label[i]);
        }
        panContent.setOpaque(false);
         
         
        //제이레이어패널
        JLayeredPane panLayered = new JLayeredPane();
        panLayered.setBounds(0, 0, 1600, 900);
        panLayered.setLayout(null);
        panLayered.setOpaque(false);
        panLayered.add(panImg, new Integer(0), 0);
        panLayered.add(panContent, new Integer(1), 0);
        add(panLayered);
         
        setVisible(true);
        setOpaque(false);
        setFocusable(true);
    }
 
    private class InnerPanel extends JPanel {
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(img, 0, 0, null);
        }
    }
 
    public void img(String filename) {
        // 이미지 받아오기 - gameOn, gameOff (로그인, 로그오프)
        try {
        	
            img = ImageIO.read(this.getClass().getResource("../img/" + filename + ".png"));
        } catch (IOException e) {
            System.out.println("이미지 불러오기 실패!");
            System.exit(0);
        }
        repaint();
    }
}