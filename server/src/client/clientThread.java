/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author babe
 */


// For every client's connection we call this class
public class clientThread extends Thread{
     private String clientName = null;
  private BufferedReader is = null;
  private PrintWriter os = null;
  private Socket clientSocket = null;
  private final clientThread[] threads;
  private int maxClientsCount;

  public clientThread(Socket clientSocket, clientThread[] threads) {
    this.clientSocket = clientSocket;
    this.threads = threads;
    maxClientsCount = threads.length;
  }

  public void run() {
		try {
			// open a new PrintWriter and BufferedReader on the socket
			os = new PrintWriter(clientSocket.getOutputStream(), true);
			is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			System.out.print("Reader and writer created. ");
			System.out.print("Accepted connection. ");
		   while (true) {
	            try {
	            	String inString;
	    			// read the command from the client
	            	synchronized (this) {
	            		
	    		        if  ((inString = is.readLine()) != null) {
			    			System.out.println("Read command " + inString);
			    			// run the command using CommandExecutor and get its output
			    			String outString = commandExecutor.run(inString);
			    			System.out.println("Server sending result to client");
			    			// send the result of the command to the client
			    			os.println(outString);
	    		        }
	            	}
	            } catch (IOException e) {
	                e.printStackTrace();
	                break;
	            }
		   }		
		}
		catch (IOException e) {
			e.printStackTrace();
		} finally {
			// close the connection to the client
			try {
				clientSocket.close();
			}
			catch (IOException e) {
				e.printStackTrace();	
			}			
			System.out.println("Output closed.");
}
}
}
