package com.example.cooktogether;

import java.util.ArrayList;

public class Filters {
   ArrayList<String> ActiveFilters;
    public Filters(ArrayList<String>filters){
        for(String filter:filters){
            this.ActiveFilters.add(filter);
        }
    }
    public void addFilter(String filter) {
        int count=0;
        for(String filterIn:this.ActiveFilters){
            if(filter.equals(filterIn)){
                count++;
            }
        }
        if(count==0){
            ActiveFilters.add(filter);
        }
    }

    public ArrayList<String> getActiveFilters() {
        return ActiveFilters;
    }
}
