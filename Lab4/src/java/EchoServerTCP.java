import java.net.*;
import java.io.*;
import java.util.Scanner;

public class EchoServerTCP {

    public static void main(String args[]) {
        Socket clientSocket = null;
        try {
            int serverPort = 7777; // the server port we are using
            
            // Create a new server socket
            ServerSocket listenSocket = new ServerSocket(serverPort);
            
            /*
             * Block waiting for a new connection request from a client.
             * When the request is received, "accept" it, and the rest
             * the tcp protocol handshake will then take place, making 
             * the socket ready for reading and writing.
             */
            clientSocket = listenSocket.accept();
            // If we get here, then we are now connected to a client.
            
            // Set up "in" to read from the client socket
            Scanner in;
            in = new Scanner(clientSocket.getInputStream());
            
            // Set up "out" to write to the client socket
            PrintWriter out;
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
            
            /* 
             * Forever,
             *   read a line from the socket
             *   print it to the console
             *   echo it (i.e. write it) back to the client
             */
            while (true) {
                String data = in.nextLine();
                System.out.println("Echoing: " + data);
                String[] list = data.split(" ");
                System.out.println("List length = " + list.length);
                if (list.length > 0){
                    String fileName = list[1].substring(1);
                    System.out.println("FileName = " + fileName);
                  
                    try {
                        File f = new File(fileName);
                        BufferedReader reader = new BufferedReader(new FileReader(f));  
                        String fileLine = "";
                        out.print("HTTP/1.1 200 OK\n\n");
                        while ((fileLine = reader.readLine()) != null){
                            out.println(fileLine);
                        }
                        out.flush();                        
                    } catch(FileNotFoundException ex){
                        out.print("HTTP/1.1 404 Not Found\n\n");
                        out.flush();
                    }   
                }
                break;
            }           
             
        // Handle exceptions
        } catch (IOException e) {
            System.out.println("IO Exception:" + e.getMessage());
            
        // If quitting (typically by you sending quit signal) clean up sockets
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                // ignore exception on close
            }
        }
    }
}
