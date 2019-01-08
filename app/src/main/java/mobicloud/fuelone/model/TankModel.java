package mobicloud.fuelone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Suraj Shakya on 07/08/2018.
 */

public class TankModel implements Serializable {

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("tank_id")
    @Expose
    private String tank_id;

    @SerializedName("tank_name")
    @Expose
    private String tank_name;

    @SerializedName("tank_title")
    @Expose
    private String tank_title;

    public String getTank_id() {
        return tank_id;
    }

    public void setTank_id(String tank_id) {
        this.tank_id = tank_id;
    }

    public String getTank_name() {
        return tank_name;
    }

    public void setTank_name(String tank_name) {
        this.tank_name = tank_name;
    }

    public String getTank_title() {
        return tank_title;
    }

    public void setTank_title(String tank_title) {
        this.tank_title = tank_title;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "TankModel{" +
                "user_id='" + user_id + '\'' +
                ", tank_id='" + tank_id + '\'' +
                ", tank_name='" + tank_name + '\'' +
                ", tank_title='" + tank_title + '\'' +
                '}';
    }
}
