package com.project.zoedmora.rr_mvp;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.clientlib.entities.options.CoordinateOptions;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by zoedmora on 11/28/16.
 */

public class Restaurant {

    String name;
    String address;
    String stylePrefered;
    String distancePrefered;
    String city;
    String phoneNumber;
    String searchStatement;
    double rating;


    Bitmap pic;
    URL yelpPicture;

    String error;

    Restaurant(){
        stylePrefered = "restaurant"; distancePrefered = "2000";  name = "";  address = "";
    }

    Restaurant(String name, String address, String phoneNumber, double rating){
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public double getRating(){
        return rating;
    }

    public void setInfo(String n, String a){
        name = n;
        address = a;
    }

    public Restaurant searchForRestaurant(){

        //This is to avoid Error from Output Streams
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        //Yelp Credentials
        String consumerKey = "ILXyuZ1tUfXNEXmiko5eBg";
        String consumerSecret = "8FwC9cN6tB4NOMfPfMkF8d4w9M0";
        String token = "L_qovPrpu3UMHjspXgvKrcIiMjM69_l0";
        String tokenSecret = "aUMm51QO2J1g5ohk5FeQc0nB7Hc";
        ArrayList<Business> business = null;                    //List of all businesses returned from search
        Random random = new Random();                           //used to create a random number
        int rNumber = (Math.abs(random.nextInt()) % 20);        //the random number used to pick from the list of businesses
        Business resultRestaurant;

        //Signing in to Yelp API
        YelpAPIFactory apiFactory = new YelpAPIFactory(consumerKey, consumerSecret, token, tokenSecret);
        YelpAPI yelpAPI = apiFactory.createAPI();


        //Parameters for Yelp Search
        Map<String, String> paramss = new HashMap<>();
        paramss.put("term", stylePrefered);                 //paramss.put("term", " restaurants");
        paramss.put("radius_filter", distancePrefered);     //radius_filter", "2000");
        paramss.put("limit", "20");


        //Coordinates for Yelp Search
        CoordinateOptions coordinate = CoordinateOptions.builder().latitude(37.7577).longitude(-122.4376).build();


        //Building the Yelp Search
        Call<SearchResponse> call = yelpAPI.search(coordinate, paramss);


        //Excecuting Yelp Search
        try {
            Response<SearchResponse> response = call.execute();
            SearchResponse restaurant = response.body();

            //totalCount = restaurant.total();        //total count of restaurants returned from Search
            business = restaurant.businesses();     //get the list of all Businesses
            resultRestaurant = business.get(rNumber);

            //Fill In All Class Attributes
            name =   resultRestaurant.name();
            rating = resultRestaurant.rating();
            city = resultRestaurant.location().city();
            phoneNumber = resultRestaurant.displayPhone();
            yelpPicture = new URL(resultRestaurant.imageUrl());
            address = business.get(rNumber).location().address().get(0) + ", ";
                    address += business.get(rNumber).location().city() + ", ";
                    address += business.get(rNumber).location().stateCode();
            searchStatement = name + " restaurant in " + city;

        } catch (Exception e) {
            error = e.toString();
        }

        return this;

    }

    public Bitmap getPic(){

        //URL yelpPicture = new URL(business.imageUrl());
        try {
            pic = BitmapFactory.decodeStream(yelpPicture.openConnection().getInputStream());
        }
        catch (Exception e){e.printStackTrace();};

        return pic;
    }

    public String printRestaurantInfo(){
        return phoneNumber + "\n\nRating:  " + rating;
    }

    public String getSearchStatement(){
        return searchStatement;
    }

}
