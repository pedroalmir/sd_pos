package com.pedroalmir.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import com.pedroalmir.Main;

/**
 * @author PedroAlmir
 */
public class TCPServer extends Server {
	
	/** Connection Socket - TCP */
	private Socket connectionSocket;
	
	@Override
	@SuppressWarnings("resource")
	public void start(int port) throws IOException {
		ServerSocket serverSocket = new ServerSocket(port, 0, InetAddress.getByName(Main.getLocalIP()));
		this.connectionSocket = serverSocket.accept();
		
		while(!this.closeFlag){
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			
			String clientRequest = null;
			try{
				clientRequest = inFromClient.readLine();
			}catch(SocketException sEX){
				serverSocket.close();
				serverSocket = new ServerSocket(port, 0, InetAddress.getByName(Main.getLocalIP()));
				this.connectionSocket = serverSocket.accept();
				continue;
			}
			
			String response = null;
			
			if(clientRequest != null && clientRequest.trim().equalsIgnoreCase("\\uptime")){
				response = this.getUpTime();
			}else if(clientRequest != null && clientRequest.trim().equalsIgnoreCase("\\reqnum")){
				response = String.valueOf(this.getReqNum());
			}else if(clientRequest != null && clientRequest.trim().equalsIgnoreCase("\\close")){
				serverSocket.close();
				serverSocket = new ServerSocket(port, 0, InetAddress.getByName(Main.getLocalIP()));
				this.connectionSocket = serverSocket.accept();
				continue;
			}else{
				continue;
			}
			
			outToClient.writeBytes(response + "\n");
			this.plusOneReqNum();
			
			System.out.println(clientRequest + " " + response);
		}
		this.connectionSocket.close();
	}
}
