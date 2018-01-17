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

	public String select_users() {
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_users;");
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String age = rs.getString("email");
				System.out.println("ID = " + id);
				System.out.println("NAME = " + name);
				System.out.println("AGE = " + age);
				System.out.println();
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return "";
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

	public String select_posts() {
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_post;");
			while (rs.next()) {
				int id = rs.getInt("id");
				int user_id = rs.getInt("user_id");
				String date = rs.getString("date");
				String text = rs.getString("text");
				System.out.println("id = " + id);
				System.out.println("user_id = " + user_id);
				System.out.println("date = " + date);
				System.out.println("text = " + text);
				System.out.println();
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return "";
	}

	public List<posts> select_posts_home() {
		List<posts> postsList = new ArrayList();
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(
					"SELECT *\r\n" + "FROM tbl_post\r\n" + "INNER JOIN tbl_users ON tbl_post.user_id = tbl_users.id;");
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

	public static void main(String[] args) {
		// users users = new users();
		// users.setEmail("me@gmail.com");
		// users.setPassword("12345");
		// System.out.println(new DBConnection().login_users(users).getEmail());

		// new DBConnection().select_users();

		// post post = new post();
		// post.setDate("01/01/1336");
		// post.setText("hello world first post ever ");
		// post.setUser_id(12);
		// new DBConnection().insert_post(post);
		// new DBConnection().select_posts();

		System.out.println(new DBConnection().select_posts_home());

	}
}
