package admin;

import javax.swing.*;

import server.MultiThreadChatServerSync;

//Class to precise who is connected : Client or Server
public class ClientServer {

	public static void main(String[] args) {

			String[] arguments = new String[] {};
			new MultiThreadChatServerSync();
			MultiThreadChatServerSync.main(arguments);

	}

}
