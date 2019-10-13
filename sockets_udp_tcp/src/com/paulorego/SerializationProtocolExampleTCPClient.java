package com.paulorego;

import java.io.*;
import java.net.*;

class SerializationProtocolExampleTCPClient {
	public static void main(String argv[]) {

		PessoaSerializationProtocol p1 = new PessoaSerializationProtocol("Joao da Silva", "Quixada", 999881122, 2012);
		
		System.out.println("Pessoa p1: " + p1.toString());
		Socket clientSocket;
		try {
			clientSocket = new Socket("localhost", 6789);
			ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());
			outToServer.writeObject(p1);
			PessoaSerializationProtocol p2 = (PessoaSerializationProtocol) inFromServer.readObject();
			System.out.println("Pessoa p2: " + p2.toString());
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}