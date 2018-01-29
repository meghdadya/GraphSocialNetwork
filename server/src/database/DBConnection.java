package database;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.commincuteObject;
import model.follow;
import model.graphNodes;
import model.like;
import model.message;
import model.notification;
import model.post;
import model.posts;
import model.user_notifications;
import model.users;

/**
 *
 * @author babe
 */
public class DBConnection {
	/**
	 * Connect to a sample GSNetwork
	 */
	private Connection connect() {
		// db parameters
		URL sqlScriptUrl = DBConnection.class.getClassLoader().getResource("sql/GSNetwork.db");
		String url = "jdbc:sqlite:" + sqlScriptUrl;
		// create a connection to the database
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	/**
	 * Insert a new row into the users table
	 *
	 * @param name
	 * @param email
	 * @param bio
	 * @param photo
	 */
	public void insert_users(users users) {

		String sql = "INSERT INTO tbl_users(name,email,password,bio,photo) VALUES('" + users.getName() + "','"
				+ users.getEmail() + "','" + users.getPassword() + "','" + users.getBio() + "','" + users.getPhoto()
				+ "')";
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void testuser() {
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_users;");
			while (rs.next()) {
				System.out.println(rs.getInt("id"));
				System.out.println(rs.getString("name"));
				System.out.println(rs.getString("email"));
				System.out.println(rs.getString("bio"));

				System.out.println();
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public String checkUserEmail(users users) {
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt
					.executeQuery("SELECT name,email FROM tbl_users where email='" + users.getEmail() + "';");
			if (!rs.next()) {
				return "successful";
			} else {
				return "unsuccessful";
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return "problem in query";
	}

	public users login_users(users user) {
		users userDetail = new users();
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_users where email ='" + user.getEmail() + "' ;");
			if (rs.next()) {
				userDetail.setId(rs.getInt("id"));
				userDetail.setEmail(rs.getString("email"));
				userDetail.setName(rs.getString("name"));
				userDetail.setBio(rs.getString("bio"));
				userDetail.setPhoto(rs.getString("photo"));
				userDetail.setPassword(rs.getString("password"));
				return userDetail;
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public users get_user(users user) {
		users userDetail = new users();
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_users where id ='" + user.getId() + "' ;");
			if (rs.next()) {
				userDetail.setId(rs.getInt("id"));
				userDetail.setEmail(rs.getString("email"));
				userDetail.setName(rs.getString("name"));
				userDetail.setBio(rs.getString("bio"));
				userDetail.setPhoto(rs.getString("photo"));
				userDetail.setPassword(rs.getString("password"));
				return userDetail;
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public void insert_post(post post) {
		String sql = "INSERT INTO tbl_post(date,photo,text,user_id) VALUES('" + post.getDate() + "','" + post.getPhoto()
				+ "','" + post.getText() + "','" + post.getUser_id() + "')";
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		users users = new users();
		users.setId(post.getUser_id());
		create_post_notification(users, post);
	}

	public List<posts> select_posts_home(users user) {
		List<posts> postsList = new ArrayList();
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT \r\n" + "tbl_post.*,\r\n" + "tbl_users.name as user_name,\r\n"
					+ "tbl_users.photo as user_photo,\r\n" + "tbl_users.email as user_email,\r\n" + "(\r\n"
					+ "	SELECT count(id) FROM tbl_like WHERE tbl_like.post_id = tbl_post.id\r\n" + ")as count_like,\r\n"
					+ "tbl_like.user_id as like_by_me\r\n" + "FROM tbl_follow\r\n"
					+ "JOIN tbl_post ON ( tbl_post.user_id = tbl_follow.followed_id OR tbl_post.user_id = "
					+ user.getId() + " )\r\n" + "JOIN tbl_users ON (tbl_post.user_id = tbl_users.id)\r\n"
					+ "LEFT JOIN tbl_like ON (tbl_like.post_id = tbl_post.id AND tbl_like.user_id =" + user.getId()
					+ ")\r\n" + "WHERE tbl_follow.follower_id = " + user.getId() + "\r\n" + "GROUP BY tbl_post.id\r\n"
					+ "ORDER BY id DESC;");
			while (rs.next()) {

				int id = rs.getInt("id");
				posts posts = new posts();
				post post = new post();
				users users = new users();
				post.setId(rs.getInt("id"));
				post.setUser_id(rs.getInt("user_id"));
				post.setDate(rs.getString("date"));
				post.setText(rs.getString("text"));
				users.setEmail(rs.getString("user_email"));
				users.setName(rs.getString("user_name"));
				if (rs.getInt("like_by_me") == user.getId()) {
					posts.setLiked(true);
				}
				posts.setLike_count(rs.getInt("count_like"));
				posts.setPost(post);
				posts.setUsers(users);
				postsList.add(posts);
			}
			rs.close();
			stmt.close();
			return postsList;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public List<follow> select_followed(int id) {
		List<follow> followsList = new ArrayList();
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_follow where followed_id = '" + id + "' and status=0;");
			while (rs.next()) {

				follow follow = new follow();
				follow.setId(rs.getInt("id"));
				follow.setFollowed_id(rs.getInt("followed_id"));
				follow.setFollower_id(rs.getInt("follower_id"));
				follow.setStatus(rs.getInt("status"));
			}
			rs.close();
			stmt.close();
			return followsList;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public List<users> select_users(int id) {

		List<users> uesrs = new ArrayList<users>();

		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_users where id != '" + id + "';");
			while (rs.next()) {
				users user = new users();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setBio(rs.getString("bio"));
				user.setEmail(rs.getString("email"));
				uesrs.add(user);
			}
			rs.close();
			stmt.close();
			return uesrs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public List<users> select_all_users() {

		List<users> uesrs = new ArrayList<users>();

		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_users ;");
			while (rs.next()) {
				users user = new users();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setBio(rs.getString("bio"));
				user.setEmail(rs.getString("email"));
				uesrs.add(user);
			}
			rs.close();
			stmt.close();
			return uesrs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public String insertfollower(follow follow) {
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_follow WHERE follower_id='" + follow.getFollower_id()
					+ "' and followed_id='" + follow.getFollowed_id() + "' ;");

			if (rs.next()) {
				String sql = "DELETE FROM tbl_follow WHERE follower_id = " + follow.getFollower_id() + " ";
				stmt.execute(sql);
				stmt.close();
				rs.close();
				return "follow";

			} else {
				String sql = "INSERT INTO tbl_follow(follower_id,followed_id) VALUES('" + follow.getFollower_id()
						+ "','" + follow.getFollowed_id() + "')";
				stmt.execute(sql);
				stmt.close();
				rs.close();
				create_follow_notification(follow);
				return "following";
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return "got nothing";
	}

	public void select_users() {

		List<users> uesrs = new ArrayList<users>();

		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_users;");
			while (rs.next()) {
				users user = new users();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setBio(rs.getString("bio"));
				user.setEmail(rs.getString("email"));
				uesrs.add(user);
				System.out.println(user.getId());
				System.out.println(user.getName());
				System.out.println(user.getEmail());
				System.out.println();
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public List<users> search_users(String query) {

		List<users> uesrs = new ArrayList<users>();

		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM tbl_users where name LIKE '%" + query + "%' OR email = '" + query + "';");
			while (rs.next()) {
				users user = new users();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setBio(rs.getString("bio"));
				user.setEmail(rs.getString("email"));
				uesrs.add(user);
			}
			rs.close();
			stmt.close();
			return uesrs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public users select_user(int id) {

		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_users where id='" + id + "';");
			users user = new users();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setBio(rs.getString("bio"));
			user.setPhoto(rs.getString("photo"));
			rs.close();
			stmt.close();
			return user;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public int select_follower(users users) {

		List<follow> followList = new ArrayList<>();

		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_follow where follower_id = '" + users.getId() + "';");
			while (rs.next()) {
				follow follow = new follow();
				follow.setId(rs.getInt("id"));
				follow.setFollowed_id(rs.getInt("followed_id"));
				follow.setFollower_id(rs.getInt("follower_id"));
				followList.add(follow);
			}
			rs.close();
			stmt.close();
			return followList.size();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return 0;

	}

	public int select_followed(users users) {

		List<follow> followList = new ArrayList<>();

		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_follow where followed_id = '" + users.getId() + "';");
			while (rs.next()) {
				follow follow = new follow();
				follow.setId(rs.getInt("id"));
				follow.setFollowed_id(rs.getInt("followed_id"));
				follow.setFollower_id(rs.getInt("follower_id"));
				followList.add(follow);
			}
			rs.close();
			stmt.close();
			return followList.size();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return 0;

	}

	public List<posts> select_posts_users(users user, int LoginId) {
		List<posts> postsList = new ArrayList();
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			String query = "SELECT tbl_post.* , tbl_users.name as user_name, tbl_users.photo as user_photo , tbl_users.email as user_email, "
					+ "( SELECT COUNT(tbl_like.id) FROM tbl_like WHERE tbl_like.post_id = tbl_post.id ) as count_like , "
					+ "tbl_like.user_id as like_by_me " + "FROM tbl_users "
					+ "JOIN tbl_post ON (tbl_post.user_id = tbl_users.id) "
					+ "LEFT JOIN tbl_like ON (tbl_like.post_id = tbl_post.id AND tbl_like.user_id = "
					+ Integer.toString(LoginId) + " ) " + "WHERE tbl_users.id = " + Integer.toString(user.getId()) + " "
					+ "GROUP BY tbl_post.id " + "ORDER BY id DESC;";

			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {

				int id = rs.getInt("id");
				posts posts = new posts();
				post post = new post();
				users users = new users();
				post.setId(rs.getInt("id"));
				post.setUser_id(rs.getInt("user_id"));
				post.setDate(rs.getString("date"));
				post.setText(rs.getString("text"));
				users.setEmail(rs.getString("user_email"));
				users.setName(rs.getString("user_name"));
				if (rs.getInt("like_by_me") == LoginId) {
					posts.setLiked(true);
				}
				System.out.println(rs.getString("count_like"));
				posts.setLike_count(rs.getInt("count_like"));
				posts.setPost(post);
				posts.setUsers(users);
				postsList.add(posts);
			}
			rs.close();
			stmt.close();
			return postsList;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public int select_posts(users users) {

		List<post> postList = new ArrayList<>();

		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_post where user_id = '" + users.getId() + "';");
			while (rs.next()) {
				post post = new post();
				post.setId(rs.getInt("id"));
				post.setUser_id(rs.getInt("user_id"));
				post.setDate(rs.getString("date"));
				post.setText(rs.getString("text"));
				post.setPhoto(rs.getString("photo"));
				postList.add(post);
			}
			rs.close();
			stmt.close();
			return postList.size();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return 0;

	}

	public List<users> select_users(users users) {

		List<users> userList = new ArrayList<>();

		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_users where id = '" + users.getId() + "';");
			while (rs.next()) {
				users user = new users();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setBio(rs.getString("bio"));
				user.setEmail(rs.getString("email"));
				userList.add(user);
			}
			rs.close();
			stmt.close();
			return userList;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;

	}

	public boolean following(follow follow) {

		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_follow where follower_id = " + follow.getFollower_id()
					+ " and followed_id=" + follow.getFollowed_id() + ";");
			if (rs.next()) {
				rs.close();
				stmt.close();
				return true;
			} else {
				rs.close();
				stmt.close();
				return false;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public boolean followed(follow follow) {

		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_follow where follower_id = " + follow.getFollowed_id()
					+ " and followed_id=" + follow.getFollower_id() + ";");
			if (rs.next()) {
				rs.close();
				stmt.close();
				return true;
			} else {
				rs.close();
				stmt.close();
				return false;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public List<users> select_followers(users u) {

		List<users> uesrs = new ArrayList<users>();

		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			// ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_users INNER
			// JOIN
			// tbl_follow ON tbl_users.id = tbl_follow.followed_id where
			// tbl_users.id="+u.getId()+" ORDER BY tbl_users.id DESC ;");
			ResultSet rs = stmt.executeQuery(
					"select * from tbl_users\r\n" + "inner join tbl_follow on tbl_users.id = tbl_follow.follower_id\r\n"
							+ "where tbl_follow.followed_id = " + u.getId()); // where
																				// tbl_users.id="+u.getId()+"
																				// ORDER
																				// BY
																				// tbl_users.id
																				// DESC
																				// ;");
			while (rs.next()) {
				users user = new users();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setBio(rs.getString("bio"));
				user.setEmail(rs.getString("email"));
				uesrs.add(user);
			}
			rs.close();
			stmt.close();
			return uesrs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public List<users> select_following(users u) {

		List<users> uesrs = new ArrayList<users>();

		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			// ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_users INNER
			// JOIN
			// tbl_follow ON tbl_users.id = tbl_follow.followed_id where
			// tbl_users.id="+u.getId()+" ORDER BY tbl_users.id DESC ;");
			ResultSet rs = stmt.executeQuery(
					"select * from tbl_users\r\n" + "inner join tbl_follow on tbl_users.id = tbl_follow.followed_id\r\n"
							+ "where tbl_follow.follower_id =" + u.getId()); // where
																				// tbl_users.id="+u.getId()+"
																				// ORDER
																				// BY
																				// tbl_users.id
																				// DESC
																				// ;");
			while (rs.next()) {
				users user = new users();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setBio(rs.getString("bio"));
				user.setEmail(rs.getString("email"));
				uesrs.add(user);
			}
			rs.close();
			stmt.close();
			return uesrs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public int select_user_id(int post_id) {
		int id;
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT user_id FROM tbl_post where id='" + post_id + "';");
			id = rs.getInt("user_id");
			rs.close();
			stmt.close();
			return id;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return 0;

	}

	public void like(like like) {
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("select * from tbl_like where  user_id='" + like.getUser_id()
					+ "' and post_id='" + like.getPost_id() + "';");

			if (rs.next()) {
				String sql = "DELETE FROM tbl_like where  user_id='" + like.getUser_id() + "' and post_id='"
						+ like.getPost_id() + "'; ";
				stmt.execute(sql);
				stmt.close();
				rs.close();
				System.out.println("unlike");

			} else {
				String sql = "INSERT INTO tbl_like(user_id,post_id) VALUES('" + like.getUser_id() + "','"
						+ like.getPost_id() + "')";
				stmt.execute(sql);
				stmt.close();
				rs.close();
				System.out.println("like");
				users users = new users();
				users.setId(select_user_id(like.getPost_id()));
				create_like_notification(users);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public void create_post_notification(users users, post post) {

		for (users u : select_followers(users)) {
			try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
				String sql = "insert Into tbl_notification(from_user_id,to_user_id,post_id,type,message,seen)values("
						+ users.getId() + "," + u.getId() + "," + post.getId() + ",'post','new Post',0)";
				stmt.execute(sql);
				stmt.close();
				System.out.println("post notification create");

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

	}

	public void create_like_notification(users users) {

		for (users u : select_followers(users)) {
			try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
				String sql = "insert Into tbl_notification(from_user_id,to_user_id,type,message,seen)values("
						+ users.getId() + "," + u.getId() + ",'like','Like notification',0)";
				stmt.execute(sql);
				stmt.close();
				System.out.println("like notification create");

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

	}

	public void create_follow_notification(follow follow) {

		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			String sql = "insert Into tbl_notification(from_user_id,to_user_id,type,message,seen)values("
					+ follow.getFollower_id() + "," + follow.getFollowed_id() + ",'follow','follow notification',0)";
			stmt.execute(sql);
			stmt.close();
			System.out.println("follow notification create");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public List<user_notifications> select_notification(users user) {
		List<user_notifications> user_notificationsList = new ArrayList();
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT \r\n" + "tbl_notification.*,\r\n"
					+ "tbl_users.id as user_id , \r\n" + "tbl_users.email  as user_email,\r\n"
					+ "tbl_users.name  as user_name,\r\n" + "tbl_users.photo as user_photo,\r\n"
					+ "tbl_post.id as post_id,\r\n" + "tbl_post.text as post_text\r\n" + "FROM tbl_notification\r\n"
					+ "JOIN tbl_users ON tbl_users.id = tbl_notification.from_user_id\r\n"
					+ "LEFT JOIN tbl_post ON tbl_post.id = tbl_notification.post_id\r\n"
					+ "WHERE tbl_notification.to_user_id = " + user.getId() + "\r\n" + "ORDER BY seen ASC, id DESC;");
			while (rs.next()) {
				user_notifications user_notifications = new user_notifications();
				notification notification = new notification();
				post post = new post();
				users users = new users();
				users.setId(rs.getInt("user_id"));
				users.setName(rs.getString("user_name"));
				users.setEmail(rs.getString("user_email"));
				post.setId(rs.getInt("post_id"));
				post.setText(rs.getString("post_text"));
				notification.setId(rs.getInt("id"));
				notification.setFrom_user_id(rs.getInt("from_user_id"));
				notification.setTo_user_id(rs.getInt("to_user_id"));
				notification.setMessage(rs.getString("message"));
				notification.setType(rs.getString("type"));
				notification.setPost_id(rs.getInt("post_id"));
				notification.setSeen(rs.getInt("seen"));
				user_notifications.setNotification(notification);
				user_notifications.setPost(post);
				user_notifications.setUsers(users);
				user_notificationsList.add(user_notifications);

			}
			rs.close();
			stmt.close();
			return user_notificationsList;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public List<graphNodes> select_graph() {
		List<graphNodes> graphNodesList = new ArrayList();
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			String query = "SELECT tblu.*, CASE tblu.id WHEN tblf2.follower_id THEN (SELECT name FROM tbl_users WHERE tbl_users.id = tblf2.followed_id) ELSE (SELECT name FROM tbl_users WHERE tbl_users.id = tblf2.follower_id) END follow_back from tbl_follow as tblf JOIN tbl_users as tblu ON (tblu.id = tblf.follower_id) LEFT JOIN tbl_follow as tblf2 ON ( tblf2.followed_id = tblu.id AND tblf2.follower_id  = tblf.followed_id) WHERE tblf2.status NOT NULL";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				graphNodes gn=new graphNodes();
				gn.setId(rs.getInt("id"));
				gn.setFollow(rs.getString("name"));
				gn.setFollowback(rs.getString("follow_back"));
				gn.setEmail(rs.getString("email"));
				gn.setBio("bio");
				graphNodesList.add(gn);
			}
			rs.close();
			stmt.close();
			return graphNodesList;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	
	public List<users> select_posts_home(post p) {
		List<users> usersList = new ArrayList();
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("Select * from tbl_post\r\n" + 
					"	Left join tbl_like on tbl_like.post_id = tbl_post.id\r\n" + 
					"	Join tbl_users on tbl_users.id = tbl_like.user_id  WHERE tbl_post.id ="+p.getId()+"  ORDER BY id DESC;");
			while (rs.next()) {
				users users = new users();
				users.setEmail(rs.getString("email"));
				users.setName(rs.getString("name"));
				users.setBio(rs.getString("bio"));
				usersList.add(users);
				
			}
			rs.close();
			stmt.close();
			return usersList;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	
	
	
	

	public static void main(String[] args) {

		// users users = new users();
		// users.setEmail("me@gmail.com");
		// users.setPassword("12345");
		// System.out.println(new DBConnection().login_users(users).getEmail());
		// post post = new post();
		// post.setDate("01/01/1336");
		// post.setText("hello world first post ever ");
		// post.setUser_id(12);
		// new DBConnection().insert_post(post);
		// new DBConnection().select_posts();

		// System.out.println(new
		// DBConnection().select_users().get(0).getEmail());
		//
		// follow mfollow = new follow();
		// mfollow.setFollower_id(3);
		// mfollow.setFollowed_id(1);
		// mfollow.setStatus(0);
		// System.out.println(new DBConnection().insertfollower(mfollow));

		// users users = new users();
		// users.setId(2);
		//
		// System.out.println(new DBConnection().select_followed(users));
		// new DBConnection().search_users("me@gmail.c");
		// users users = new users();
		// users.setId(2);
		// new DBConnection().select_followers(users);
		// like l1 = new like();
		// l1.setPost_id(1);
		// l1.setUser_id(2);
		// new DBConnection().like(l1);

		// System.out.println(new DBConnection().select_notification(users));
		
		System.out.println(new DBConnection().select_graph());

	}

}
