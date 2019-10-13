package com.paulorego;

import java.io.*;
import java.net.*;

class SimpleUDPServer {
	public static void main(String args[]) {
		DatagramSocket serverSocket = null;
		try {
			serverSocket = new DatagramSocket(6789);
			System.out.println("Servidor em execução!");
			byte[] receiveData = new byte[1024];
			int id = 0;
			while (true) {
				id++;
				System.out.println("Esperando Msg " + id + " ...");
				DatagramPacket request = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(request);

				String sentence = new String(request.getData(), 0, request.getLength());
				System.out.println("Cliente: " + request.getAddress().getHostAddress() + " - Porta: " + request.getPort());
				System.out.println("Msg: " + sentence);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null) serverSocket.close();
		}
	}
}