package project2task3;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A TCP server class dealing with the user authentication and location file update
 *
 * Created by Gao on 9/10/2015.
 */
public class TCPSpyCommanderUsingTEAandPasswords {

    public static String[] userIdList = {"jamesb", "joem", "mikem"}; //user id list
    public static HashMap<String, String> salts = new HashMap<String, String>(); //user id with corresponding salt
    public static HashMap<String, String> hashValues = new HashMap<String, String>(); //user id with corresponding salt and password hash value
    public static HashMap<String, String> locations = new HashMap<String, String>(); // user id with corresponding location
    public static String symmetricKey; // server symmetricKey got from commander
    public static int userCount = 0;

    // create the content of the KML file
    public static String fileHeader =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
            "<kml xmlns=\"http://earth.google.com/kml/2.2\"\n" +
            "><Document>\n" +
            "<Style id=\"style1\">\n" +
            "<IconStyle>\n" +
            "<Icon>\n" +
            "<href>http://maps.gstatic.com/intl/en_ALL/mapfiles/ms/micons/bluedot.png</href>\n" +
            "</Icon>\n" +
            "</IconStyle>\n" +
            "</Style>";
    public static String seanBegg =
            "<Placemark>\n" +
            " <name>seanb</name>\n" +
            " <description>Spy Commander</description>\n" +
            " <styleUrl>#style1</styleUrl>\n" +
            " <Point>\n" +
            " <coordinates>-79.945289,40.44431,0.00000</coordinates>\n" +
            " </Point>\n" +
            "</Placemark>";

    public static String jamesb;
    public static String joe;
    public static String mike;

    public static String tail =
            "</Document>\n" +
            "</kml>";

    public static void main (String args[]) {
        initialize();

        try {
            System.out.println("Enter symmetric key for TEA (taking first sixteen bytes):");
            Scanner reader = new Scanner(System.in);
            symmetricKey = reader.nextLine().trim();
            System.out.println("Waiting for spies to visit...");

            int serverPort = 7896; // the server port
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while (true) {
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Listen socket:"+e.getMessage());
        }
    }

    /**
     *  Initialization of the information stored in server
     */
    public static void initialize() {
        // initialize three salts for different users
        salts.put(userIdList[0], "sb1");
        salts.put(userIdList[1], "sb2");
        salts.put(userIdList[2], "sb3");

        // initialize the starting pointer location for the three users
        locations.put(userIdList[0], "-79.945289, 40.44431, 0");
        locations.put(userIdList[1], "-79.945289, 40.44431, 0");
        locations.put(userIdList[2], "-79.945289, 40.44431, 0");

        // initialize the user passwords
        String[] passwords = {"james", "joe", "mike"};

        // generating the hash value of different users' salt with password and stored in server
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((salts.get(userIdList[0]) + passwords[0]).getBytes());
            String hash0 = new String(md.digest());

            md.update((salts.get(userIdList[1]) + passwords[1]).getBytes());
            String hash1 = new String(md.digest());

            md.update((salts.get(userIdList[2]) + passwords[2]).getBytes());
            String hash2 = new String(md.digest());

            hashValues.put(userIdList[0], hash0);
            hashValues.put(userIdList[1], hash1);
            hashValues.put(userIdList[2], hash2);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // initialize the original location data
        jamesb =
                "<Placemark>\n" +
                " <name>" + userIdList[0] + "</name>\n" +
                " <description>Spy</description>\n" +
                " <styleUrl>#style1</styleUrl>\n" +
                " <Point>\n" +
                " <coordinates>" + locations.get(userIdList[0]) + "</coordinates>\n" +
                " </Point>\n" +
                "</Placemark>";

        joe =
            "<Placemark>\n" +
            " <name>" + userIdList[1] + "</name>\n" +
            " <description>Spy</description>\n" +
            " <styleUrl>#style1</styleUrl>\n" +
            " <Point>\n" +
            " <coordinates>" + locations.get(userIdList[1]) + "</coordinates>\n" +
            " </Point>\n" +
            "</Placemark>";

        mike =
            "<Placemark>\n" +
            " <name>" + userIdList[2] + "</name>\n" +
            " <description>Spy</description>\n" +
            " <styleUrl>#style1</styleUrl>\n" +
            " <Point>\n" +
            " <coordinates>" + locations.get(userIdList[2]) + "</coordinates>\n" +
            " </Point>\n" +
            "</Placemark>";
    }
}

/**
 * A class extends Thread to handle every socket request
 */
class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    boolean keyFlag;

    /**
     * Construct method to create and run a connection
     * @param aClientSocket socket to listen and get request from client
     */
    public Connection (Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream()); // client request input data
            out =new DataOutputStream(clientSocket.getOutputStream()); // service reply output data
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:"+e.getMessage());
        }
    }

    /**
     * override the method of Thread to do service
     */
    public void run() {
        try {
            // read a line of data from the input stream
            byte[] userIdInput = new byte[500];
            in.read(userIdInput);

            byte[] passwordInput = new byte[500];
            in.read(passwordInput);

            byte[] locationInput = new byte[500];
            in.read(locationInput);

            // use server's symmetric key to decrypt the encrypted data got from client
            TEA tea = new TEA(TCPSpyCommanderUsingTEAandPasswords.symmetricKey.getBytes());
            String userIdPlain = new String(tea.decrypt(userIdInput));
            System.out.println("Got visit " + (++TCPSpyCommanderUsingTEAandPasswords.userCount) + " from " + userIdPlain);
            String passwordPlain = new String(tea.decrypt(passwordInput));

            // authenticate user input information
            boolean isAuthenticated = authentication(userIdPlain, passwordPlain);

            // deal with different cases according from the authentication result
            if (!keyFlag) { // if the symmetric key is not authenticated

                System.out.println("Illegal user try to log in to the system");
                clientSocket.close();

            } else if (!isAuthenticated) { // if the symmetric key is authenticated but the user id and password are illegal

                String errorMessage = "Not a valid user-id or password";
                System.out.println(errorMessage);
                out.write(errorMessage.getBytes());

            } else { //if both the asymmetric key and user information are authenticated

                // decrypt the location data from client
                String location = new String(tea.decrypt(locationInput));

                // update the location information stored in server
                TCPSpyCommanderUsingTEAandPasswords.locations.replace(userIdPlain, location);
                updataKML(userIdPlain);
                String successMessage = "Thank you. Your location was securely transmitted to Intelligence Headquarters";

                // update the KML file with the new location information
                Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("SecretAgents.kml"), "UTF-8"));
                writer.write(TCPSpyCommanderUsingTEAandPasswords.fileHeader);
                writer.write(TCPSpyCommanderUsingTEAandPasswords.seanBegg);
                writer.write(TCPSpyCommanderUsingTEAandPasswords.jamesb);
                writer.write(TCPSpyCommanderUsingTEAandPasswords.joe);
                writer.write(TCPSpyCommanderUsingTEAandPasswords.mike);
                writer.write(TCPSpyCommanderUsingTEAandPasswords.tail);
                writer.close();

                // send back the successful information
                out.write(successMessage.getBytes());
            }
        } catch (EOFException e) {
            System.out.println("EOF:"+e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:"+e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                /*close failed*/
            }
        }
    }

    /**
     * Authenticate the user identification
     * @param userId Decrypted userId
     * @param password Decrypted user password
     * @return authentication passed or not
     */
    public boolean authentication (String userId, String password) {
        // determine whether the decrypted userId is legal ascii string
        CharsetEncoder asciiEncoder = Charset.forName("US-ASCII").newEncoder();
        boolean isAscii = asciiEncoder.canEncode(userId);

        if (isAscii) { // if userId is legal ascii string, symmetric key is legal
            if (userId.equals(TCPSpyCommanderUsingTEAandPasswords.userIdList[0]) || userId.equals(TCPSpyCommanderUsingTEAandPasswords.userIdList[1]) || userId.equals(TCPSpyCommanderUsingTEAandPasswords.userIdList[2])) { // if userId is existed
                MessageDigest md;
                try {
                    // generate hash value of the input password with user salt
                    md = MessageDigest.getInstance("MD5");
                    md.update((TCPSpyCommanderUsingTEAandPasswords.salts.get(userId) + password).getBytes());
                    String hashValue = new String(md.digest());

                    // validate the user password using hash value
                    if (hashValue.equals(TCPSpyCommanderUsingTEAandPasswords.hashValues.get(userId))) { // if password is correct
                        keyFlag = true;
                        return true;
                    } else { // if password id not correct
                        keyFlag = true;
                        return false;
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    return false;
                }
            } else { // if userId is not existed
                keyFlag = true;
                return false;
            }
        } else { // if userId is illegal ascii string
            keyFlag = false;
            return  false;
        }
    }

    /**
     * Update the location information written to the KML file
     * @param userID user id used to decide which part to update
     */
    public void updataKML (String userID) {
        if (userID.equals("jamesb")) {
            TCPSpyCommanderUsingTEAandPasswords.jamesb =
                    "<Placemark>\n" +
                    " <name>" + TCPSpyCommanderUsingTEAandPasswords.userIdList[0] + "</name>\n" +
                    " <description>Spy</description>\n" +
                    " <styleUrl>#style1</styleUrl>\n" +
                    " <Point>\n" +
                    " <coordinates>" + TCPSpyCommanderUsingTEAandPasswords.locations.get(TCPSpyCommanderUsingTEAandPasswords.userIdList[0]) + "</coordinates>\n" +
                    " </Point>\n" +
                    "</Placemark>";
        } else if (userID.equals("joem")) {
            TCPSpyCommanderUsingTEAandPasswords.joe =
                    "<Placemark>\n" +
                    " <name>" + TCPSpyCommanderUsingTEAandPasswords.userIdList[1] + "</name>\n" +
                    " <description>Spy</description>\n" +
                    " <styleUrl>#style1</styleUrl>\n" +
                    " <Point>\n" +
                    " <coordinates>" + TCPSpyCommanderUsingTEAandPasswords.locations.get(TCPSpyCommanderUsingTEAandPasswords.userIdList[1]) + "</coordinates>\n" +
                    " </Point>\n" +
                    "</Placemark>";
        } else {
            TCPSpyCommanderUsingTEAandPasswords.mike =
                    "<Placemark>\n" +
                    " <name>" + TCPSpyCommanderUsingTEAandPasswords.userIdList[2] + "</name>\n" +
                    " <description>Spy</description>\n" +
                    " <styleUrl>#style1</styleUrl>\n" +
                    " <Point>\n" +
                    " <coordinates>" + TCPSpyCommanderUsingTEAandPasswords.locations.get(TCPSpyCommanderUsingTEAandPasswords.userIdList[2]) + "</coordinates>\n" +
                    " </Point>\n" +
                    "</Placemark>";
        }
    }

}
