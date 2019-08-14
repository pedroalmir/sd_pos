/**
 * 
 */
package com.pedroalmir;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.pedroalmir.client.Client;
import com.pedroalmir.client.TCPClient;
import com.pedroalmir.client.UDPClient;
import com.pedroalmir.server.Server;
import com.pedroalmir.server.TCPServer;
import com.pedroalmir.server.UDPServer;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import net.sourceforge.argparse4j.inf.Subparsers;

/**
 * Main class of Sockets UDP/TCP Study
 * 
 * @course Sistemas Distribuídos e Redes de Computadores
 * @professor Paulo Antonio Leal Rego
 * @author Pedro Almir Martins de Oliveira
 * @date 13/08/2019
 * 
 * How to use this program:
 * java -jar sockets-sd-1.0.3.jar server -p [UDP or TCP] -port 9876
 * java -jar sockets-sd-1.0.3.jar client -p [UDP or TCP] -ip 127.0.0.1 -port 9876
 *    \\uptime to get server connection time
 *    \\reqnum to get the number of requests received by the server
 *    \\close to close the client
 *    
 * Attention: the servers TCP/UDP here accept only one connection at a time
 * because it was not a requirement to work with threads.
 * 
 * @Bugs report to pedro.oliveira@ifma.edu.br
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/* Args util */
		ArgumentParser parser = ArgumentParsers.newArgumentParser("sockets_sd");
		Subparsers subparsers = parser.addSubparsers().dest("mode");
		
		Subparser serverParser = subparsers.addParser("server").help("Server Mode");
		serverParser.addArgument("-p", "--protocol").required(true).setDefault("UDP").help("Transport protocol: TCP or UDP");
		serverParser.addArgument("-port").type(Integer.class).required(true).setDefault(49152).help("Server Port");
		
		Subparser clientParser = subparsers.addParser("client").help("Client Mode");
		clientParser.addArgument("-p", "--protocol").required(true).setDefault("UDP").help("Transport protocol: TCP or UDP");
		clientParser.addArgument("-ip").required(true).setDefault("127.0.0.1").help("Server IP");
		clientParser.addArgument("-port").type(Integer.class).required(true).setDefault(49152).help("Server Port");
		
		try {
            Namespace namespace = parser.parseArgs(args);
            checkArgs(namespace);
            String protocol = namespace.getString("protocol");
            Integer port = namespace.getInt("port");
            
            if(namespace.getString("mode").equalsIgnoreCase("server")){
            	System.out.println(String.format("Creating a %s socket on server:port %s:%d;", protocol, getLocalIP(), port));
            	
            	Server server = null;
            	if(protocol.equalsIgnoreCase("TCP")){
            		server = new TCPServer();
            	}else{
            		server = new UDPServer();
            	}
            	
            	server.start(port);
            }else if(namespace.getString("mode").equalsIgnoreCase("client")){
            	String ip = namespace.getString("ip");
            	System.out.println(String.format("Client connecting using %s with server:port %s:%d;", protocol, ip, port));
            	BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
            	
            	Client client = null;
            	if(protocol.equalsIgnoreCase("TCP")){
            		client = new TCPClient(ip, port);
            	}else{
            		client = new UDPClient(ip, port);
            	}
            	
            	while(true){
            		System.out.println("Options:\n\t\\uptime to get server connection time, "
            				+ "\n\t\\reqnum to get the number of requests received by the server or "
            				+ "\n\t\\close to close the client. Don't forget to press Enter to confirm.\n");
            		
            		boolean terminate = false;
            		System.out.print("Your choice: ");
            		String answer = systemIn.readLine();
            		
            		if(answer.equalsIgnoreCase("\\uptime") || answer.equalsIgnoreCase("\\reqnum")){
            			String response = client.sendMessage(answer.toLowerCase().trim(), true);
            			System.out.println("Server response: " + response.trim());
            			System.out.println("***********************************\n");
            		}else if(answer.equalsIgnoreCase("\\close")){
            			client.sendMessage("\\close", false);
            			client.closeConnection();
            			terminate = true;
            		}
            		
            		if(terminate){ break; }
            	}
            	System.out.println("See you later!");
            }
        } catch (ArgumentParserException e) {
            parser.handleError(e);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}
	

	/**
	 * @param args
	 * @throws Exception
	 */
	private static void checkArgs(Namespace args) throws Exception{
        if(args.getString("mode").equalsIgnoreCase("client") && !args.getString("ip").matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$")){
            throw new Exception("IP must be formatted as [0-255].[0-255].[0-255].[0-255]");
        }
        if(args.getInt("port") < 0 || args.getInt("port") > 65535){
        	throw new Exception("Port value must be in the range of 0 - 65535");
        }
    }
	
	public static String getLocalIP() throws SocketException, UnknownHostException{
		/*try (final DatagramSocket socket = new DatagramSocket()) {
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			return socket.getLocalAddress().getHostAddress();
		}*/
		return "127.0.0.1";
	}

}
