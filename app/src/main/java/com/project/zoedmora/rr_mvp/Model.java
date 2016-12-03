package com.project.zoedmora.rr_mvp;

import com.yelp.clientlib.entities.Business;

/**
 * Created by zoedmora on 10/21/16.
 */

public class Model implements ProvidedModelOps{
    //variables
    private Restaurant chosenBusiness;

    //presenter reference
    private RequiredPresenterOps presenter;


    //Constructor
    public Model(RequiredPresenterOps presenterConnect) {
        this.presenter = presenterConnect;       //Connects the model and presentor together, presenterConnect passes old presenter to model
        chosenBusiness = new Restaurant();
    }




    @Override
    public void saveSearchResults(Restaurant r) {
        //chosenBusiness = b;
        //setTextInView("Lets See");
        chosenBusiness = new Restaurant(r.getName(),r.getAddress(), r.getPhoneNumber(), r.getRating());

    }

    @Override
    public String getAddress(){
        String address = chosenBusiness.getAddress();
        //String address = chosenBusiness.location().address().get(0) + ", ";
         //         address += chosenBusiness.location().city() + ", ";
         //         address += chosenBusiness.location().stateCode();
        //presenter.giveAddressToView(address);
        return address;
    }

    public Restaurant getSearchedRestaurant(String style, String distance){
        return chosenBusiness.searchForRestaurant(style, distance);
    }

    public String getRestaurantsWebStatement(){
        return chosenBusiness.getSearchStatement();
    }

}
