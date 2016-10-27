package com.project.zoedmora.rr_mvp;

import com.yelp.clientlib.entities.Business;

/**
 * Created by zoedmora on 10/21/16.
 */

public class Model implements ProvidedModelOps{
    //variables
    private Business chosenBusiness;

    //presenter reference
    private RequiredPresenterOps presenter;


    //Constructor
    public Model(RequiredPresenterOps presenterConnect) {
        this.presenter = presenterConnect;       //Connects the model and presentor together, presenterConnect passes old presenter to model
    }




    @Override
    public void saveSearchResults(Business b) {
        chosenBusiness = b;
        //setTextInView("Lets See");

    }

    @Override
    public String getAdress(){
        String address = chosenBusiness.location().address().get(0) + ", ";
                  address += chosenBusiness.location().city() + ", ";
                  address += chosenBusiness.location().stateCode();
        //presenter.giveAddressToView(address);
        return address;
    }

}
