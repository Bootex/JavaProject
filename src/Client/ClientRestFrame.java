package Client;

import java.awt.*;
import java.awt.event.*;

import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import javax.swing.*;


public class ClientRestFrame extends JFrame implements ActionListener{
	
	private int posXpanSeat=220,posYpanSeat=80;
	private FrameMakeTable[] pan = new FrameMakeTable[20];

	private JComboBox tableSelect;
	private JComboBox memberSelect;
	
	private JTextField menuText;
	private JTextField timeText;
	
	private JTextArea memoArea;
	
	private JButton submitBt;

	private Socket socket;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    
	public ClientRestFrame() {
    
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(1600, 900);
        setTitle("식당 예약 프로그램");
        setLayout(null);
     
        // 내 윈도우 화면
        Dimension frameSize = this.getSize();
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2, (windowSize.height - frameSize.height) / 2);
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1600, 900);
        layeredPane.setLayout(null);
 
        // 배경
        JPanel backGround = new FrameBackGround();
        backGround.setLayout(null);
        backGround.setBounds(0, -30, 1600, 900);

        //선택창
    	tableSelect=new JComboBox();
    	memberSelect=new JComboBox();
    
    menuText= new JTextField();
    	timeText=new JTextField();
    	memoArea=new JTextArea();
    	
    	submitBt=new JButton();
    	
    	
        for(int i=1;i<=20;i++)//반복문으로 테이블 번호값 반복
        	tableSelect.addItem(i);
    
        for(int i=1;i<=5;i++)//반복문으로 멤버 번호값 반복
        	memberSelect.addItem(i);
        
        memberSelect.addItem("기타");
     
        memberSelect.setBounds(900,150, 80, 30);
        tableSelect.setBounds(900, 190, 80, 30);
        
        timeText.setBounds(900, 230, 150, 30);
        menuText.setBounds(900, 270, 150, 30);  
        memoArea.setBounds(900,310,300,200);
        submitBt.setBounds(920,530,100,30);
        submitBt.setText("Submit");
        submitBt.setFont(new Font("SansSerif",Font.BOLD,18));
        
        layeredPane.add(memberSelect);
        layeredPane.add(tableSelect);
     layeredPane.add(menuText);   
        layeredPane.add(timeText);
        layeredPane.add(memoArea);
       layeredPane.add(submitBt);
       
        // 시계
        FrameImgClock imgClock = new FrameImgClock();
        imgClock.setLayout(null);
        imgClock.setBounds(15, 20, 179, 149);
        imgClock.setOpaque(false); // 배경이 투명처리
     new Thread(imgClock).start();
     
     // 시계 글씨
        FrameClockMessage clockMessage = new FrameClockMessage();
        clockMessage.setBounds(80, 53, 100, 100);
        clockMessage.setOpaque(false);
     new Thread(clockMessage).start();
 
     
     // 움직이는 광원처리
        FrameMovingImage myStarPanel = new FrameMovingImage();
        myStarPanel.setLayout(null);
        myStarPanel.setBounds(0, -30, 1600, 900);
        myStarPanel.setOpaque(false);
        new Thread(myStarPanel).start();

        JPanel seat50 = new JPanel();
        seat50.setLayout(null);
        seat50.setOpaque(false);
        seat50.setBounds(525, 309, 1368, 686);
        for (int seat = 0; seat < 20; seat++) {
            pan[seat] = new FrameMakeTable(seat);
            if (seat % 4 == 0 && seat != 0) {
                posXpanSeat = 220;
                posYpanSeat += 140;
            }
            pan[seat].setBounds(posXpanSeat, posYpanSeat, 99, 99);
            posXpanSeat += 135;
            layeredPane.add(pan[seat]);
        }
         
         
        //TODO 여기도 계속 잡아줌
        // 최종 삽입
        layeredPane.add(backGround, new Integer(0));
        layeredPane.add(imgClock, new Integer(4));
        layeredPane.add(clockMessage, new Integer(5));
        layeredPane.add(myStarPanel, new Integer(3));
        add(layeredPane);
        
        submitBt.addActionListener(this);
        
        try{
        	socket = new Socket("127.0.0.1",9999);
        	dataIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        	dataOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch(IOException ie){
        	stop();
        }
    } 
	
	public void stop(){
    	try{
    		dataIn.close();
    		dataOut.close();
    		socket.close();
    	}
    	catch(IOException e){
    		System.out.println("STOP");
    	}
    }
    
	public void actionPerformed(ActionEvent e) {
        if (submitBt.getText().equals("Submit")) {
        	try{
        	dataOut.writeUTF(("2"+"|"+tableSelect.getSelectedIndex()+1)+
        			"|"+memberSelect.getSelectedIndex()+
        			"|"+menuText.getText()+
        			"|"+timeText.getText()+
        			"|"+memoArea.getText());
        	dataOut.flush();
        	}catch(Exception ie){		
        		System.out.println("IO ERROR!");
        	}
        	
        	JOptionPane.showMessageDialog(null, "전송하였습니다.");	    // 메시지를 날린다.
        	}
            
        
    }
}