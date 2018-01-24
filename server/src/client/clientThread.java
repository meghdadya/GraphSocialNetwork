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
public class clientThread extends Thread {
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
			Gson gson = new Gson();

			while (true) {
				try {

					commincuteObject mcommincuteObject = gson.fromJson(is.readLine(), commincuteObject.class);

					synchronized (this) {

						if (mcommincuteObject != null) {

							if (mcommincuteObject.getMessage().getRoute().equals("register")) {

								System.out.println(is.readLine());
								os.println(new commandExecutor().register(mcommincuteObject.getUsers().get(0)));

							} else if (mcommincuteObject.getMessage().getRoute().equals("login")) {
								System.out.println(is.readLine());
								os.println(new commandExecutor().login(mcommincuteObject.getUsers().get(0)));

							} else if (mcommincuteObject.getMessage().getRoute().equals("post")) {
								System.out.println(is.readLine());
								if (mcommincuteObject.getMessage().getMessageText().equals("newpost")) {
									os.println(new commandExecutor().newPost(mcommincuteObject.getPost().get(0)));
								} else if (mcommincuteObject.getMessage().getMessageText().equals("deletepost")) {

								}
							} else if (mcommincuteObject.getMessage().getRoute().equals("home")) {
								System.out.println(is.readLine());
								os.println(new commandExecutor().getHome(mcommincuteObject.getUsers().get(0)));
							} else if (mcommincuteObject.getMessage().getRoute().equals("findfriend")) {
								System.out.println(is.readLine());
								if (mcommincuteObject.getMessage().getMessageText().equals("getall")) {
									os.println(new commandExecutor()
											.getFriends(mcommincuteObject.getUsers().get(0).getId()));
								} else if (mcommincuteObject.getMessage().getMessageText().equals("searchfriend")) {
									os.println(new commandExecutor()
											.searchFriends(mcommincuteObject.getMessage().getJson()));
								}

							} else if (mcommincuteObject.getMessage().getRoute().equals("profile")) {
								System.out.println(is.readLine());
								os.println(new commandExecutor().getProfile(mcommincuteObject.getFollow().get(0),
										mcommincuteObject.getUsers().get(0)));

							} else if (mcommincuteObject.getMessage().getRoute().equals("follow")) {

								if (mcommincuteObject.getMessage().getMessageText().equals("following")) {

								} else if (mcommincuteObject.getMessage().getMessageText().equals("followback")) {

								} else if (mcommincuteObject.getMessage().getMessageText().equals("unfollow")) {

								}

							}

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// close the connection to the client
			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Output closed.");
		}
	}
}
