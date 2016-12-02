package com.project.zoedmora.rr_mvp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yelp.clientlib.entities.Business;

import java.net.URL;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RequiredViewOps {

    private ImageView restaurantPic;
    private TextView restaurantTitle;
    private TextView restaurantInfo;// = (TextView) findViewById(R.id.textbox);
    private TextView webSearch;
    private Button searchButton;



    private ProvidedPresenterOps presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restaurantTitle = (TextView) findViewById(R.id.title);
        restaurantInfo = (TextView) findViewById(R.id.myTextView);

        webSearch = (TextView) findViewById(R.id.webSearch);
        webSearch.setOnClickListener(this);

        searchButton = (Button) findViewById(R.id.findButton);
        searchButton.setOnClickListener(this);

        restaurantPic = (ImageView) findViewById(R.id.photo);
        restaurantPic.setOnClickListener(this);


        Presenter presenterConnection = new Presenter(this);  //Connects View and Presenter, presentor gives shares this view
        Model model = new Model(presenterConnection);          //Connects Presenter and Model,
        presenterConnection.setModel(model);

        presenter = presenterConnection;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.findButton:{
                presenter.search();
            }   break;
            case R.id.photo:{
                presenter.openMap();
            }
            case R.id.webSearch:{
                presenter.restaurantWebSearch();
            }

        }
    }

    @Override
    public void showSearchResults(Restaurant restaurant, String msg) {
        //Putting text information onto Screen
        if (restaurant != null) {

            //This is to avoid Error from Output Streams
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //Changing Info
            restaurantTitle.setText(restaurant.getName());
            restaurantInfo.setText(restaurant.printRestaurantInfo());
            restaurantPic.setImageBitmap(restaurant.getPic());
            webSearch.setText(R.string.website_search);    //"Search for Website");

        } else {
            restaurantInfo.setText(msg);
        }
    }

    @Override
    public void editText(String t){
        restaurantInfo.setText(t);
    }

    @Override
    public void showMap(String address){
        //This is so that the google app opens up
        if (address == null){address = "407 E Poppy St, Long Beach, CA";}//else{address = "long beach, CA";}

        String uri = String.format(Locale.ENGLISH, "geo:0,0?q=" + address );
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    public void showWebSearch(String statement){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/#q=" + statement));
        startActivity(browserIntent);
    }

}