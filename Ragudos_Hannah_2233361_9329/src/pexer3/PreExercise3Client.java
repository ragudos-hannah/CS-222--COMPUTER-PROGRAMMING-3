/*
Name: Ragudos, Hannah T.
Class Code and Schedule: 9329 - MTh 9:00-10:30
Programming date: February 6, 2024
*/

package pexer3;

import java.io.*;
import java.net.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 * The PreExercise3Client class implements a client application that reads expressions from an XML file,
 * sends them to a server for processing, and prints the responses received from the server.
 *
 * Input:
 *  - the client reads expressions from an XML file containing operands and operators
 *  - the server listens for connections on a specified hostname and port
 *
 * Process:
 *  - the client establishes a connection with the server
 *  - after sending all expressions, send "bye" to indicate the end of input
 *
 * Output:
 *  - the client prints the expressions sent to the server and the responses received from the server
 *
 * Algorithm:
 *  1. Create a connection with the server
 *  2. Read expressions from the XML file
 *  3. For each expression:
 *      a. Parse the expression from the XML file
 *      b. Send the expression to the server
 *      c. Receive the response from the server
 *      d. Print the expression and its response
 *  4. After sending all expressions, send "bye" to the server to indicate the end of input
 *  5. Close the connection with the server
 *
 * Sample Run:
 *  Client's Side:
 *     5 ^ 2 = 25.0
 *     12 % 5 = 2.0
 *     20 * 4 = 80.0
 *     15 / 3 = 5.0
 *     9 + 10 = 19.0
 *     100 - 45 = 55.0
 *     5.2 + 2.x = Invalid number format
 *     23 $ 2.5 = Invalid operator
 *     eight ^ three = Invalid number format
 *     7 ! 2 = Invalid operator
 *     Sending 'bye' to the server...
 *     Sent 'bye' to the server.
 */

public class PreExercise3Client {
    public static void main(String[] args) {

        // Define the hostname and port for the server connection
        String hostname = "localhost";
        int port = 2000;

        try (Socket socket = new Socket(hostname, port);
             PrintWriter sendData = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader receiveData = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Read expressions from an XML file
            File xmlFile = new File("Ragudos_Hannah_2233361_9329/src/pexer3/res/exer3.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Get the list of expression nodes from the XML document
            NodeList expressionNodes = doc.getElementsByTagName("expression");

            // Iterate over each expression node
            for (int i = 0; i < expressionNodes.getLength(); i++) {
                Node expressionNode = expressionNodes.item(i);

                // Check if the node is an element node
                if (expressionNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) expressionNode;

                    // Extract operand1, operator, and operand2 from the expression node
                    String operand1 = element.getElementsByTagName("operand1").item(0).getTextContent();
                    String operator = element.getElementsByTagName("operator").item(0).getTextContent();
                    String operand2 = element.getElementsByTagName("operand2").item(0).getTextContent();

                    // Construct the expression
                    String expression = operand1 + " " + operator + " " + operand2;

                    // Send the expression to the server
                    sendData.println(expression);

                    // Receive the response from the server
                    String response = receiveData.readLine();

                    // Print the expression and its corresponding response
                    System.out.println(expression + " = " + response);
                } // end of if-statement

            } // end of for statement

            // After sending all expressions, before sending "bye"
            System.out.println("Sending 'bye' to the server..."); // Print before sending "bye"
            sendData.println("bye"); // Send "bye" to indicate end of input
            System.out.println("Sent 'bye' to the server."); // Print after sending "bye"

        } catch (Exception ex) {
            ex.printStackTrace();
        } // end of catch

    } // end of main method
} // end of PreExercise3Client class