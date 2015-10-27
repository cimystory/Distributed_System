package project2task3;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * A TCP client class sending user identification information and location information to the server in a secure way
 *
 * Created by Gao on 9/10/2015.
 */
public class TCPSpyUsingTEAandPasswords {
    public static void main (String args[]) {

        // arguments supply message and hostname
        Socket s = null;

        // read symmetric key from the command line
        System.out.println("Enter symmetric key for TEA (taking first sixteen bytes):");
        Scanner keyReader = new Scanner(System.in);
        String symmetricKey = keyReader.nextLine().trim();

        try {
            int serverPort = 7896;
            s = new Socket("localhost", serverPort);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out =new DataOutputStream(s.getOutputStream());

            // read user id from command line
            System.out.println("Enter your ID: ");
            Scanner idReader = new Scanner(System.in);
            String userId = idReader.nextLine().trim();
            // encrypt user id using TEA
            byte[] encryptedUserId = encryption(userId, symmetricKey);
            // send user id to the server side
            out.write(encryptedUserId);

            // read password from command line
            System.out.println("Enter your password: ");
            Scanner pwdReader = new Scanner(System.in);
            String password = pwdReader.nextLine().trim();
            // encrypt password using TEA
            byte[] encryptedPassword = encryption(password, symmetricKey);
            // send user password to the server side
            out.write(encryptedPassword);

            // read location from command line
            System.out.println("Enter your location: ");
            Scanner locationReader = new Scanner(System.in);
            String location = locationReader.nextLine().trim();
            // encrypt location using TEA
            byte[] encryptedLocation = encryption(location, symmetricKey);
            // send location to the server side
            out.write(encryptedLocation);

            // deal with the server reply
            byte[] resultByte = new byte[500];
            in.read(resultByte);
            System.out.println(new String(resultByte)) ;

        } catch (UnknownHostException e) {
            System.out.println("Socket:"+e.getMessage());
        } catch (EOFException e){
            System.out.println("EOF:"+e.getMessage());
        } catch (IOException e){
            System.out.println("readline:"+e.getMessage());
        } finally {
            if(s!=null)
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("close:"+e.getMessage());
                }
        }
    }

    /**
     *  Encrypt the given data using TEA
     * @param plaintext plaintext to be encrypted
     * @param key key to implement TEA
     * @return encrypted data
     */
    public static byte[] encryption (String plaintext, String key) {
        // Initialize a TEA cipher using the first 16 bytes of a key
        TEA tea = new TEA(key.getBytes());
        // Then use it to encrypt text and return
        return tea.encrypt(plaintext.getBytes());
    }
}
