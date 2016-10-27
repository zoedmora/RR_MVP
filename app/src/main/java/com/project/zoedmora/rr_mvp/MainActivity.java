package com.project.zoedmora.rr_mvp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
    private TextView restaurantInfo;// = (TextView) findViewById(R.id.textbox);
    private Button searchButton;

    private ProvidedPresenterOps presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restaurantInfo = (TextView) findViewById(R.id.myTextView);

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

        }
    }

    @Override
    public void showSearchResults(Business business, String msg) {
        //Putting text information onto Screen
        if (business != null) {
            //Changing Info
            restaurantInfo.setText(business.name() + " " + business.rating());

            //Changing Picture
            try {//We should do this in a different thread but to avoid it we can do the following strict thing

                //This is to avoid Error from Output Streams
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);


                URL yelpPicture = new URL(business.imageUrl());
                Bitmap urlStream = BitmapFactory.decodeStream(yelpPicture.openConnection().getInputStream());
                restaurantPic.setImageBitmap(urlStream);
            }
            catch (Exception e) {

                restaurantInfo.setText(e.toString());
                e.printStackTrace();
            }

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
        String uri = String.format(Locale.ENGLISH, "geo:0,0?q=" + address );
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

}