package mobicloud.fuelone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Suraj Shakya on 07/08/2018.
 */

public class MapingModel implements Serializable {

    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("tank_id")
    @Expose
    private String tank_id;

    @SerializedName("tankName")
    @Expose
    private String tankName;

    @SerializedName("nozzel_name")
    @Expose
    private String nozzel_name;

    @SerializedName("fuletype")
    @Expose
    private String fuletype;

    @SerializedName("capacity")
    @Expose
    private String capacity;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTank_id() {
        return tank_id;
    }

    public void setTank_id(String tank_id) {
        this.tank_id = tank_id;
    }

    public String getTankName() {
        return tankName;
    }

    public void setTankName(String tankName) {
        this.tankName = tankName;
    }

    public String getNozzel_name() {
        return nozzel_name;
    }

    public void setNozzel_name(String nozzel_name) {
        this.nozzel_name = nozzel_name;
    }

    public String getFuletype() {
        return fuletype;
    }

    public void setFuletype(String fuletype) {
        this.fuletype = fuletype;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "MapingModel{" +
                "userId='" + userId + '\'' +
                ", tank_id='" + tank_id + '\'' +
                ", tankName='" + tankName + '\'' +
                ", nozzel_name='" + nozzel_name + '\'' +
                ", fuletype='" + fuletype + '\'' +
                ", capacity='" + capacity + '\'' +
                '}';
    }
}
