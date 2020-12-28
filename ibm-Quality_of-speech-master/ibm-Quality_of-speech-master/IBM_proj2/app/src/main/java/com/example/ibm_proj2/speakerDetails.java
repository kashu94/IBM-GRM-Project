package com.example.ibm_proj2;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class speakerDetails<editName, editId, editTopic> extends AppCompatActivity implements LocationListener {
    //public static final String id editId;
    DatabaseHelper myDb;
    EditText editId,editName,editTopic;





    private final int REQUEST_LOCATION_PERMISSION = 1;
    LocationManager locationManager;
    TextView locationS;
    Button getloc;
    Button next;
    //ProgressBar circle;


    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, h:mm:a");
    String date = df.format(Calendar.getInstance().getTime());

    private String mLanguageCode = "kn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_details);

        findViewById(R.id.btnChangeLangView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Change Application level locale
                LocaleHelper.setLocale(speakerDetails.this, mLanguageCode);

                //It is required to recreate the activity to reflect the change in UI.
                recreate();
            }
        });

        myDb = new DatabaseHelper(this);
        editName = findViewById(R.id.Name);
        editTopic = findViewById(R.id.Topic);


        locationS = findViewById(R.id.address);
        getloc = findViewById(R.id.btnlocation);
        next = findViewById(R.id.button5);
        TextView d = findViewById(R.id.text_view_date);
        d.setText(date);

        next =findViewById(R.id.button5);




        if (ContextCompat.checkSelfPermission(speakerDetails.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(speakerDetails.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }



            getloc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);



                    locationEnabled();
                    //circle.setVisibility(View.VISIBLE);
                    //if (locationS.getText()!="") circle.setVisibility(View.INVISIBLE);

                    getLocation();

                }
            });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(editName.getText().toString(), editTopic.getText().toString(), date, null);
                if(isInserted == true)
                    Toast.makeText(speakerDetails.this,"Data Inserted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(speakerDetails.this,"Data not Inserted",Toast.LENGTH_LONG).show();

                Intent i = new Intent(speakerDetails.this, capture.class);
                Bundle bundle = new Bundle();
                bundle.putString("username",editName.getText().toString());
                i.putExtras(bundle);
                startActivity(i);

            }
        });

        }



    private void locationEnabled() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!gps_enabled && !network_enabled) {
            new AlertDialog.Builder(speakerDetails.this)
                    .setTitle("Enable GPS Service")
                    .setMessage("We need your GPS location")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, (LocationListener) this);



        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            locationS.setText(addresses.get(0).getAddressLine(0));

        } catch (Exception e) {
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }



}


