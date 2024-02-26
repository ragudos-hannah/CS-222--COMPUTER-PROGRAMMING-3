/*
Name: Ragudos, Hannah T.
Class Code and Schedule: 9329 - MTh 9:00-10:30
Programming date: February 8, 2024
*/

package pexer3;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * The PreExercise3Server class implements a server that processes mathematical expressions
 * received from clients.
 *
 * Input:
 *  - the server listens for connections on a specified port
 *  - the client sends mathematical expressions to the server
 *
 * Process:
 *  - the server runs continuously
 *  - each client connection is handled in its own thread
 *  - if the client sends "bye", the server shuts down gracefully and closes the connection
 *
 * Output:
 *  - the server sends the result of the evaluated expression back to the client
 *  - if the expression is invalid, the server sends an appropriate error message
 *
 * Algorithm:
 *  1. Create a ServerSocket for client connections
 *  2. Enter an infinite loop to continuously accept new client connections
 *  3. For each client connection:
 *      a. Accept the connection
 *      b. Read expressions from the client
 *      c. Process each expression to evaluate the mathematical operation
 *      d. Send the result back to the client
 *      e. If the client sends "bye", shut down the server and close the connection.
 *  4. The server runs indefinitely until manually stopped.
 *
 * Sample Run:
 *  Server Side:
 *      Server is listening on port 2000
 *      Server received 'bye' command. Shutting down...
 */

public class PreExercise3Server {
    public static void main(String[] args) {
        // Define the port for the server to listen on
        int port = 2000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            // Start an infinite loop to accept client connections
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                    // Read each line of input from the client
                    String clientInput;

                    while ((clientInput = input.readLine()) != null) {

                        // Check if the client sent "bye" to close the connection
                        if ("bye".equalsIgnoreCase(clientInput)) {
                            System.out.println("Server received 'bye' command. Shutting down...");
                            break;
                        } // end of if

                        // Process the expression sent by the client
                        String result = processExpression(clientInput);
                        // Send the result back to the client
                        output.println(result);

                    } // end of second while-loop statement

                } catch (IOException e) {
                    // Handle any IO exceptions that occur during client communication
                    System.out.println("Exception caught when trying to listen on port " + port + " or listening for a connection");
                    System.out.println(e.getMessage());
                } // end of catch

            } // end of first while statement

        } catch (IOException e) {
            // Handle any IO exceptions that occur during server initialization
            System.out.println("Could not listen on port " + port);
            System.out.println(e.getMessage());
        } // end of catch
    } // end of main method

    /**
     * Processes a mathematical expression
     *
     * @param expression the expression to be processed, containing operands and an operator
     * @return the result of the evaluated expression, or an error message if the expression is invalid
     */
    private static String processExpression (String expression){
        // Split the input string into components
        String[] parts = expression.split("\\s+");
        // Check if the expression contains operand1, operator, operand2
        if (parts.length != 3) {
            return "Invalid expression";
        } // end of if statement

        try {
            // Parse the operands and operator from the expression
            double operand1 = Double.parseDouble(parts[0]);
            double operand2 = Double.parseDouble(parts[2]);
            String operator = parts[1];
            double result;

            // Perform the mathematical operation based on the operator
            switch (operator) {
                case "^":
                    result = Math.pow(operand1, operand2);
                    break;
                case "*":
                    result = operand1 * operand2;
                    break;
                case "/":
                    if (operand2 == 0) return "Cannot divide by zero";
                    result = operand1 / operand2;
                    break;
                case "%":
                    result = operand1 % operand2;
                    break;
                case "+":
                    result = operand1 + operand2;
                    break;
                case "-":
                    result = operand1 - operand2;
                    break;
                default:
                    return "Invalid operator";
            } // end of switch-case
            return String.valueOf(result);

        } catch (NumberFormatException e) {
            // Handle the case where operands cannot be parsed as numbers
            return "Invalid number format";
        } // end of catch

    } // end of processExpression method
} // end of PreExercise3Server class
