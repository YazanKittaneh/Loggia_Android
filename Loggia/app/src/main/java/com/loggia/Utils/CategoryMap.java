package com.loggia.Utils;

import com.loggia.R;

import java.util.HashMap;

/**
 * Created by L7 on 2/11/16.
 * A class to handle the catagory hashmap for the filters.
 */
public class CategoryMap {
    //TODO: extend a hashmap and make this a polymorphic object

    HashMap<Enum, Boolean> CategoryMap;

    /** Constructor of the CatagoryMap. Defults all filter options to false **/
    public CategoryMap() {
        for (Enum filter : Constants.FilterOptions.values()) {
            CategoryMap.put(filter, false);
        }
    }

    /** implementation of the put function **/
    public void put(Enum key, boolean value){
        this.CategoryMap.put(key,value);
    }

}
