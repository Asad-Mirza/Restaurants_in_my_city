package javabeans;

import java.io.Serializable;

/**
 * Created by Asad Mirza on 24-01-2021.
 */

public class LocationSuggestion implements Serializable {
    public int id;
    public String name;
    public int country_id;
    public String country_name;
    public String country_flag_url;
    public int should_experiment_with;
    public int has_go_out_tab;
    public int discovery_enabled;
    public int has_new_ad_format;
    public int is_state;
    public int state_id;
    public String state_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_flag_url() {
        return country_flag_url;
    }

    public void setCountry_flag_url(String country_flag_url) {
        this.country_flag_url = country_flag_url;
    }

    public int getShould_experiment_with() {
        return should_experiment_with;
    }

    public void setShould_experiment_with(int should_experiment_with) {
        this.should_experiment_with = should_experiment_with;
    }

    public int getHas_go_out_tab() {
        return has_go_out_tab;
    }

    public void setHas_go_out_tab(int has_go_out_tab) {
        this.has_go_out_tab = has_go_out_tab;
    }

    public int getDiscovery_enabled() {
        return discovery_enabled;
    }

    public void setDiscovery_enabled(int discovery_enabled) {
        this.discovery_enabled = discovery_enabled;
    }

    public int getHas_new_ad_format() {
        return has_new_ad_format;
    }

    public void setHas_new_ad_format(int has_new_ad_format) {
        this.has_new_ad_format = has_new_ad_format;
    }

    public int getIs_state() {
        return is_state;
    }

    public void setIs_state(int is_state) {
        this.is_state = is_state;
    }

    public int getState_id() {
        return state_id;
    }

    public void setState_id(int state_id) {
        this.state_id = state_id;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    @Override
    public String toString() {
        return name;
    }

    public String state_code;
}