package com.example.ibm_proj2;


import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class BarGraph extends AppCompatActivity {

    BarChart barChart;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_graph);
        myDb = new DatabaseHelper(this);
        barChart = findViewById(R.id.barGraph);

        BarDataSet barDataSet1 = new BarDataSet(dataValues(), "Crutch Words Used");

        BarData barData = new BarData();
        barData.addDataSet(barDataSet1);

        barChart.setData(barData);
        barChart.invalidate();
    }


    private ArrayList<BarEntry> dataValues(){

        ArrayList<BarEntry> dataVals = new ArrayList<>();

        for(int i=0; i<=5; i+=1){
            int max=6;
            int min=0;
            double r_r = Math.random() * ( max - min );

            int r = (int)r_r;

            int sum = myDb.select(r);

            dataVals.add(new BarEntry(r,sum));

        }

        return dataVals;
    }

}