package com.paulorego;

import java.io.IOException;
import java.net.*;

class UDPServer {
	public static void main(String args[]) throws Exception {
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

				// Trata os dados recebidos no pacote
				String msg = new String(request.getData(), 0, request.getLength());
				InetAddress IpDoCliente = request.getAddress();
				int portaDoCliente = request.getPort();

				System.out.println("Cliente: " + IpDoCliente.getHostAddress() + " - Porta: " + portaDoCliente);
				System.out.println("Msg: " + msg);

				// Prepara a resposta para o cliente (Serviço prestado)
				byte[] replyData = new byte[1000];
				String respota = msg.toUpperCase();
				replyData = respota.getBytes();
				DatagramPacket reply = new DatagramPacket(replyData, replyData.length, IpDoCliente, portaDoCliente);
				serverSocket.send(reply);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null)
				serverSocket.close();
		}
	}
}