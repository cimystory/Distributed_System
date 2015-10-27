package project2task2;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/**
 * A UDP client class enable to ask for retransmission after timeout
 * 
 * Created by Gao on 9/10/2015.
 */
public class UDPClientWithReliability {
    public static void main(String args[]){
        String location;

        // Window display for information input and output
        System.out.println("Enter city name and we will find its coordinates");
        // Get the input city string
        Scanner reader = new Scanner(System.in);
        String city = reader.nextLine();
        System.out.println(city);

        location = getLocation(city);
        System.out.println(location);

    }

    /**
     * This function creates request package, sends to the server and handles the reply
     * @param city
     * @return
     */
    static String getLocation (String city) {
        String location = null;
        DatagramSocket aSocket = null;

        try {
            // use local host as the server IP
            InetAddress hostIP = InetAddress.getLocalHost();
            // args give message contents and destination hostname
            int serverPort = 6789;

            // create a socket
            aSocket = new DatagramSocket();
            // generate request
            DatagramPacket request = new DatagramPacket(city.getBytes(), city.getBytes().length, hostIP, serverPort);
            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);

            // keep asking for retransmission if unsuccessful transmission happens and wait longer than timeout
            while (location == null) {
                aSocket.send(request);
                try {
                    aSocket.setSoTimeout(1000);
                    aSocket.receive(reply);
                    location = new String(reply.getData());
                } catch (Exception e) {
                    location = null;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (aSocket != null) aSocket.close();
        }
        return location;
    }
}
