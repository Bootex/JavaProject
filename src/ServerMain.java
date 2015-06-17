import java.io.*;
import java.net.*;

import server.ServerCheckUser;


public class ServerMain {
	public static void main(String[] args) throws IOException{
		
		int port = 9999;
		
		ServerSocket serverS= new ServerSocket(port);
		System.out.println("Restaurant Started..!!!");
		boolean CheckState  = false;
		while(true){
			Socket client = serverS.accept();
			System.out.println("Accepted From:" + client.getInetAddress());


			ServerCheckUser check_user = new ServerCheckUser(client);
			check_user.init();
			//핸들러
			//핸들러 시작
			
		}
	}

}