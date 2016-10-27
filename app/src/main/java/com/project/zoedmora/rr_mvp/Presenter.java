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



    //Constructor
    public Presenter(RequiredViewOps viewFromActivity) {

        view = viewFromActivity;//new WeakReference<>(viewFromActivity);
    }

    public void setModel(ProvidedModelOps modelC) {
        model = modelC;
    }




    @Override
    public void search() {

            //view.editText("Trying Here");
        try{
             new AsyncTask<Void, String, Business>() {
                    String show = "before";
                 @Override
                 protected Business doInBackground(Void... param) {

                     //This is to avoid Error from Output Streams
                     StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                     StrictMode.setThreadPolicy(policy);

                     String consumerKey = "ILXyuZ1tUfXNEXmiko5eBg";
                     String consumerSecret = "8FwC9cN6tB4NOMfPfMkF8d4w9M0";
                     String token = "L_qovPrpu3UMHjspXgvKrcIiMjM69_l0";
                     String tokenSecret = "aUMm51QO2J1g5ohk5FeQc0nB7Hc";
                     //String show = "Clicked It";                             //for testing only
                     int totalCount = 0;                                     //count of restaurants from search
                     ArrayList<Business> business = null;                    //List of all businesses returned from search
                     Random random = new Random();                           //used to create a random number
                     int rNumber = (Math.abs(random.nextInt()) % 20);        //the random number used to pick from the list of businesses



                     //Signing in to Yelp API
                     YelpAPIFactory apiFactory = new YelpAPIFactory(consumerKey, consumerSecret, token, tokenSecret);
                     YelpAPI yelpAPI = apiFactory.createAPI();


                     //Parameters for Yelp Search
                     Map<String, String> paramss = new HashMap<>();
                     paramss.put("term", " restaurants");
                     paramss.put("radius_filter", "2000");
                     paramss.put("limit", "20");


                     //Coordinates for Yelp Search
                     //askPermissionsAndShowMyLocation();
                     CoordinateOptions coordinate = CoordinateOptions.builder().latitude(37.7577).longitude(-122.4376).build();
                     //CoordinateOptions coordinate = CoordinateOptions.builder().latitude(latitude).longitude(longitude).build();



                     //Building the Yelp Search
                     Call<SearchResponse> call = yelpAPI.search(coordinate, paramss);
                     //Call<SearchResponse> call = yelpAPI.search("San Francisco",params);


                     //Excecuting Yelp Search
                     try {
                         Response<SearchResponse> response = call.execute();
                         SearchResponse restaurant = response.body();

                         //totalCount = restaurant.total();        //total count of restaurants returned from Search
                         business = restaurant.businesses();     //get the list of all Businesses

                     } catch (Exception e) {
                         show = e.toString();
                         //show = "Error Fool!";
                     }

                     //view.editText(show);
                     //view.editText("Secondary");


                     //model.getSearchResults(business.get(rNumber);
                     if(business != null) {
                         //view.showSearchResults(business.get(rNumber), show);
                     }

                        return business.get(rNumber);

                 }




                 @Override
                 protected void onPostExecute(Business business){
                    // view.editText("lastly");
                    // view.editText(show);
                     view.showSearchResults(business, "Error");
                     model.saveSearchResults(business);
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
        String address = model.getAdress();

        //This is so that the google app opens up
       view.showMap(address);
    }


















      /*
    private RequiredViewOps getView() throws NullPointerException{
        if ( view != null )
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }  */
}
