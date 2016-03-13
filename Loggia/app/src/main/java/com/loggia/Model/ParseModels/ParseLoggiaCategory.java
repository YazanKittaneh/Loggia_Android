package com.loggia.Model.ParseModels;

import com.loggia.Interfaces.LoggiaCategory;
import com.loggia.Interfaces.LoggiaUser;
import com.loggia.Utils.TableData;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;

/**
 * Created by albertowusu-asare on 3/10/16.
 */
@ParseClassName("CATEGORY")
public class ParseLoggiaCategory extends ParseObject implements LoggiaCategory {
    ParseLoggiaCategory category;
    public ParseLoggiaCategory(){
        super();
        category = this;
    }

    @Override
    public String getCategoryName() {
        return category.getString(TableData.CategoryColumnNames.category_name.toString());
    }

    @Override
    public int getCategoryId() {
        return category.getInt(TableData.CategoryColumnNames.category_id.toString());
    }
}
