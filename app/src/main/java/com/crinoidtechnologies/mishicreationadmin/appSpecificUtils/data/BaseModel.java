package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.data;

import java.io.Serializable;

/**
 * Created by ${Vivek} on 5/4/2016 for Avante.Be careful
 */
public interface BaseModel extends Serializable, Cloneable {
    String getUniqueId();
  //  String getJsonString();
    boolean containSearchString(String searchString);
}
