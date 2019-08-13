/**
 * 
 */
package com.pedroalmir.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author PedroAlmir
 *
 */
public class TCPClient extends Client {
	
	/** TCP Socket */
	private Socket socket;
	
	/**
	 * @param ip
	 * @param port
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public TCPClient(String ip, int port) throws UnknownHostException, IOException {
		super(ip, port);
		this.socket = new Socket(this.ip, this.port);
	}
	
	/**
	 * @param message
	 * @return response from servers
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public String sendMessage(String message) throws UnknownHostException, IOException{
		DataOutputStream outToServer = new DataOutputStream(this.socket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

		outToServer.writeBytes(message + '\n');
		
		return inFromServer.readLine();
	}
	
	/**
	 * @throws IOException
	 */
	public void closeConnection() throws IOException{
		this.socket.close();
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}
}
