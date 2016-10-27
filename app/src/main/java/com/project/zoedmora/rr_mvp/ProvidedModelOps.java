package com.project.zoedmora.rr_mvp;

import com.yelp.clientlib.entities.Business;

/**
 * Created by zoedmora on 10/21/16.
 */

public interface ProvidedModelOps {

    void saveSearchResults(Business businesses);

    String getAdress();
}
