package ir.ac.guilan.serveradmin.graph;

import java.io.Serializable;
import java.util.ArrayList;

public class Vertex implements Serializable {//graph node
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
