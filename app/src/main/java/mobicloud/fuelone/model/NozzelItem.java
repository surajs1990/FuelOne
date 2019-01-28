package mobicloud.fuelone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Suraj Shakya on 07/08/2018.
 */

public class NozzelItem implements Serializable {

    @SerializedName("nozzelName")
    @Expose
    private String nozzelName;

    @SerializedName("nozzel_entry")
    @Expose
    private String nozzel_entry;

    @SerializedName("nozzel_id")
    @Expose
    private String nozzel_id;

    public String getNozzelName() {
        return nozzelName;
    }

    public void setNozzelName(String nozzelName) {
        this.nozzelName = nozzelName;
    }

    public String getNozzel_entry() {
        return nozzel_entry;
    }

    public void setNozzel_entry(String nozzel_entry) {
        this.nozzel_entry = nozzel_entry;
    }

    public String getNozzel_id() {
        return nozzel_id;
    }

    public void setNozzel_id(String nozzel_id) {
        this.nozzel_id = nozzel_id;
    }

    @Override
    public String toString() {
        return "NozzelItem{" +
                "nozzelName='" + nozzelName + '\'' +
                ", nozzel_entry='" + nozzel_entry + '\'' +
                ", nozzel_id='" + nozzel_id + '\'' +
                '}';
    }
}
