package com.paulorego;

import java.io.*;
import java.net.*;

class UDPClient {
	public static void main(String args[]){
		DatagramSocket clientSocket = null;
		String msg = null;
		String ipDoServidor = "127.0.0.1";
		int port = 6789;
		
		try {
			// Digitar mensagem usando o teclado
			BufferedReader entradaDoUsuario = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Digite a msg a ser enviada: ");
			msg = entradaDoUsuario.readLine();
			
			clientSocket = new DatagramSocket();
			byte[] sendArray = msg.getBytes();
			InetAddress IpServidor = InetAddress.getByName(ipDoServidor);
			
			DatagramPacket sendPacket = new DatagramPacket(sendArray, sendArray.length, IpServidor, port);
			clientSocket.send(sendPacket);
			System.out.println("Msg '" + msg + "' enviada para o servidor " + ipDoServidor + " na porta " + port);
			
			// Se prepara para receber a resposta
			byte[] receiveArray = new byte[1000];
			DatagramPacket receivePacket = new DatagramPacket(receiveArray, receiveArray.length);
			clientSocket.receive(receivePacket);
			String resposta = new String(receivePacket.getData(), 0, receivePacket.getLength());
			System.out.println("Resposta do servidor: " + resposta);
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (clientSocket != null)
				clientSocket.close();
		}
	}
}