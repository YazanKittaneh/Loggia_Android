package com.loggia.Utils;

import com.loggia.R;

import java.util.HashMap;

/**
 * Created by L7 on 2/11/16.
 */
public class CategoryMap {
    HashMap<Enum, Boolean> CategoryMap;

    public CategoryMap() {
        for (Enum filter : Constants.FilterOptions.values()) {
            CategoryMap.put(filter, false);
        }
    }

    public void put(Enum key, boolean value){
        this.put(key,value);
    }

}
