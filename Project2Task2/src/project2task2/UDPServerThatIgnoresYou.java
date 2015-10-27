package project2task2;

import java.io.IOException;
import java.net.*;
import java.util.Random;
import java.util.TreeMap;

/**
 * A highly unreliable UDP server which throws 90% of client requests
 * 
 * Created by Gao on 9/10/2015.
 */
public class UDPServerThatIgnoresYou {
    public static void main(String args[]){
            DatagramSocket aSocket = null;
            String city;
            String location;
            Random rnd = new Random();

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
                    // receive request packets from client
                    DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                    aSocket.receive(request);
                    city = new String(request.getData()).trim();

                    // randomly throws request
                    if (rnd.nextInt() < 9) {
                        System.out.println("Got request " + city + " but ignoring it.");
                        continue;
                    } else {
                        // handle the request lookup and generate the reply package
                        location = treeMap.get(city);
                        DatagramPacket reply;

                        if(location != null) {
                            System.out.println("Handling request for " + city);
                            System.out.println(city);
                            System.out.println(location);
                            reply = new DatagramPacket(location.getBytes(), location.getBytes().length,request.getAddress(), request.getPort());
                        } else {
                            System.out.println("Was unable to handle a request for " + city + "'");
                            String errorMessage = "Could not resolve '" + city + "'";
                            reply = new DatagramPacket(errorMessage.getBytes(), errorMessage.getBytes().length,request.getAddress(), request.getPort());
                        }
                        aSocket.send(reply);
                    }
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
