package ir.ac.guilan.graphsocialnetwork.model;

/**
 * Created by gilaceco on 1/17/18.
 */

public class posts {

    post post;
    users users;

    public post getPost() {
        return post;
    }

    public void setPost(post post) {
        this.post = post;
    }

    public users getUsers() {
        return users;
    }

    public void setUsers(users users) {
        this.users = users;
    }
}
