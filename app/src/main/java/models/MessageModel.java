package models;

/**
 *
 * Created by linh on 16/05/2015.
 */
public class MessageModel {
    public  int id;
    public  int categoryId;
    public  String content;

    public MessageModel(int id, int categoryId, String content) {
        this.id = id;
        this.categoryId = categoryId;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }
}
