package ir.ac.guilan.graphsocialnetwork.model;

/**
 * Created by gilaceco on 1/17/18.
 */

public class posts {

    int like_count;
    boolean liked;
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

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
