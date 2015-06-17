import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import server.ServerCheckUser;
import server.ServerSubmit;


public class ServerMain{
	public static void main(String[] args) throws IOException{
		
		int port = 9999;
		
		ServerSocket serverS= new ServerSocket(port);
		System.out.println("Restaurant Started..!!!");
		
		while(true){
			Socket client = serverS.accept();
			
			System.out.println("Accepted From login:" + client.getInetAddress());
			
			ServerCheckUser check_user = new ServerCheckUser(client);
			check_user.init();

			
			//핸들러
			//핸들러 시작
			
		}
	}
}