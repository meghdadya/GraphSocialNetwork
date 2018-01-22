package client;

import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import model.commincuteObject;
import model.message;
import model.post;
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
		message message = new message();
		commincuteObject commincuteObject = new commincuteObject();
		List<users> usersList = new ArrayList();
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

	public String newPost(post post) {
		message message = new message();
		commincuteObject commincuteObject = new commincuteObject();
		try {

			new DBConnection().insert_post(post);
			message.setMessageText("Posting done");

		} catch (Exception e) {
			e.printStackTrace();
		}
		commincuteObject.setMessage(message);
		return new Gson().toJson(commincuteObject);
	}

	public String getHome(users user) {
		message message = new message();
		commincuteObject commincuteObject = new commincuteObject();
		try {
			if (new DBConnection().select_posts_home() != null) {
				if (new DBConnection().get_user(user) != null) {
					List<users> usersList = new ArrayList();
					usersList.add(new DBConnection().get_user(user));
					commincuteObject.setUsers(usersList);
				}
				if (new DBConnection().select_followed(user.getId()) != null) {
					commincuteObject.setFollow(new DBConnection().select_followed(user.getId()));
				}
				commincuteObject.setPosts(new DBConnection().select_posts_home());
				message.setMessageText("home done");
			} else {
				message.setMessageText("not load");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		commincuteObject.setMessage(message);
		return new Gson().toJson(commincuteObject);
	}

	public String getFriends(int id) {

		message message = new message();
		commincuteObject commincuteObject = new commincuteObject();
		try {
			if (new DBConnection().select_users(id) != null) {
				commincuteObject.setUsers(new DBConnection().select_users(id));
				message.setMessageText("done");
			} else {

				message.setMessageText("empty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		commincuteObject.setMessage(message);
		return new Gson().toJson(commincuteObject);
	}

	public static void main(String[] args) {
		// users users = new users();
		// users.setEmail("meghdad@gmail.com");
		// users.setPassword("12345");
		// users user = new users();
		// user.setId(1);
		System.out.println(new commandExecutor().getFriends(1));
	}

}