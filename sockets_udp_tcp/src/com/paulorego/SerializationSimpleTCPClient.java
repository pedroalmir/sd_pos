package com.paulorego;

import java.io.*;
import java.net.*;

class SerializationSimpleTCPClient {
	public static void main(String argv[]) {

		Pessoa p1 = new Pessoa("Joao da Silva", "Quixada", 999881122, 2012);
		System.out.println("Pessoa p1: " + p1.toString());
		
		Socket clientSocket;
		try {
			clientSocket = new Socket("localhost", 6789);
			ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());
			outToServer.writeObject(p1);
			
			FileOutputStream fos = new FileOutputStream("objserializado.data");
			ObjectOutputStream outToFile = new ObjectOutputStream(fos);
			outToFile.writeObject(p1);
			outToFile.close();
			fos.close();
			
			Object object = inFromServer.readObject();
			if(object instanceof Pessoa){
				Pessoa p2 = (Pessoa) object;
				System.out.println("Pessoa p2: " + p2.toString());
			}
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}