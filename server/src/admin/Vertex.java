package admin;

import java.io.Serializable;
import java.util.ArrayList;

import model.users;

public class Vertex implements Serializable {
	public Profile profile;
	public ArrayList<Vertex> followings = new ArrayList<Vertex>();

	public Vertex(Profile profile, Vertex add) {
		this.profile = profile;
		followings.add(add);
	}

	public Vertex(Profile profile) {
		super();
		this.profile = profile;
	}

}
