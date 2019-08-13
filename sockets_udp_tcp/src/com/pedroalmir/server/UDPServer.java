/**
 * 
 */
package com.pedroalmir.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author PedroAlmir
 */
public class UDPServer extends Server {
	
	/** Server Socket - UDP */
	private DatagramSocket serverSocket; 
	
	@Override
	public void start(int port) throws IOException {
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		this.serverSocket = new DatagramSocket(port);
		
		while(!this.closeFlag){
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);

			String clientRequest = new String(receivePacket.getData());

			if(clientRequest.equalsIgnoreCase("\\uptime")){
				sendData = this.getUpTime().getBytes();
			}else if(clientRequest.equalsIgnoreCase("\\reqnum")){
				sendData = String.valueOf(this.getReqNum()).getBytes();
			}

			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
			serverSocket.send(sendPacket);
		}
		this.serverSocket.close();
	}

	@Override
	public void stop() {
		this.closeFlag = true;
		System.out.println("Closing the server! See you!");
	}
}
