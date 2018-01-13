package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* 
 * Class containing methods for taking the command string (a string with a digit 1-6)
 * received from the server and converting that into its respective Linux shell command, 
 * then executing it and returning the results.
 */
public class commandExecutor {

	/**
	 * Runs the shell command after first using parseCommand() to determine which
	 * command to run.
	 * 
	 * @param commandString		A string containing a single digit, 1-6;
	 * @return			A string containing the results of the shell command.
	 */
	static String run(String commandString) {
		String result = "";
	
		try {
			// start the shell command running as a child processes
			parseCommand(commandString);

		

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Converts the digit string into its respective shell command.
	 * 
	 * @param inputString		A string containing a single digit, 1-6;
	 * @return			A string containing the shell command to run		
	 */
	static String parseCommand(String inputString) {
		int inputInt = Integer.parseInt(inputString);
		String commandString = "";
		switch (inputInt) {
			// Date
			case 1:
				commandString = "register";
				
				
				break;

			case 2:
				commandString = "login";
				
				
				
				break;
		
		}

		return commandString;
	}
}