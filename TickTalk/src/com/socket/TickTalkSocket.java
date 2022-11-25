package com.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.dispose.Dispose;
import com.entity.CommonVo;

public class TickTalkSocket {
	CommonVo commonVo = new CommonVo();
	ObjectOutputStream oos;
	ObjectInputStream ois;
	public static void main(String[] args) {
		System.out.println("服务端已启动");
		new TickTalkSocket();
		
	}
	public TickTalkSocket(){
		new server().start();
	}
	private class server extends Thread{
		private ServerSocket  serverSocket= null;
		private Socket client = null;
		public server() {
			try {
				
				serverSocket = new ServerSocket(5000);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			while(this.isAlive()) {
				try {
					client = serverSocket.accept();
					System.out.println("已连接");
				         if(client!=null) {

						     ois = new ObjectInputStream(client.getInputStream());
						     oos = new ObjectOutputStream(client.getOutputStream());
						     System.out.println("读数据");
						     
						     commonVo = (CommonVo) ois.readObject();
						     if(commonVo!=null) {
						    	 System.out.println("写数据");
			
						    	 oos.writeObject(new Dispose().dispose(commonVo));
						    	 oos.flush();
						     }
					     }
				         
		   } catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}finally {
						try {
							client.close();
						} catch (Exception e2) {
							// TODO: handle exception
							e2.printStackTrace();
						}
					}
					
				}
			}
		}
	}
