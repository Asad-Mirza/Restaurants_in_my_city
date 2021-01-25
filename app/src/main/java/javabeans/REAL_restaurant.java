package javabeans;

import java.io.Serializable;

/**
 * Created by Asad Mirza on 25-01-2021.
 */

public class REAL_restaurant implements Serializable {
String name;
String cuisin;
String Cityid;

    public String getCityid() {
        return Cityid;
    }

    public void setCityid(String cityid) {
        Cityid = cityid;
    }

    public String getRating() {
        return rating;
    }

    public String getCuisin() {
        return cuisin;
    }

    public void setCuisin(String cuisin) {
        this.cuisin = cuisin;
    }

    @Override
    public String toString() {
        return "R - {" +
                "name='" + name + '\'' +
                ", rating='" + rating + '\'' +
                ", timing='" + timing + '\'' +
                '}';
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    String rating;
String timing;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
