/**
 * 
 */
package com.pedroalmir.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.pedroalmir.Main;

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
		this.serverSocket = new DatagramSocket(port, InetAddress.getByName(Main.getLocalIP()));
		
		while(!this.closeFlag){
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);

			String clientRequest = new String(receivePacket.getData());
			String response = null;
			
			if(clientRequest != null && clientRequest.trim().equalsIgnoreCase("\\uptime")){
				response = this.getUpTime();
			}else if(clientRequest != null && clientRequest.trim().equalsIgnoreCase("\\reqnum")){
				response = String.valueOf(this.getReqNum());
			}else if(clientRequest != null && clientRequest.trim().equalsIgnoreCase("\\close")){
				sendData = "bye".getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
				serverSocket.send(sendPacket);
				this.serverSocket = new DatagramSocket(port, InetAddress.getByName(Main.getLocalIP()));
				continue;
			}else{
				continue;
			}
			
			sendData = response.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
			serverSocket.send(sendPacket);
			this.plusOneReqNum();
			
			System.out.println(clientRequest.trim() + " " + response);
		}
		this.serverSocket.close();
	}

	@Override
	public void stop() {
		this.closeFlag = true;
		System.out.println("Closing the server! See you!");
	}
}
