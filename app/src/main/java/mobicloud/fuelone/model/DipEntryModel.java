package mobicloud.fuelone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Suraj Shakya on 07/08/2018.
 */

public class DipEntryModel implements Serializable {

    @SerializedName("UserId")
    @Expose
    private String UserId;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("shift")
    @Expose
    private String shift;

    @SerializedName("tanks")
    @Expose
    private ArrayList<MapingModel> tanks;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public ArrayList<MapingModel> getTanks() {
        return tanks;
    }

    public void setTanks(ArrayList<MapingModel> tanks) {
        this.tanks = tanks;
    }

    @Override
    public String toString() {
        return "DipEntryModel{" +
                "UserId='" + UserId + '\'' +
                ", date='" + date + '\'' +
                ", shift='" + shift + '\'' +
                ", tanks=" + tanks +
                '}';
    }
}
