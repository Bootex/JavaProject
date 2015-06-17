package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerSubmit implements Runnable{

	protected Socket socket;
		public ServerSubmit(Socket socket){
			this.socket = socket;
		}

		   protected DataInputStream dataIn;
		   protected DataOutputStream dataOut;
		   protected Thread listener;

		   public synchronized void init() {
			      if (listener == null) {
			         try {
			         // 소켓으로부터 입력스트림을 획득.
			            dataIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			            // 소켓으로부터 출력스트림을 획득.
			            dataOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			            // listener 스레드를 생성하고 시작.
			            listener = new Thread(this);
			            listener.start();
			         } catch (IOException ignored) {
			         }
			      }

			   }
		   /**
		    * 메소드 : stop() 
		    * listener 쓰레드에 인터럽트를 걸고 
		    * dataOut 스트림을 닫는 역할.
		    */
		   public synchronized void stop() {
		      if (listener != null) {
		         try {
		            if (listener != Thread.currentThread())
		               listener.interrupt();
		            listener = null;
		            dataOut.close();
		         }
		          catch (IOException ignored) {
		         }
		      }
		   }
		   // 메소드 : run() 
		   // 쓰레드의 실행이 가장 먼저 시작되는 부분

		   public void run() {      
			      try {         
			         while (!Thread.interrupted()) {
			            String message = dataIn.readUTF();
			            try {
			               
			      // 이 프로그램에서는 생성된 소켓을 가지고 스트림을 구성하였으며 
			      // 클라이언트 에서 보내어진 자료를 한 줄씩 읽어 들이는 작업 수행.
			      // 구분자 “|”를 가지는 StringTokenizer형 객체를 생성합니다.
			         StringTokenizer stk = new StringTokenizer(message, "|");
			      // nextToken() 메소드를 이용해 파싱한 토큰으로 각 변수에 대입
			         String rest_name = stk.nextToken();
			         String tableNum= stk.nextToken();
			         String member= stk.nextToken();
			         String menu= stk.nextToken();
			         String time= stk.nextToken();
			         String memo= stk.nextToken();
					        
			      // 서버에 클라이언트가 보내온 정보를 화면에 출력.
			      // 화면에 출력할 때마다 새로운 Date 클래스를 생성하여 클라이언트가 정보를 보낸 시간을 동시에 출력 시키는 역할 수행
			         System.out.println("name : "+rest_name +
			        		 "pwd : " +tableNum+
			        		 "member:"+member+
			        		 "menu:" + menu+
			        		 "time:" + time +
			        		 "memo:" + memo);

			            } catch (NoSuchElementException e) {
			               stop();
			         }
			      }
			   } 
			   catch (EOFException ignored) {
			         System.out.println( "접속을 종료하셨습니다.");
			   }
			   catch (IOException ie) {
			         if (listener == Thread.currentThread())
			            ie.printStackTrace();
			   } finally {
			         stop();
			      }
			   }

}
