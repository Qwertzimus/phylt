package org.qwertzimus.phyltoisus.network;

import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server1 {
	public static boolean isRunning;
	ConnectionReceiver connectionReceiver;
	public static int port = 1337;
	static ServerSocket serverSocket;
	static List<Socket> clientSockets;
	static List<DataPackage> packagesToSend;
	public Server1() {
		isRunning=true;
		clientSockets=new ArrayList<Socket>();
		packagesToSend=new ArrayList<DataPackage>();
		try {
			serverSocket = new ServerSocket(port);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void run(){
		connectionReceiver=new ConnectionReceiver();
		connectionReceiver.start();
		while(isRunning){
			
			try{
				Thread.sleep(1);
			}catch(Exception e){
				
			}
		}
	}
	class ConnectionReceiver extends Thread {

		public ConnectionReceiver() {
			
		}

		public void run() {
			try {
				clientSockets.add(serverSocket.accept());
				
			} catch (Exception e) {

			}
		}
	}
}
