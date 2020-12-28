package com.example.ibm_proj2;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class GraphActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName,editTopic,editTextId;

    PieChart pieChart;
    Button next;
    int[] colorClassArray = new int[]{Color.LTGRAY, Color.MAGENTA, Color.CYAN, Color.YELLOW, Color.GREEN, Color.RED};
    private String mLanguageCode = "kn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        findViewById(R.id.btnChangeLangView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Change Application level locale
                LocaleHelper.setLocale(GraphActivity.this, mLanguageCode);

                //It is required to recreate the activity to reflect the change in UI.
                recreate();
            }
        });

        myDb = new DatabaseHelper(this);
        Bundle bundle = getIntent().getExtras();
        final String username = bundle.getString("username");


        editName = findViewById(R.id.Name);
        editTopic = findViewById(R.id.Topic);

        next = findViewById(R.id.button6);

        pieChart = findViewById(R.id.pieChart);
        TextView t = findViewById(R.id.Sum);
        ArrayList<PieEntry> dataVals = new ArrayList<PieEntry>();

        if(capture.count!=0){
            dataVals.add(new PieEntry(capture.count,"Slighlty/ಮೂಲತಃ"));
        }
        if(capture.count1!=0){
            dataVals.add(new PieEntry(capture.count1,"Definitely/ಖಂಡಿತವಾಗಿ"));
        }
        if(capture.count2!=0){
            dataVals.add(new PieEntry(capture.count2,"Certainly/ಕೆಲವು"));
        }
        if(capture.count3!=0){
            dataVals.add(new PieEntry(capture.count3,"Probably/ಅದಕ್ಕಾಗಿ"));
        }
        if(capture.count4!=0){
            dataVals.add(new PieEntry(capture.count4,"Actually/ವಾಸ್ತವವಾಗಿ"));
        }
        if(capture.count5!=0){
            dataVals.add(new PieEntry(capture.count5,"Basically/ನಿಜವಾಗಿಯೂ"));
        }



        final int Total = capture.count+capture.count1+capture.count2+capture.count3+capture.count4+capture.count5;
        PieDataSet pieDataSet = new PieDataSet(dataVals, "");
        pieDataSet.setColors(colorClassArray);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setDrawEntryLabels(true);
        pieChart.setUsePercentValues(false);
        pieChart.setCenterText("Crutch Words");
        pieChart.setCenterTextSize(20);
        pieChart.setCenterTextRadiusPercent(50);
        pieChart.setHoleRadius(50);
        //pieChart.setTransparentCircleRadius(40);

        pieChart.setData(pieData);
        pieChart.invalidate();

        t.setText(String.valueOf(Total));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isUpdate = myDb.updatesum(username,Total);
                if(isUpdate == true)
                    Toast.makeText(GraphActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(GraphActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(GraphActivity.this, BarGraph.class);
                startActivity(intent);

            }
        });



    }
}