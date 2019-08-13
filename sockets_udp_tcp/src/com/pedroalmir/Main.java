/**
 * 
 */
package com.pedroalmir;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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

/**
 * @author PedroAlmir
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/* Args util */
		ArgumentParser parser = ArgumentParsers.newArgumentParser("sockets");
		parser.addArgument("-m", "--mode").required(true).setDefault("Server").help("Define app mode: client or server");
		parser.addArgument("-p", "--protocol").required(true).setDefault("UDP").help("Transport protocol: TCP or UDP");
		parser.addArgument("-ip").required(true).setDefault("127.0.0.1").help("Server IP");
		parser.addArgument("-port").type(Integer.class).required(true).setDefault(49152).help("Server Port");
		
		try {
            Namespace namespace = parser.parseArgs(args);
            checkArgs(namespace);
            String protocol = namespace.getString("protocol"), ip = namespace.getString("ip");
            Integer port = namespace.getInt("port");
            
            if(namespace.getString("mode").equalsIgnoreCase("Server")){
            	System.out.println(String.format("Creating a %s socket on server:port %s:%d;", protocol, ip, port));
            	
            	Server server = null;
            	if(protocol.equalsIgnoreCase("TCP")){
            		server = new TCPServer();
            	}else{
            		server = new UDPServer();
            	}
            	
            	server.start(port);
            }else{
            	System.out.println(String.format("Client connecting using %s with server:port %s:%d;", protocol, ip, port));
            	BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
            	
            	Client client = null;
            	if(protocol.equalsIgnoreCase("TCP")){
            		client = new TCPClient(ip, port);
            	}else{
            		client = new UDPClient(ip, port);
            	}
            	
            	while(true){
            		System.out.println("Options: \\uptime to get server connection time, "
            				+ "\\reqnum to get the number of requests received by the server or "
            				+ "\\close to close the client. Don't forget to press Enter to confirm.");
            		
            		boolean terminate = false;
            		String answer = systemIn.readLine();
            		
            		if(answer.equalsIgnoreCase("\\uptime") || answer.equalsIgnoreCase("\\reqnum")){
            			String response = client.sendMessage(answer.toLowerCase().trim());
            			System.out.println("Server response: " + response);
            		}else if(answer.equalsIgnoreCase("\\close")){
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
        if(!args.getString("ip").matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$")){
            throw new Exception("IP must be formatted as [0-255].[0-255].[0-255].[0-255]");
        }
        if(args.getInt("port") < 0 || args.getInt("port") > 65535){
        	throw new Exception("Port value must be in the range of 0 - 65535");
        }
    }

}
