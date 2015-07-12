package models;

/**
 *
 * Created by linh on 16/05/2015.
 */
public enum MessageTypes {
    LOVE(1), NEW_YEAR(2), CHRISTMAS(3), BIRTHDAY(4), WOMEN_DAY(5);
    private int value;

    MessageTypes(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
