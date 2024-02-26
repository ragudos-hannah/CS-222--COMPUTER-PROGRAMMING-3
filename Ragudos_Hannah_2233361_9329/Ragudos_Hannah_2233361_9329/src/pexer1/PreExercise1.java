/*
Name: Ragudos, Hannah T.
Class Code and Schedule: 9329 - MTh 9:00-10:30
Programming date: January 22, 2024
 */

package pexer1;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/*Input:
  - hostname (e.g., "google.com") or an IP address.
  - search for another host.

  Process:
  1. The program uses the InetAddress to resolve the hostname to its IP addresses
  2. Prints out the hostname and associated IP addresses
  3. If the hostname cannot be resolved, an error message is printed
  4. The user can repeat this process or exit

  Output:
  - number of IP addresses found for the given hostname
  - list of the hostnames and their respective IP addresses
  - an error message is shown, if the hostname cannot be resolved

  Sample Run:
  Enter a hostname or IP address: yahoo.com
  Number of Hosts/IPs: 12
  Host name: yahoo.com IP Address: 74.6.143.26
  Host name: yahoo.com IP Address: 98.137.11.164
  Host name: yahoo.com IP Address: 74.6.143.25
  Host name: yahoo.com IP Address: 98.137.11.163
  Host name: yahoo.com IP Address: 74.6.231.20
  Host name: yahoo.com IP Address: 74.6.231.21
  Host name: yahoo.com IP Address: 2001:4998:124:1507:0:0:0:f001
  Host name: yahoo.com IP Address: 2001:4998:124:1507:0:0:0:f000
  Host name: yahoo.com IP Address: 2001:4998:24:120d:0:0:1:0
  Host name: yahoo.com IP Address: 2001:4998:44:3507:0:0:0:8000
  Host name: yahoo.com IP Address: 2001:4998:44:3507:0:0:0:8001
  Host name: yahoo.com IP Address: 2001:4998:24:120d:0:0:1:1
  Search another [y/n]? y

  Enter a hostname or IP address: google.com
  Number of Hosts/IPs: 2
  Host name: google.com IP Address: 142.251.220.238
  Host name: google.com IP Address: 2404:6800:4017:803:0:0:0:200e
  Search another [y/n]? y

  Enter a hostname or IP address: facebook.com
  Number of Hosts/IPs: 2
  Host name: facebook.com IP Address: 163.70.130.35
  Host name: facebook.com IP Address: 2a03:2880:f17e:180:face:b00c:0:25de
  Search another [y/n]? n
  Program exited.

  Process finished with exit code 0
 */

/**
 * Algorithm:
 * 1. Initialize a Scanner to read from the console
 * 2. Enter a loop that continues until the user wants to exit
 *    a. Prompt the user for a hostname or an IP address
 *    b. Use InetAddress.getAllByName() to find all IP addresses for the hostname
 *    c. If an UnknownHostException is thrown, inform the user of the error
 *    d. Otherwise, print out the hostname and all IP addresses
 *    e. Ask if the user wants to search for another host.
 *       If 'y', continue; if 'n', exit the loop.
 * 3. Close the scanner and end the program.
 */

public class PreExercise1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueSearching = true;

        while (continueSearching) {
            System.out.println();
            System.out.print("Enter a hostname or IP address: ");
            String input = scanner.nextLine();

            try {
                // Perform the host lookup using the InetAddress class
                InetAddress[] addresses = InetAddress.getAllByName(input);
                System.out.println("Number of Hosts/IPs: " + addresses.length);
                for (InetAddress address : addresses) {
                    System.out.println("Host name: " + address.getHostName() + " IP Address: " + address.getHostAddress());
                }
            } catch (UnknownHostException e) {
                System.out.println("Could not find IP address for: " + input);
            }
            // Prompt the user to search for another host or to exit
            System.out.print("Search another [y/n]? ");
            String answer = scanner.nextLine();
            if (!answer.equalsIgnoreCase("y")) {
                continueSearching = false;
            }
        }
        // Close the scanner and exit the program
        scanner.close();
        System.out.println("Program exited.");
    }
}





















