package ir.ac.guilan.serveradmin.graph;

import java.io.Serializable;
import java.util.ArrayList;
import ir.ac.guilan.serveradmin.model.users;

public class Profile implements Serializable {//profile had edge and vertex for fill graph

	users user = new users();
	public Vertex profileVertex;
	public FriendsGraph graph = new FriendsGraph();
	public ArrayList<Profile> followers = new ArrayList<Profile>();
	public ArrayList<Profile> followings = new ArrayList<Profile>();

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
		profileVertex = new Vertex(this);
		this.graph.source = profileVertex;
		this.graph.vertexs.add(profileVertex);
	}

}
