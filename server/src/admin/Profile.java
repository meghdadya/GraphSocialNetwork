package admin;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import model.users;

public class Profile implements Serializable{
	
	users user  = new users();
	public Vertex profileVertex;
	public FriendsGraph graph=new FriendsGraph();
	public ArrayList<Profile> followers=new ArrayList<Profile>();
	public ArrayList<Profile> followings=new ArrayList<Profile>();

	public ArrayList<Profile> getFollowers() {
		return followers;
	}
	public void setFollowers(ArrayList<Profile> followers) {
		this.followers = followers;
	}
	public ArrayList<Profile> getFollowings() {
		return followings;
	}
	public void setFollowings(ArrayList<Profile> followings) {
		this.followings = followings;
	}
	public Profile(users users) {
		super();
		this.user = users;
		profileVertex=new Vertex(this);
		this.graph.source=profileVertex;
		this.graph.vertexs.add(profileVertex);
	}

}
