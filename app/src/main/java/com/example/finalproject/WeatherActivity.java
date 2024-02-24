package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity {

    EditText inputCity;
    Button btnCheck;
    ImageView imgIcon;
    ImageButton btnBack;
    TextView tempView, weatherView, feelsView, seaLvlView, latLonView, windView, cloudsView, humidityView, cityTitle;
    LinearLayout linearLayoutBg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        inputCity = findViewById(R.id.inputCity);
        btnCheck = findViewById(R.id.btnCheck);
        tempView = findViewById(R.id.tempView);
        weatherView = findViewById(R.id.weatherView);
        feelsView = findViewById(R.id.feelsView);
        seaLvlView = findViewById(R.id.seaLvlView);
        latLonView = findViewById(R.id.latLonView);
        windView = findViewById(R.id.windView);
        cityTitle = findViewById(R.id.cityTitle);
        cloudsView = findViewById(R.id.cloudsView);
        humidityView = findViewById(R.id.humidityView);
        imgIcon = findViewById(R.id.imgIcon);
        btnBack = findViewById(R.id.btnBack);

        linearLayoutBg = findViewById(R.id.linearLayoutBg);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WeatherActivity.this, WelcomeActivity.class));
            }
        });

        backEnd();
    }

    @Override
    protected void onStart() {
        super.onStart();

        String apiKey = "b120d506d16d54182fb3d399226049ca";
        String inCity = "malolos";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + inCity + "&appid=" + apiKey;

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        @SuppressLint("SetTextI18n") JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss aa", Locale.getDefault());
                SimpleDateFormat dy = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String formattedTime = df.format(c);
                String formattedDate = dy.format(c);
                cityTitle.setText(inCity.substring(0, 1).toUpperCase() + inCity.substring(1).toLowerCase() + " City Weather Updates as of \n" + formattedTime + "(GMT+8) " + formattedDate);

                JSONObject objectTemp = response.getJSONObject("main");
                String temperature = objectTemp.getString("temp");
                Double tempValue = Double.parseDouble(temperature) - 273.15;
                tempView.setText(tempValue.toString().substring(0, 4) + "°C");

                JSONObject objectFeels = response.getJSONObject("main");
                String feelsLike = objectFeels.getString("feels_like");
                Double feelsValue = Double.parseDouble(feelsLike) - 273.15;
                feelsView.setText("Feels like: " + feelsValue.toString().substring(0, 4) + "°C");

                JSONObject objectHumid = response.getJSONObject("main");
                String humidity = objectHumid.getString("humidity");
                humidityView.setText(humidity.trim() + "%");

                JSONObject objectSeaLvl = response.getJSONObject("main");
                String seaLvl = objectSeaLvl.getString("sea_level");
                Double seaLvlValue = Double.parseDouble(seaLvl) / 33.9f;
                seaLvlView.setText(seaLvlValue.toString().substring(0, 4));

                JSONObject objectLatLon = response.getJSONObject("coord");
                String latString = objectLatLon.getString("lat");
                String lonString = objectLatLon.getString("lon");
                latLonView.setText("Latitude: " + latString + ", Longitude: " + lonString);

                JSONArray arrayWeather = response.getJSONArray("weather");
                JSONObject weatherDescription = arrayWeather.getJSONObject(0);
                String weatherConditionMain = weatherDescription.getString("main");
                weatherView.setText(weatherConditionMain.substring(0, 1).toUpperCase() + weatherConditionMain.substring(1).toLowerCase());

                SimpleDateFormat timex = new SimpleDateFormat("HH", Locale.getDefault());
                String timedf = timex.format(c);

                if(weatherConditionMain.equals("Clouds"))
                {
                    if(timedf.equals("06") || timedf.equals("07") || timedf.equals("08") || timedf.equals("09") || timedf.equals("10") || timedf.equals("11") || timedf.equals("12") || timedf.equals("13") || timedf.equals("14") || timedf.equals("15") || timedf.equals("16") || timedf.equals("17"))
                    {
                        linearLayoutBg.setBackgroundResource(R.drawable.cloudbg);
                        imgIcon.setBackgroundResource(R.drawable.cloudsicon);
                    }

                    else if(timedf.equals("18") || timedf.equals("19") || timedf.equals("20") || timedf.equals("21") || timedf.equals("22") || timedf.equals("23") || timedf.equals("00") || timedf.equals("01") || timedf.equals("02") || timedf.equals("03") || timedf.equals("04") || timedf.equals("05"))
                    {
                        linearLayoutBg.setBackgroundResource(R.drawable.cloudnightbg);
                        imgIcon.setBackgroundResource(R.drawable.cloudsnighticon);
                    }
                }

                if(weatherConditionMain.equals("Thunderstorm"))
                {
                    linearLayoutBg.setBackgroundResource(R.drawable.thunderstormbg);
                    imgIcon.setBackgroundResource(R.drawable.thundericon);
                }

                if(weatherConditionMain.equals("Drizzle"))
                {
                    linearLayoutBg.setBackgroundResource(R.drawable.drizzlebg);
                    imgIcon.setBackgroundResource(R.drawable.rainicon);
                }

                if(weatherConditionMain.equals("Rain"))
                {
                    linearLayoutBg.setBackgroundResource(R.drawable.rainbg);
                    imgIcon.setBackgroundResource(R.drawable.rainicon);
                }

                if(weatherConditionMain.equals("Snow"))
                {
                    linearLayoutBg.setBackgroundResource(R.drawable.snowbg);
                    imgIcon.setBackgroundResource(R.drawable.snowicon);
                }

                if(weatherConditionMain.equals("Clear"))
                {
                    if(timedf.equals("06") || timedf.equals("07") || timedf.equals("08") || timedf.equals("09") || timedf.equals("10") || timedf.equals("11") || timedf.equals("12") || timedf.equals("13") || timedf.equals("14") || timedf.equals("15") || timedf.equals("16") || timedf.equals("17"))
                    {
                        linearLayoutBg.setBackgroundResource(R.drawable.clearbg);
                        imgIcon.setBackgroundResource(R.drawable.clearicon);
                    }

                    else if(timedf.equals("18") || timedf.equals("19") || timedf.equals("20") || timedf.equals("21") || timedf.equals("22") || timedf.equals("23") || timedf.equals("00") || timedf.equals("01") || timedf.equals("02") || timedf.equals("03") || timedf.equals("04") || timedf.equals("05"))
                    {
                        linearLayoutBg.setBackgroundResource(R.drawable.clearnightbg);
                        imgIcon.setBackgroundResource(R.drawable.clearnighticon);
                    }
                }

                JSONObject objectWind = response.getJSONObject("wind");
                String windSpeed = objectWind.getString("speed");
                String windDirection = objectWind.getString("deg");
                Double speedValue = Double.valueOf(windSpeed);
                Double dirValue = Double.valueOf(windDirection);
                String dirNESW = "";
                if(dirValue == 0 || dirValue == 360)
                {
                    dirNESW = "N";
                }
                else if(dirValue > 0 && dirValue < 89)
                {
                    dirNESW = "NE";
                }
                else if(dirValue == 90)
                {
                    dirNESW = "E";
                }
                else if(dirValue > 90 && dirValue < 180)
                {
                    dirNESW = "SE";
                }
                else if(dirValue == 180)
                {
                    dirNESW = "S";
                }
                else if(dirValue > 180 && dirValue < 270)
                {
                    dirNESW = "SW";
                }
                else if(dirValue == 270)
                {
                    dirNESW = "W";
                }
                else if(dirValue > 270 && dirValue < 360)
                {
                    dirNESW = "NW";
                }
                windView.setText(speedValue + "km/h, " + dirValue + "°"+dirNESW);

                JSONObject objectClouds = response.getJSONObject("clouds");
                String cloudiness = objectClouds.getString("all");
                Double cloudinessValue = Double.parseDouble(cloudiness);
                cloudsView.setText(cloudinessValue + "%");

                inputCity.setText("");

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(WeatherActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WeatherActivity.this, "Incorrect City Name", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

    private void backEnd() {


        btnCheck.setOnClickListener(view -> {
            String apiKey = "b120d506d16d54182fb3d399226049ca";
            String inCity = inputCity.getText().toString().trim();
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + inCity + "&appid=" + apiKey;

            if (inCity.isEmpty()) {
                inputCity.setError("Please input a city name");
            } else {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                @SuppressLint("SetTextI18n") JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
                    try {
                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss aa", Locale.getDefault());
                        SimpleDateFormat dy = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                        String formattedTime = df.format(c);
                        String formattedDate = dy.format(c);
                        cityTitle.setText(inCity.substring(0, 1).toUpperCase() + inCity.substring(1).toLowerCase() + " City Weather Updates as of \n" + formattedTime + "(GMT+8) " + formattedDate);

                        JSONObject objectTemp = response.getJSONObject("main");
                        String temperature = objectTemp.getString("temp");
                        Double tempValue = Double.parseDouble(temperature) - 273.15;
                        tempView.setText(tempValue.toString().substring(0, 4) + "°C");

                        JSONObject objectFeels = response.getJSONObject("main");
                        String feelsLike = objectFeels.getString("feels_like");
                        Double feelsValue = Double.parseDouble(feelsLike) - 273.15;
                        feelsView.setText("Feels like: " + feelsValue.toString().substring(0, 4) + "°C");

                        JSONObject objectHumid = response.getJSONObject("main");
                        String humidity = objectHumid.getString("humidity");
                        humidityView.setText(humidity.trim() + "%");

                        JSONObject objectSeaLvl = response.getJSONObject("main");
                        String seaLvl = objectSeaLvl.getString("sea_level");
                        Double seaLvlValue = Double.parseDouble(seaLvl) / 33.9f;
                        seaLvlView.setText(seaLvlValue.toString().substring(0, 4));

                        JSONObject objectLatLon = response.getJSONObject("coord");
                        String latString = objectLatLon.getString("lat");
                        String lonString = objectLatLon.getString("lon");
                        latLonView.setText("Latitude: " + latString + ", Longitude: " + lonString);

                        JSONArray arrayWeather = response.getJSONArray("weather");
                        JSONObject weatherDescription = arrayWeather.getJSONObject(0);
                        String weatherConditionMain = weatherDescription.getString("main");
                        weatherView.setText(weatherConditionMain.substring(0, 1).toUpperCase() + weatherConditionMain.substring(1).toLowerCase());

                        SimpleDateFormat timex = new SimpleDateFormat("HH", Locale.getDefault());
                        String timedf = timex.format(c);

                        if(weatherConditionMain.equals("Clouds"))
                        {
                            if(timedf.equals("06") || timedf.equals("07") || timedf.equals("08") || timedf.equals("09") || timedf.equals("10") || timedf.equals("11") || timedf.equals("12") || timedf.equals("13") || timedf.equals("14") || timedf.equals("15") || timedf.equals("16") || timedf.equals("17"))
                            {
                                linearLayoutBg.setBackgroundResource(R.drawable.cloudbg);
                                imgIcon.setBackgroundResource(R.drawable.cloudsicon);
                            }

                            else if(timedf.equals("18") || timedf.equals("19") || timedf.equals("20") || timedf.equals("21") || timedf.equals("22") || timedf.equals("23") || timedf.equals("00") || timedf.equals("01") || timedf.equals("02") || timedf.equals("03") || timedf.equals("04") || timedf.equals("05"))
                            {
                                linearLayoutBg.setBackgroundResource(R.drawable.cloudnightbg);
                                imgIcon.setBackgroundResource(R.drawable.cloudsnighticon);
                            }
                        }

                        if(weatherConditionMain.equals("Thunderstorm"))
                        {
                            linearLayoutBg.setBackgroundResource(R.drawable.thunderstormbg);
                            imgIcon.setBackgroundResource(R.drawable.thundericon);
                        }

                        if(weatherConditionMain.equals("Drizzle"))
                        {
                            linearLayoutBg.setBackgroundResource(R.drawable.drizzlebg);
                            imgIcon.setBackgroundResource(R.drawable.rainicon);
                        }

                        if(weatherConditionMain.equals("Rain"))
                        {
                            linearLayoutBg.setBackgroundResource(R.drawable.rainbg);
                            imgIcon.setBackgroundResource(R.drawable.rainicon);
                        }

                        if(weatherConditionMain.equals("Snow"))
                        {
                            linearLayoutBg.setBackgroundResource(R.drawable.snowbg);
                            imgIcon.setBackgroundResource(R.drawable.snowicon);
                        }

                        if(weatherConditionMain.equals("Clear"))
                        {
                            if(timedf.equals("06") || timedf.equals("07") || timedf.equals("08") || timedf.equals("09") || timedf.equals("10") || timedf.equals("11") || timedf.equals("12") || timedf.equals("13") || timedf.equals("14") || timedf.equals("15") || timedf.equals("16") || timedf.equals("17"))
                            {
                                linearLayoutBg.setBackgroundResource(R.drawable.clearbg);
                                imgIcon.setBackgroundResource(R.drawable.clearicon);
                            }

                            else if(timedf.equals("18") || timedf.equals("19") || timedf.equals("20") || timedf.equals("21") || timedf.equals("22") || timedf.equals("23") || timedf.equals("00") || timedf.equals("01") || timedf.equals("02") || timedf.equals("03") || timedf.equals("04") || timedf.equals("05"))
                            {
                                linearLayoutBg.setBackgroundResource(R.drawable.clearnightbg);
                                imgIcon.setBackgroundResource(R.drawable.clearnighticon);
                            }
                        }


                        JSONObject objectWind = response.getJSONObject("wind");
                        String windSpeed = objectWind.getString("speed");
                        String windDirection = objectWind.getString("deg");
                        Double speedValue = Double.valueOf(windSpeed);
                        Double dirValue = Double.valueOf(windDirection);
                        String dirNESW = "";
                        if(dirValue == 0 || dirValue == 360)
                        {
                            dirNESW = "N";
                        }
                        else if(dirValue > 0 && dirValue < 89)
                        {
                            dirNESW = "NE";
                        }
                        else if(dirValue == 90)
                        {
                            dirNESW = "E";
                        }
                        else if(dirValue > 90 && dirValue < 180)
                        {
                            dirNESW = "SE";
                        }
                        else if(dirValue == 180)
                        {
                            dirNESW = "S";
                        }
                        else if(dirValue > 180 && dirValue < 270)
                        {
                            dirNESW = "SW";
                        }
                        else if(dirValue == 270)
                        {
                            dirNESW = "W";
                        }
                        else if(dirValue > 270 && dirValue < 360)
                        {
                            dirNESW = "NW";
                        }
                        windView.setText(speedValue + "km/h, " + dirValue + "°"+dirNESW);

                        JSONObject objectClouds = response.getJSONObject("clouds");
                        String cloudiness = objectClouds.getString("all");
                        Double cloudinessValue = Double.parseDouble(cloudiness);
                        cloudsView.setText(cloudinessValue + "%");

                        inputCity.setText("");

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(WeatherActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(WeatherActivity.this, "Incorrect City Name", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(request);
            }
        });
    }
}