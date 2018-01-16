package client;

import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import model.commincuteObject;
import model.message;
import model.users;
import com.google.gson.Gson;

public class commandExecutor {

	public String register(users users) {
		String result = "";
		try {

			if (new DBConnection().checkUserEmail(users).equals("successful")) {
				new DBConnection().insert_users(users);
				result = "Thanks for registration";
			} else {
				result = "Please try another email";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String login(users user) {
		users result = new users();
		message message=new message();
		commincuteObject commincuteObject=new commincuteObject();
		List <users> usersList=new ArrayList();
		message.setMessageText("login");
		try {
			result = new DBConnection().login_users(user);
			if (result != null) {
				if (result.getEmail().equals(user.getEmail()) && !result.getPassword().equals(user.getPassword())) {
					message.setMessageText("Wrong Password");
					
				}
			} else {
				
				message.setMessageText("User Not Found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		usersList.add(result);
		commincuteObject.setMessage(message);
		commincuteObject.setUsers(usersList);
		
		return new Gson().toJson(commincuteObject);
	}
	
	
	

	public static void main(String[] args) {
		users users = new users();
		users.setEmail("meghdad@gmail.com");
		users.setPassword("12345");
		System.out.println(new commandExecutor().login(users));

	}

}