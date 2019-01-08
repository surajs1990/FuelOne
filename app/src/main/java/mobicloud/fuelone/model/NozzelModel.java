package mobicloud.fuelone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Suraj Shakya on 07/08/2018.
 */

public class NozzelModel implements Serializable {

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("nozzel_id")
    @Expose
    private String nozzel_id;

    @SerializedName("nozzel_name")
    @Expose
    private String nozzel_name;

    @SerializedName("nozzel_title")
    @Expose
    private String nozzel_title;

    public String getNozzel_id() {
        return nozzel_id;
    }

    public void setNozzel_id(String nozzel_id) {
        this.nozzel_id = nozzel_id;
    }

    public String getNozzel_name() {
        return nozzel_name;
    }

    public void setNozzel_name(String nozzel_name) {
        this.nozzel_name = nozzel_name;
    }

    public String getNozzel_title() {
        return nozzel_title;
    }

    public void setNozzel_title(String nozzel_title) {
        this.nozzel_title = nozzel_title;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "NozzelModel{" +
                "user_id='" + user_id + '\'' +
                ", nozzel_id='" + nozzel_id + '\'' +
                ", nozzel_name='" + nozzel_name + '\'' +
                ", nozzel_title='" + nozzel_title + '\'' +
                '}';
    }
}
