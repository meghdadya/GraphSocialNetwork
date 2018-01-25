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
import model.message;
import model.post;
import model.posts;
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
	}

	public List<posts> select_posts_home() {
		List<posts> postsList = new ArrayList();
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM tbl_post INNER JOIN tbl_users ON tbl_post.user_id = tbl_users.id ORDER BY tbl_post.id DESC;");
			while (rs.next()) {

				int id = rs.getInt("id");
				posts posts = new posts();
				post post = new post();
				users users = new users();
				post.setId(rs.getInt("id"));
				post.setUser_id(rs.getInt("user_id"));
				post.setDate(rs.getString("date"));
				post.setText(rs.getString("text"));
				post.setPhoto(rs.getString("photo"));
				users.setEmail(rs.getString("email"));
				users.setName(rs.getString("name"));
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

	public List<posts> select_posts_users(users user) {
		List<posts> postsList = new ArrayList();
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM tbl_post INNER JOIN tbl_users ON tbl_post.user_id = tbl_users.id where tbl_users.id="
							+ user.getId() + " ;");
			while (rs.next()) {

				int id = rs.getInt("id");
				posts posts = new posts();
				post post = new post();
				users users = new users();
				post.setId(rs.getInt("id"));
				post.setUser_id(rs.getInt("user_id"));
				post.setDate(rs.getString("date"));
				post.setText(rs.getString("text"));
				post.setPhoto(rs.getString("photo"));
				users.setEmail(rs.getString("email"));
				users.setName(rs.getString("name"));
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
			// ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_users INNER JOIN
			// tbl_follow ON tbl_users.id = tbl_follow.followed_id where
			// tbl_users.id="+u.getId()+" ORDER BY tbl_users.id DESC ;");
			ResultSet rs = stmt.executeQuery(
					"select * from tbl_users\r\n" + "inner join tbl_follow on tbl_users.id = tbl_follow.follower_id\r\n"
							+ "where tbl_follow.followed_id = " + u.getId()); // where tbl_users.id="+u.getId()+" ORDER
																				// BY tbl_users.id DESC ;");
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
			// ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_users INNER JOIN
			// tbl_follow ON tbl_users.id = tbl_follow.followed_id where
			// tbl_users.id="+u.getId()+" ORDER BY tbl_users.id DESC ;");
			ResultSet rs = stmt.executeQuery(
					"select * from tbl_users\r\n" + "inner join tbl_follow on tbl_users.id = tbl_follow.followed_id\r\n"
							+ "where tbl_follow.follower_id =" + u.getId()); // where tbl_users.id="+u.getId()+" ORDER
																				// BY tbl_users.id DESC ;");
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

		users users = new users();
		users.setId(2);

		System.out.println(new DBConnection().select_followed(users));
		// new DBConnection().search_users("me@gmail.c");
		// users users = new users();
		// users.setId(2);
		// new DBConnection().select_followers(users);
	}

}
