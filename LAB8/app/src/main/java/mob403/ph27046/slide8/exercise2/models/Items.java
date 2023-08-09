package mob403.ph27046.slide8.exercise2.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Items {
    private String uid;
    private String title;
    private String author;
    private boolean isDone;

    //  _constructor

    public Items() {
    }

    public Items(String uid, String title, String author, boolean isDone) {
        this.uid = uid;
        this.title = title;
        this.author = author;
        this.isDone = isDone;
    }

    //  _getter - setter

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    //  _Firebase: database
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("isDone", isDone);
        return result;
    }

}
