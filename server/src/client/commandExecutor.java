package client;

import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import model.commincuteObject;
import model.follow;
import model.followInfo;
import model.like;
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
			if (new DBConnection().select_posts_home(user) != null) {
				if (new DBConnection().get_user(user) != null) {
					List<users> usersList = new ArrayList();
					usersList.add(new DBConnection().get_user(user));
					commincuteObject.setUsers(usersList);
				}
				if (new DBConnection().select_followed(user.getId()) != null) {
					commincuteObject.setFollow(new DBConnection().select_followed(user.getId()));
				}
				if (new DBConnection().select_notification(user) != null) {
					commincuteObject.setNotificationsList(new DBConnection().select_notification(user));
				}
				commincuteObject.setPosts(new DBConnection().select_posts_home(user));
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

	public String searchFriends(String query) {

		message message = new message();
		commincuteObject commincuteObject = new commincuteObject();
		try {
			if (new DBConnection().search_users(query) != null) {
				commincuteObject.setUsers(new DBConnection().search_users(query));
				message.setMessageText("done");
			} else {
				commincuteObject.setUsers(new DBConnection().search_users(query));
				message.setMessageText("empty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		commincuteObject.setMessage(message);
		return new Gson().toJson(commincuteObject);
	}

	public String getProfile(follow f, users u, message recive) {
		followInfo followInfo = new followInfo();
		message message = new message();
		commincuteObject commincuteObject = new commincuteObject();
		followInfo.setPostQty(new DBConnection().select_posts(u));
		followInfo.setFollowerQty(new DBConnection().select_followed(u));
		followInfo.setFollowedQty(new DBConnection().select_follower(u));
		message.setJson(new Gson().toJson(followInfo));
		if (new DBConnection().select_users(u) != null)
			commincuteObject.setUsers(new DBConnection().select_users(u));
		System.out.println(recive.getMessageText());
		if (new DBConnection().select_posts_users(u, Integer.parseInt(recive.getMessageText())) != null)
			commincuteObject
					.setPosts(new DBConnection().select_posts_users(u, Integer.parseInt(recive.getMessageText())));

		if (new DBConnection().following(f) && new DBConnection().followed(f)) {

			message.setMessageText("fowllowing");
		} else if (new DBConnection().following(f) && new DBConnection().followed(f) == false) {

			message.setMessageText("fowllowing");

		} else if (new DBConnection().following(f) != true && new DBConnection().followed(f)) {

			message.setMessageText("followback");

		} else if (new DBConnection().following(f) == false && new DBConnection().followed(f) == false) {

			message.setMessageText("follow");

		}
		commincuteObject.setMessage(message);

		return new Gson().toJson(commincuteObject);
	}

	public String followFunc(follow f) {

		message message = new message();
		commincuteObject commincuteObject = new commincuteObject();
		message.setRoute("followFunc");
		message.setMessageText(new DBConnection().insertfollower(f));
		commincuteObject.setMessage(message);
		return new Gson().toJson(commincuteObject);
	}

	public String getFollowers(users u) {

		message message = new message();
		commincuteObject commincuteObject = new commincuteObject();
		try {
			if (new DBConnection().select_followers(u) != null) {
				commincuteObject.setUsers(new DBConnection().select_followers(u));
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

	public String getFollowing(users u) {

		message message = new message();
		commincuteObject commincuteObject = new commincuteObject();
		try {
			if (new DBConnection().select_following(u) != null) {
				commincuteObject.setUsers(new DBConnection().select_following(u));
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

	public void setLike(message message) {
		like like = new Gson().fromJson(message.getJson(), like.class);
		try {
			new DBConnection().like(like);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// follow mfollow = new follow();
		// mfollow.setFollower_id(1);
		// mfollow.setFollowed_id(2);
		// mfollow.setStatus(0);
		//
		// follow mfollow = new follow();
		// mfollow.setFollower_id(3);
		// mfollow.setFollowed_id(1);
		// mfollow.setStatus(0);
		// // System.out.println(new commandExecutor().followFunc(mfollow));
		users users = new users();
		users.setId(1);
		System.out.println(new commandExecutor().getHome(users));
		// message message=new message();
		// message.setMessageText("2");

		// System.out.println(new commandExecutor().getHome(users));
		// System.out.println(new commandExecutor().getFriends(2));
		// System.out.println(new commandExecutor().getProfile(mfollow, users,message));
		// like l1 = new like();
		// l1.setPost_id(1);
		// l1.setUser_id(2);
		//
		//
		// message message = new message();
		// message.setJson(new Gson().toJson(l1));
		// new commandExecutor().setLike(message);
	}

}