/**
 * 
 */
package com.pedroalmir.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author PedroAlmir
 *
 */
public class UDPClient extends Client {

	/** UDP Socket */
	private DatagramSocket socket;
	
	/**
	 * @param ip
	 * @param port
	 * @throws SocketException
	 */
	public UDPClient(String ip, int port) throws SocketException {
		super(ip, port);
		this.socket = new DatagramSocket();
	}

	@Override
	public String sendMessage(String message, boolean wait) throws UnknownHostException, IOException {
		byte[] sendData = new byte[1024]; 
		byte[] receiveData = new byte[1024];
		sendData = message.getBytes();

		InetAddress IPAddress = InetAddress.getByName(this.ip);
		
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, this.port);
		this.socket.send(sendPacket);
		
		if(wait){
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			this.socket.receive(receivePacket);
			
			return new String(receivePacket.getData());
		}
		
		return null;
	}

	@Override
	public void closeConnection() throws IOException {
		this.socket.close();
	}

}
