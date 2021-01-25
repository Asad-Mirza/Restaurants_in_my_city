package javabeans;

import java.util.List;

/**
 * Created by Asad Mirza on 25-01-2021.
 */





public class cuisins{
    public List<Cuisine> cuisines;

    @Override
    public String toString() {
        return "cuisins{" +
                "cuisines=" + cuisines +
                '}';
    }

    public cuisins(List<Cuisine> cuisines) {
        this.cuisines = cuisines;
    }

    public List<Cuisine> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<Cuisine> cuisines) {
        this.cuisines = cuisines;
    }
}