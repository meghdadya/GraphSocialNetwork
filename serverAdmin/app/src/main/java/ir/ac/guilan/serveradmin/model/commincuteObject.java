package ir.ac.guilan.serveradmin.model;

import java.util.List;

public class commincuteObject {//fill the commincuteObject  constructor
    //commincuteObject = object have all detail for example follow,post,notification,like,email...

    private message Message;
    private List<follow> Follow;
    private List<post> Post;
    private List<users> Users;
    private List<posts> posts;
    private List<user_notifications> notificationsList;
    private List<graphNodes> graphNodesList;

    public message getMessage() {
        return Message;
    }

    public void setMessage(message message) {
        Message = message;
    }

    public List<follow> getFollow() {
        return Follow;
    }

    public void setFollow(List<follow> follow) {
        Follow = follow;
    }

    public List<post> getPost() {
        return Post;
    }

    public void setPost(List<post> post) {
        Post = post;
    }

    public List<users> getUsers() {
        return Users;
    }

    public void setUsers(List<users> users) {
        Users = users;
    }

    public List<posts> getPosts() {
        return posts;
    }

    public void setPosts(List<posts> posts) {
        this.posts = posts;
    }

    public List<user_notifications> getNotificationsList() {
        return notificationsList;
    }

    public void setNotificationsList(List<user_notifications> notificationsList) {
        this.notificationsList = notificationsList;
    }

    public List<graphNodes> getGraphNodesList() {
        return graphNodesList;
    }

    public void setGraphNodesList(List<graphNodes> graphNodesList) {
        this.graphNodesList = graphNodesList;
    }
}
