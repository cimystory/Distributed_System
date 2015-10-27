package project2task1;

import java.net.*;
import java.io.*;
import java.util.TreeMap;

/**
 * UDP server class to deal with client request
 * @author Gao
 */
public class UDPServer{
    public static void main(String args[]){ 
    	DatagramSocket aSocket = null;
		String city;
		String location;

		// create the tree map storing the cities and locations
		TreeMap<String, String> treeMap = new TreeMap<String, String>();
		treeMap.put("Pittsburgh,PA", "40.440625,-79.995886");
		treeMap.put("New York,NY", "40.7141667,-740063889");
		treeMap.put("Washington,DC", "38.904722,-77016389");
		treeMap.put("Boston,MA", "42.358056,-71.063611");

		try{
			// create socket at agreed port
			aSocket = new DatagramSocket(6789);
			
 			while(true){
                                byte[] buffer = new byte[1000];
 				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
  				aSocket.receive(request);

				// handle the request lookup and generate the reply package
				city = new String(request.getData()).trim();
				location = treeMap.get(city);
				DatagramPacket reply;

				if(location != null) {
					System.out.println("Handling request for " + city);
					reply = new DatagramPacket(location.getBytes(), location.getBytes().length,
							request.getAddress(), request.getPort());
				} else {
					System.out.println("Was unable to handle a request for " + city + "'");
					String errorMessage = "Could not resolve '" + city + "'";
					reply = new DatagramPacket(errorMessage.getBytes(), errorMessage.getBytes().length,
							request.getAddress(), request.getPort());
				}
                            aSocket.send(reply);
                        }
		}catch (SocketException e){
            System.out.println("Socket: " + e.getMessage());
		}catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
		}finally {
            if(aSocket != null) aSocket.close();
        }
    }
}
