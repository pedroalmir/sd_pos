package com.pedroalmir.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author PedroAlmir
 */
public class TCPServer extends Server {
	
	/** Connection Socket - TCP */
	private Socket connectionSocket;
	
	@Override
	@SuppressWarnings("resource")
	public void start(int port) throws IOException {
		this.connectionSocket = new ServerSocket(port).accept();
		
		while(!this.closeFlag){
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			
			String clientRequest = inFromClient.readLine();
			
			if(clientRequest.equalsIgnoreCase("\\uptime")){
				outToClient.writeBytes(this.getUpTime());
			}else if(clientRequest.equalsIgnoreCase("\\reqnum")){
				outToClient.writeBytes(String.valueOf(this.getReqNum()));
			}
		}
		this.connectionSocket.close();
	}
}
