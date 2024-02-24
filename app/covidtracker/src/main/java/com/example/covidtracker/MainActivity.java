package com.example.covidtracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hbb20.CountryCodePicker;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    CountryCodePicker countryCodePicker;
    TextView mtodaytotal, mtotal, mactive, mtodayactive, mrecovered, mtodayrecovered, mdeaths, mtotaldeaths;

    String country;
    TextView mfilter;
    Spinner spinner;
    String[] types = {"cases", "deaths", "recovered", "active"};
    private List<ModelClass> modelClassList;
    private List<ModelClass> modelClassList2;
    PieChart mPieChart;
    ImageButton btnBack;
    private RecyclerView recyclerView;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_tracker_activity);
        getSupportActionBar().hide();

        countryCodePicker = findViewById(R.id.ccp);
        mtodaytotal = findViewById(R.id.todaytotal);
        mtotal = findViewById(R.id.totalcase);
        mactive = findViewById(R.id.activecase);
        mtodayactive = findViewById(R.id.todayactive);
        mrecovered = findViewById(R.id.recoveredcase);
        mdeaths = findViewById(R.id.todaydeath);
        mtotaldeaths = findViewById(R.id.totaldeath);
        mtodayrecovered = findViewById(R.id.todayrecovered);
        mPieChart = findViewById(R.id.piechart);
        spinner = findViewById(R.id.spinner);
        mfilter = findViewById(R.id.filter);
        recyclerView = findViewById(R.id.recyclerview);
        btnBack = findViewById(R.id.btnBack);
        modelClassList = new ArrayList<>();
        modelClassList2 = new ArrayList<>();

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, types);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClassName("com.example.finalproject", "com.example.finalproject.WelcomeActivity");
                startActivity(intent);
            }
        });

        ApiUtilities.getApiInterface().getCountryData().enqueue(new Callback<List<ModelClass>>() {
            @Override
            public void onResponse(Call<List<ModelClass>> call, Response<List<ModelClass>> response) {
                modelClassList2.addAll(response.body());
                //adapter.notify();
            }

            @Override
            public void onFailure(Call<List<ModelClass>> call, Throwable t) {

            }
        });

        adapter = new Adapter(getApplicationContext(), this, modelClassList2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        countryCodePicker.setAutoDetectedCountry(true);
        country = countryCodePicker.getSelectedCountryName();
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                country = countryCodePicker.getSelectedCountryName();
                fetchdata();
            }
        });

        fetchdata();

    }

    private void fetchdata() {

        ApiUtilities.getApiInterface().getCountryData().enqueue(new Callback<List<ModelClass>>() {
            @Override
            public void onResponse(Call<List<ModelClass>> call, Response<List<ModelClass>> response) {
                modelClassList.addAll(response.body());
                for(int i = 0; i < modelClassList.size(); i++)
                {
                    if(modelClassList.get(i).getCountry().equals(country))
                    {
                        mtotal.setText((modelClassList.get(i).getCases()));
                        mactive.setText((modelClassList.get(i).getActive()));
                        mrecovered.setText((modelClassList.get(i).getRecovered()));
                        mtotaldeaths.setText((modelClassList.get(i).getDeaths()));
                        mtodaytotal.setText("+" + (modelClassList.get(i).getTodayCases()));
                        mtodayactive.setText("+" + (modelClassList.get(i).getTodayCases()));
                        mtodayrecovered.setText("+" + (modelClassList.get(i).getTodayRecovered()));
                        mdeaths.setText("+" + (modelClassList.get(i).getTodayDeaths()));


                        int active, total, recovered, deaths;

                        active = Integer.parseInt(modelClassList.get(i).getActive());
                        total = Integer.parseInt(modelClassList.get(i).getCases());
                        recovered = Integer.parseInt(modelClassList.get(i).getRecovered());
                        deaths = Integer.parseInt(modelClassList.get(i).getDeaths());

                        updateGraph(active, total, recovered, deaths);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<ModelClass>> call, Throwable t) {

            }
        });

    }

    private void updateGraph(int active, int total, int recovered, int deaths) {

        mPieChart.clearChart();
        mPieChart.addPieSlice(new PieModel("Confirm", total, Color.parseColor("#FFB701")));
        mPieChart.addPieSlice(new PieModel("Active ", active, Color.parseColor("#FF4CAF50")));
        mPieChart.addPieSlice(new PieModel("Recovered", recovered, Color.parseColor("#38ACCD")));
        mPieChart.addPieSlice(new PieModel("Deaths", deaths, Color.parseColor("#F55C47")));
        mPieChart.startAnimation();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String items = types[i];
        mfilter.setText(items);
        adapter.filter(items);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
