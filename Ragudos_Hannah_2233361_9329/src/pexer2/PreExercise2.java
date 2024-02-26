/*
Name: Ragudos, Hannah T.
Class Code and Schedule: 9329 - MTh 9:00-10:30
Programming date: January 29, 2024
*/

package pexer2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/*
 Input:
  - server listens for connections on a specified port
  - when a client connects, it will be asked by his name and age

 Process:
 - the server runs continuously, accepting multiple client connections
 - each client connection is handled in its own thread
 - the server echoes back the messages until it exits

 Output:
 - eligibility status for the client
 - connection with client is closed after interaction with the server

 Sample Run:
 Client's Side:
 What is your name?
 Simon
 What is your age?
 13
 Simon, you are not yet eligible for a driver's license.
 Thank you and have a good day.
 Connection to host lost.

 What is your name?
 Hannah
 What is your age?
 19
 Hannah, you are eligible for a driver's license.
 Thank you and have a good day.
 Connection to host lost.

 What is your name?
 Jezreel
 What is your age?
 20
 Simon, you are not yet eligible for a driver's license.
 Thank you and have a good day.
 Connection to host lost.

 Server Side:
 Client 1 connected
 Client 2 connected
 Client 3 connected
*/

/**
 * The PreExercise2 class implements a server application that checks if a client is eligible for a driver's license
 * The server runs continuously, handling multiple client connections in separate threads
 * Each client is asked for their name and age, and the server responds with their eligibility for a driver's license
 *
 * Algorithm:
 * 1. Create a ServerSocket for client connections
 * 2. Enter an infinite loop to continuously accept new client connections
 * 3. For each client connection, call a new thread to handle the interaction
 * 4. In each thread:
 *      a. Prompt the client for their name
 *      b. Prompt the client for their age
 *      c. Check if the age is 18 or above.
 *      d. Close the connection to the client and exit the thread
 * 5. The server runs indefinitely until manually stopped
*/

public class PreExercise2 {
    private static int clientCount = 0; // counter for the clients

    public static void main(String[] args) {
        int port = 2000;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started. Listening for connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept();

                // Increment client counter and print the message
                clientCount++;
                System.out.println("Client " + clientCount + " connected");

                new Thread(() -> handleClient(clientSocket, clientCount)).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the interaction with a connected client
     *
     * @param clientSocket The socket representing the connection to the client
     * @param clientNumber The unique number assigned to the client
     */
    private static void handleClient(Socket clientSocket, int clientNumber) {
        try (
                BufferedReader streamRdr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter streamWtr = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            // Client interaction
            streamWtr.println("What is your name?");
            String name = streamRdr.readLine();

            int age;
            while (true) {
                streamWtr.println("What is your age?");
                try {
                    age = Integer.parseInt(streamRdr.readLine());
                    if (age <= 0) {
                        throw new NumberFormatException();
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    streamWtr.println("Please enter a valid age.");
                }
            }
            if (age >= 18) {
                streamWtr.println(name + ", you are eligible for a driver's license.");
            } else {
                streamWtr.println(name + ", you are not yet eligible for a driver's license.");
            }
            streamWtr.println("Thank you and have a good day.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}