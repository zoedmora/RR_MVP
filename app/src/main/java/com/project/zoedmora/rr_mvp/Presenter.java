package com.project.zoedmora.rr_mvp;









import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.clientlib.entities.options.CoordinateOptions;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by zoedmora on 10/21/16.
 */

public class Presenter implements ProvidedPresenterOps, RequiredPresenterOps{

    private ProvidedModelOps model;
    private RequiredViewOps view;//WeakReference<RequiredViewOps> view;
    //private Restaurant restaurant;



    //Constructor
    public Presenter(RequiredViewOps viewFromActivity) {

        view = viewFromActivity;//new WeakReference<>(viewFromActivity);
        //restaurant = new Restaurant();
    }

    public void setModel(ProvidedModelOps modelC) {
        model = modelC;
    }




    @Override
    public void search() {

        try{
             new AsyncTask<Void, String, Restaurant>() {
                    String show = "before";
                 @Override
                 protected Restaurant doInBackground(Void... param) {
                     //return restaurant.searchForRestaurant();
                     return model.getSearchedRestaurant();
                 }

                 @Override
                 protected void onPostExecute(Restaurant restaurant){
                     view.showSearchResults(restaurant, "Error");
                     //model.saveSearchResults(restaurant);
                 }
             }.execute();

        }
        catch(Exception e){
            e.printStackTrace();

            //testing error
            String show = e.toString();
            view.editText(show);
        }

    }

    @Override
    public void openMap(){
        //This is so that the google app opens up
        String address = model.getAddress();
        view.showMap(address);
    }

    public void restaurantWebSearch(){
        view.showWebSearch(model.getRestaurantsWebStatement());
    }















}
