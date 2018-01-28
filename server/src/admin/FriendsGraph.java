package admin;

import java.io.Serializable;
import java.util.ArrayList;


public class FriendsGraph implements Serializable{
	public Vertex source;
	public ArrayList<Vertex> vertexs=new ArrayList<Vertex>();
	public ArrayList<Edge> edges=new ArrayList<Edge>();
	public ArrayList<ArrayList<Vertex>> adjList=new ArrayList<ArrayList<Vertex>>();
	
	
}
