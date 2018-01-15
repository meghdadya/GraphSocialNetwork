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

import com.google.gson.Gson;

import model.commincuteObject;
import model.users;

/**
 *
 * @author babe
 */


// For every client's connection we call this class
public class clientThread extends Thread{
  private String clientName = null;
  private DataInputStream is = null;
  private PrintStream os = null;
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
			is = new DataInputStream(clientSocket.getInputStream());
		    os = new PrintStream(clientSocket.getOutputStream());
			System.out.print("Reader and writer created. ");
			System.out.print("Accepted connection. ");
			Gson gson = new Gson();
			
		   while (true) {
	            try {
	        
	            	commincuteObject mcommincuteObject=gson.fromJson(is.readLine(), commincuteObject.class);
	
	            	synchronized (this) {
	            		
	    		        if  (mcommincuteObject != null) {
	    		        	
		    		        	if(mcommincuteObject.getMessage().getRoute().equals("register")) {
		    		        		
		    		        		System.out.println(is.readLine());
		    		        		os.println(new commandExecutor().register(mcommincuteObject.getUsers().get(0)));
		
		    		    		}else if(mcommincuteObject.getMessage().getRoute().equals("login")) {
		    		    			
		    		    			System.out.println(is.readLine());
		    		    				
		    		    		}
			    			
	    		        }
	            	}
	            } catch (Exception e) {
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
