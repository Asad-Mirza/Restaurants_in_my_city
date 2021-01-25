package javabeans;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class Cities{

    public List<LocationSuggestion> location_suggestions;
    public String status;
    public int has_more;

    public List<LocationSuggestion> getLocation_suggestions() {
        return location_suggestions;
    }

    public String getStatus() {
        return status;
    }

    public int getHas_more() {
        return has_more;
    }

    public int getHas_total() {
        return has_total;
    }

    public boolean isUser_has_addresses() {
        return user_has_addresses;
    }

    public int has_total;

    public Cities(List<LocationSuggestion> location_suggestions, String status, int has_more, int has_total, boolean user_has_addresses) {
        this.location_suggestions = location_suggestions;
        this.status = status;
        this.has_more = has_more;
        this.has_total = has_total;
        this.user_has_addresses = user_has_addresses;
    }

    public boolean user_has_addresses;
}