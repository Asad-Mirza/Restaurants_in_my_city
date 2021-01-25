package javabeans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Asad Mirza on 25-01-2021.
 */

public class Section implements Serializable {
    String header;
    ArrayList<REAL_restaurant> restaurantArrayList;

    public Section(String header, ArrayList<REAL_restaurant> restaurantArrayList) {
        this.header = header;
        this.restaurantArrayList = restaurantArrayList;
    }

    @Override
    public String toString() {
        return "\n\nSection{" +
                "header='" + header + '\'' +
                ", restaurantArrayList=" + restaurantArrayList +
                '}';
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public ArrayList<REAL_restaurant> getRestaurantArrayList() {
        return restaurantArrayList;
    }

    public void setRestaurantArrayList(ArrayList<REAL_restaurant> restaurantArrayList) {
        this.restaurantArrayList = restaurantArrayList;
    }
}
