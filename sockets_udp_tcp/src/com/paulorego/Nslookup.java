package com.paulorego;

import java.net.*;

public class Nslookup {
	public static void main(String[] args) {
		InetAddress ip;
		try {
			ip = InetAddress.getByName("globo.com");
			System.out.println(ip.getHostAddress());
		} catch (UnknownHostException e) {
			System.out.println("Endereço desconhecido");
		}
	}
}
