package com.pedroalmir.client;

import java.io.IOException;
import java.net.UnknownHostException;

public abstract class Client {
	
	/** Server IP */
	protected String ip;
	/** Server Port */
	protected int port;
	
	/**
	 * @param ip
	 * @param port
	 */
	public Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	/**
	 * Send a Message
	 * 
	 * @param message
	 * @param wait
	 * @return response from server
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public abstract String sendMessage(String message, boolean wait) throws UnknownHostException, IOException;
	
	/**
	 * Close Connection
	 * 
	 * @throws IOException
	 */
	public abstract void closeConnection() throws IOException;
}
