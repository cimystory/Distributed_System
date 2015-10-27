package project2task1;

import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * UDP client class to handle user input and send request to the server
 * @author Gao
 */
public class UDPClient{
    public static void main(String args[]){
		// Window display for information input and output
		System.out.println("Enter city name and we will find its coordinates");

		// Get the input city string
		Scanner reader = new Scanner(System.in);
		String city = reader.nextLine();
		System.out.println(city);

		// args give message contents and destination hostname
		DatagramSocket aSocket = null;
		try {
			aSocket = new DatagramSocket();
			// use local host as the server IP
			InetAddress hostIP = InetAddress.getLocalHost();
			int serverPort = 6789;
			DatagramPacket request = new DatagramPacket(city.getBytes(), city.getBytes().length, hostIP, serverPort);
			aSocket.send(request);

			// construct the reply information
			byte[] buffer = new byte[1000];
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);	
			aSocket.receive(reply);
			System.out.println(new String(reply.getData()));
		}catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		}catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}finally {
			if(aSocket != null) aSocket.close();
		}
	}		      	
}
