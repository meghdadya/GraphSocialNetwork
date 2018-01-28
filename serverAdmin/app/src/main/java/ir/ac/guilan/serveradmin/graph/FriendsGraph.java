package ir.ac.guilan.serveradmin.graph;

import java.io.Serializable;
import java.util.ArrayList;


public class FriendsGraph implements Serializable{
	public Vertex source;
	public ArrayList<Vertex> vertexs=new ArrayList<Vertex>();
	public ArrayList<Edge> edges=new ArrayList<Edge>();
	
}
