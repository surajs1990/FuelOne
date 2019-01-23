package mobicloud.fuelone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Suraj Shakya on 07/08/2018.
 */

public class FileModel implements Serializable {

    @SerializedName("dipChartName")
    @Expose
    private String dipChartName;

    @SerializedName("dipChartURL")
    @Expose
    private String dipChartURL;

    public String getDipChartName() {
        return dipChartName;
    }

    public void setDipChartName(String dipChartName) {
        this.dipChartName = dipChartName;
    }

    public String getDipChartURL() {
        return dipChartURL;
    }

    public void setDipChartURL(String dipChartURL) {
        this.dipChartURL = dipChartURL;
    }

    @Override
    public String toString() {
        return "FileModel{" +
                "dipChartName='" + dipChartName + '\'' +
                ", dipChartURL='" + dipChartURL + '\'' +
                '}';
    }
}
