package admin;

import java.io.Serializable;

import model.users;

public class Edge implements Serializable {
	Profile v1, v2;

	public Edge addEdge(Profile v1, Profile v2) {
		if (v1.equals(v2)) {
			return null;
		} else {
			v1.followings.add(v2);
			return new Edge(v1, v2);
		}
	}

	public Edge(Profile v1, Profile v2) {
		super();
		this.v1 = v1;
		this.v2 = v2;

	}
}
