package ir.ac.guilan.serveradmin.model;

public class graphNodes {//fill the graph constructor
    int id;
    String follow;
    String followback;
    String email;
    String bio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getFollowback() {
        return followback;
    }

    public void setFollowback(String followback) {
        this.followback = followback;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
