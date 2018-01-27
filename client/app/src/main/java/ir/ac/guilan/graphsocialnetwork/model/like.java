package ir.ac.guilan.graphsocialnetwork.model;

/**
 * Created by gilaceco on 1/27/18.
 */

public class like {
    int id;
    int user_id;
    int post_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }
}
