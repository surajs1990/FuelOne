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

    @SerializedName("tankDipName")
    @Expose
    private String tankDipName;

    @SerializedName("nozzel_name")
    @Expose
    private String nozzel_name;

    @SerializedName("fuletype")
    @Expose
    private String fuletype;

    @SerializedName("capacity")
    @Expose
    private String capacity;

    @SerializedName("sheet")
    @Expose
    private String sheet;

    @SerializedName("sheetUrl")
    @Expose
    private String sheetUrl;

    @SerializedName("dipentry")
    @Expose
    private String dipentry;

    public String getDipentry() {
        return dipentry;
    }

    public void setDipentry(String dipentry) {
        this.dipentry = dipentry;
    }

    public String getSheet() {
        return sheet;
    }

    public void setSheet(String sheet) {
        this.sheet = sheet;
    }

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

    public String getSheetUrl() {
        return sheetUrl;
    }

    public void setSheetUrl(String sheetUrl) {
        this.sheetUrl = sheetUrl;
    }

    public String getTankDipName() {
        return tankDipName;
    }

    public void setTankDipName(String tankDipName) {
        this.tankDipName = tankDipName;
    }

    @Override
    public String toString() {
        return "MapingModel{" +
                "userId='" + userId + '\'' +
                ", tank_id='" + tank_id + '\'' +
                ", tankDipName='" + tankDipName + '\'' +
                ", nozzel_name='" + nozzel_name + '\'' +
                ", fuletype='" + fuletype + '\'' +
                ", capacity='" + capacity + '\'' +
                ", sheet='" + sheet + '\'' +
                ", sheetUrl='" + sheetUrl + '\'' +
                ", dipentry='" + dipentry + '\'' +
                '}';
    }
}
