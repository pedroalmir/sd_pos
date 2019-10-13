package com.paulorego;

import java.io.*;
import java.net.*;

class SimpleUDPClient {
	public static void main(String args[]) {
		DatagramSocket clientSocket = null;
		try {
			clientSocket = new DatagramSocket();
			byte[] sendArray = "Minha mensagem!".getBytes();
			InetAddress IpServidor = InetAddress.getByName("127.0.0.1");
			int port = 6789;

			DatagramPacket sendPacket = new DatagramPacket(sendArray, sendArray.length, IpServidor, port);
			clientSocket.send(sendPacket);

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (clientSocket != null) clientSocket.close();
		}
	}
}