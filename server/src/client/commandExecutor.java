package client;

import database.DBConnection;
import model.users;

public class commandExecutor {

	
	public String register(users users ) {
		String result = "";
		try {
			
			if(new DBConnection().checkUserEmail(users).equals("successful")) {
				new DBConnection().insert_users(users);
				result="Thanks for registration";
			}else {
				result="Please try another name or user name";
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	


	
	
	
}