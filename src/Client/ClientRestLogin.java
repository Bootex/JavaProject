package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
 
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import server.ServerSubmit;

public class ClientRestLogin extends JFrame implements ActionListener{
 
	private BufferedImage img = null;
    private JTextField idTextField;
    private JPasswordField pwField;
    private JButton loginBt;
    private JComboBox restSelect;
    
    private Socket socket;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    
    // 생성자
    public ClientRestLogin() {
        setTitle("식당 예약 프로그램");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        setLayout(null);
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1600, 900);
        layeredPane.setLayout(null);
 
        // 패널1
        // 이미지 받아오기
        try {
            img = ImageIO.read(this.getClass().getResource("/img/login.png"));
        } catch (IOException e) {
            System.out.println("이미지 불러오기 실패");
            System.exit(0);
        }
         
        MyPanel loginPanel = new MyPanel();
        loginPanel.setBounds(0, 0, 1600, 900);
         
        restSelect = new JComboBox();
     restSelect.addItem("나리네");
     restSelect.addItem("은혜네");
     restSelect.addItem("봉춘이네");
     restSelect.addItem("대성식당");
     restSelect.setBounds(151, 269, 80, 30);
     layeredPane.add(restSelect);
     
        // 로그인 필드
        idTextField = new JTextField(15);
        idTextField.setBounds(161, 329, 280, 30);   
        layeredPane.add(idTextField);
        
        idTextField.setOpaque(false);
        idTextField.setForeground(Color.black);
        idTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        idTextField.setFont(new Font("SansSerif",Font.BOLD,18));
        // 패스워드
        pwField = new JPasswordField(15);
        pwField.setBounds(161, 384, 280, 30);
        pwField.setOpaque(false);
        pwField.setForeground(Color.black);
        pwField.setFont(new Font("SansSerif",Font.BOLD,18));
        
        pwField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        layeredPane.add(pwField);
        
       // 로그인버튼 추가
        loginBt = new JButton(new ImageIcon(this.getClass().getResource("../img/btLogin_hud2.png")));
        loginBt.setBounds(321, 439, 104, 48);
        loginBt.setName("Login");
        
        // 버튼 투명처리
        loginBt.setBorderPainted(false);
        loginBt.setFocusPainted(false);
        loginBt.setContentAreaFilled(false);
 
        layeredPane.add(loginBt);
 

        loginBt.addActionListener(this);
     
        
        // 마지막 추가들
        layeredPane.add(loginPanel);
        add(layeredPane);
        setVisible(true);
        
        
        try{
        	socket = new Socket("127.0.0.1",9999);
        	dataIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        	dataOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch(IOException ie){
        	stop();
        }
    }
 
    class MyPanel extends JPanel {
        public void paint(Graphics g) {
            g.drawImage(img, 0, 0, null);
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
    
    public void actionPerformed(ActionEvent event) {
    	if(loginBt.getName().equals("Login")){
    String id = idTextField.getText();
        char[] pass = pwField.getPassword();
        String password = new String(pass);
 
        if (id.equals("") || password.equals("")) {
            // 메시지를 날린다.
        			
        	if(id.equals("")){
        		JOptionPane.showMessageDialog(null, "아이디를 입력하세요");	
        		idTextField.requestFocus();
        	}
        	else{
        		JOptionPane.showMessageDialog(null, "패스워드를 입력하세요");
        		pwField.requestFocus();
            	
        	}
            
        } else {
 
            // 로그인 참 거짓 여부를 판단
            
        	String response = new String();
        	try{
        		dataOut.writeUTF(("Login"+"|"+restSelect.getSelectedIndex()+1)+"|"+idTextField.getText()+"|"+pwField.getText());
        		dataOut.flush();	
        	}
        	catch(Exception ee){
            	System.out.println("Send Info Error!");
            }
        	
            
        	try{
        		response=dataIn.readUTF();
        		
            	}catch(Exception ee){
            	System.out.println("Receive Response Error!");
        	}
 
            if (response.equals("correct")) {
                // 로그인 성공일 경우
                JOptionPane.showMessageDialog(null, "로그인 성공");
                try{
                System.out.println(restSelect.getSelectedIndex()+"|"+idTextField.getText()+"|"+pwField.getText());
                
                }catch(Exception ee){
                	System.out.println("ERROR");
                }
                ClientRestFrame frame = new ClientRestFrame();
                
    		   this.hide();
                
            } else {
                // 로그인 실패일 경우
                JOptionPane.showMessageDialog(null, "로그인 실패");
            }
 
        }
       }
    }
}