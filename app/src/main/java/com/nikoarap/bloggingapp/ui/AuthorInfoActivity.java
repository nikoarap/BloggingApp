package com.nikoarap.bloggingapp.ui;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nikoarap.bloggingapp.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class AuthorInfoActivity extends AppCompatActivity {

    public TextView name_txt;
    public TextView userName_txt;
    public TextView email_txt;
    public TextView address_txt;
    public CircleImageView authorImg;
    public ImageButton backButton;
    private String authorId;
    private String authorName;
    private String authorAvatar;
    private String authorUserName;
    private String authorEmail;
    private String authorAddressLat;
    private String authorAddressLng;
    private Double latitude;
    private Double longitude;
    private String address, city, state, country, postalCode, knownName;
    List<Address> addresses;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.author_info_layout);

        Intent i = getIntent();
        authorId = i.getStringExtra("authorId");
        authorName = i.getStringExtra("authorName");
        authorAvatar = i.getStringExtra("authorAvatar");
        authorUserName = i.getStringExtra("authorUserName");
        authorEmail = i.getStringExtra("authorEmail");
        authorAddressLat = i.getStringExtra("authorAddressLat");
        authorAddressLng = i.getStringExtra("authorAddressLng");

        name_txt = (TextView) findViewById(R.id.nameInfoTxt);
        userName_txt = (TextView) findViewById(R.id.usernameInfoTxt);
        email_txt = (TextView) findViewById(R.id.emailInfoTxt);
        address_txt = (TextView) findViewById(R.id.addressInfoTxt);
        authorImg = (CircleImageView) findViewById(R.id.imageInfo);
        backButton = (ImageButton) findViewById(R.id.backbuttonInfo);

        name_txt.setText(authorName);
        userName_txt.setText(authorUserName);
        email_txt.setText(authorEmail);

        latitude = Double.parseDouble(authorAddressLat);
        longitude = Double.parseDouble(authorAddressLng);


        //convert latLng to address
        Geocoder geocoder;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,"asdasd",Toast.LENGTH_LONG).show();
        }

        if(addresses.size() == 0){
            address_txt.setText("Address not listed");
        }
        else{
            address = addresses.get(0).getAddressLine(0);
            address_txt.setText(address);

        }

        Glide.with(this)
                .asBitmap()
                .load(authorAvatar)
                .into(authorImg);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //going to the previous activity
            }
        });

    }


}
