package com.project.zoedmora.rr_mvp;

import com.yelp.clientlib.entities.Business;

/**
 * Created by zoedmora on 10/22/16.
 */

public interface RequiredViewOps {

    void showSearchResults(Business business, String errorMessage);

    void editText(String t);

    void showMap(String address);
}
