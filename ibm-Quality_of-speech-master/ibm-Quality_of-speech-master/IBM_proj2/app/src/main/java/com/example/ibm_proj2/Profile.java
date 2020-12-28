package com.example.ibm_proj2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    ImageView imageView;
    TextView textName;
    FirebaseAuth mAuth;
    private Button button;
    private String mLanguageCode = "kn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findViewById(R.id.btnChangeLangView1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Change Application level locale
                LocaleHelper.setLocale(Profile.this, mLanguageCode);

                //It is required to recreate the activity to reflect the change in UI.
                recreate();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        imageView = findViewById(R.id.imageView);
        textName = findViewById(R.id.name);


        FirebaseUser user = mAuth.getCurrentUser();

        Glide
                .with(this)
                .load(user.getPhotoUrl())
                .apply(new RequestOptions().override(600, 200))
                .into(imageView);


        textName.setText(user.getDisplayName());
        //textEmail.setText(user.getEmail());


        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openspeakerDetails();
            }
        });
    }
    public void openspeakerDetails() {
        Intent intent = new Intent(this, speakerDetails.class);
        startActivity(intent);
    }


    @Override
    protected void onStart () {
        super.onStart();

        //if the user is not logged in
        //opening the login activity
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, com.example.ibm_proj2.MainActivity.class));
        }
    }
}