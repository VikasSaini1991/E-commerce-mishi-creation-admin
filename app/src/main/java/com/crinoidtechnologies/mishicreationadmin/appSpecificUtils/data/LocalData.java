package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.data;


import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.ProfileSession;
import com.crinoidtechnologies.mishicreationadmin.models.AllCategoryDatum;
import com.crinoidtechnologies.mishicreationadmin.models.RegisterData;

import java.util.ArrayList;

/**
 * Created by ${Vivek} on 7/23/2016 for MobileTrackerChat.Be careful
 */

public class LocalData extends ProfileSession.ProfileLocalData<RegisterData> {
    public ArrayList<AllCategoryDatum> categoryList = new ArrayList<>();

    public ArrayList<AllCategoryDatum> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<AllCategoryDatum> categoryList) {
        this.categoryList = categoryList;
    }
}
