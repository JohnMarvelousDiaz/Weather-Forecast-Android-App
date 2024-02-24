package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Pokedex extends AppCompatActivity {

    LinearLayout background;
    ImageView pokeImage;
    EditText pokeSearch;
    Button searchBtn,clearBtn;
    TextView pokeName,pokeType,pokeIndex,pokeHealth,pokeAttack,pokeDefense,pokeSA,pokeSD,pokeSpeed, btnExit, btnChange, pokeTypeTitle, pokeIndexTitle, pokeBaseTitle, pokeHealthTitle,pokeAttackTitle,pokeDefenseTitle,pokeSATitle,pokeSDTitle,pokeSpeedTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);
        initialize();

    }

    public void initialize()
    {
        pokeImage = findViewById(R.id.pokeImage);
        pokeSearch = findViewById(R.id.pokeSearch);
        searchBtn = findViewById(R.id.searchBtn);
        clearBtn = findViewById(R.id.clearBtn);
        btnExit = findViewById(R.id.btnExit);
        btnChange = findViewById(R.id.btnChange);
        pokeName = findViewById(R.id.pokeName);
        pokeType = findViewById(R.id.pokeType);
        pokeIndex = findViewById(R.id.pokeIndex);
        pokeHealth = findViewById(R.id.pokeHealth);
        pokeAttack = findViewById(R.id.pokeAttack);
        pokeDefense = findViewById(R.id.pokeDefense);
        pokeSA = findViewById(R.id.pokeSA);
        pokeSD = findViewById(R.id.pokeSD);
        pokeSpeed = findViewById(R.id.pokeSpeed);
        pokeTypeTitle = findViewById(R.id.pokeTypeTitle);
        pokeIndexTitle = findViewById(R.id.pokeIndexTitle);
        pokeBaseTitle = findViewById(R.id.pokeBaseTitle);
        pokeHealthTitle = findViewById(R.id.pokeHealthTitle);
        pokeAttackTitle = findViewById(R.id.pokeAttackTitle);
        pokeDefenseTitle = findViewById(R.id.pokeDefenseTitle);
        pokeSATitle = findViewById(R.id.pokeSATitle);
        pokeSDTitle = findViewById(R.id.pokeSDTitle);
        pokeSpeedTitle = findViewById(R.id.pokeSpeedTitle);
        background = findViewById(R.id.background);
        String pokemon = pokeSearch.getText().toString().toLowerCase();
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = pokeSearch.getText().toString().toLowerCase();
                if(search != null){
                    apiCall(getApplicationContext(),search);

                }else{
                    Toast.makeText(Pokedex.this, "Search field is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearField();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitApp();
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor();
            }
        });
    }

    public void clearField()
    {
        Picasso.get()
                .load(R.drawable.pokeball)
                .into(pokeImage);
        pokeName.setText("NAME");
        pokeIndex.setText("");
        pokeType.setText("");

        pokeSearch.setText("");
        pokeHealth.setText("");
        pokeAttack.setText("");
        pokeDefense.setText("");
        pokeSA.setText("");
        pokeSD.setText("");
        pokeSpeed.setText("");
        Toast.makeText(this, "Data Cleared!", Toast.LENGTH_SHORT).show();
    }

    public void exitApp()
    {
        startActivity(new Intent(Pokedex.this, WelcomeActivity.class));
    }
    boolean color = true;
    public void changeColor()
    {
        if(color)
        {
            background.setBackground(ContextCompat.getDrawable(Pokedex.this, R.drawable.pokedex));
            searchBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
            clearBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
            searchBtn.setTextColor(Color.parseColor("#FFFFFF"));
            clearBtn.setTextColor(Color.parseColor("#FFFFFF"));
            pokeSearch.setTextColor(Color.parseColor("#000000"));
            pokeName.setTextColor(Color.parseColor("#000000"));
            pokeType.setTextColor(Color.parseColor("#000000"));
            pokeIndex.setTextColor(Color.parseColor("#000000"));
            pokeHealth.setTextColor(Color.parseColor("#000000"));
            pokeAttack.setTextColor(Color.parseColor("#000000"));
            pokeDefense.setTextColor(Color.parseColor("#000000"));
            pokeSA.setTextColor(Color.parseColor("#000000"));
            pokeSD.setTextColor(Color.parseColor("#000000"));
            pokeSpeed.setTextColor(Color.parseColor("#000000"));
            pokeTypeTitle.setTextColor(Color.parseColor("#000000"));
            pokeIndexTitle.setTextColor(Color.parseColor("#000000"));
            pokeBaseTitle.setTextColor(Color.parseColor("#000000"));
            pokeHealthTitle.setTextColor(Color.parseColor("#000000"));
            pokeAttackTitle.setTextColor(Color.parseColor("#000000"));
            pokeDefenseTitle.setTextColor(Color.parseColor("#000000"));
            pokeSATitle.setTextColor(Color.parseColor("#000000"));
            pokeSDTitle.setTextColor(Color.parseColor("#000000"));
            pokeSpeedTitle.setTextColor(Color.parseColor("#000000"));
            color = false;
        }

        else if(!color)
        {
            background.setBackground(ContextCompat.getDrawable(Pokedex.this, R.drawable.pokedex1));
            searchBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            clearBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            searchBtn.setTextColor(Color.parseColor("#000000"));
            clearBtn.setTextColor(Color.parseColor("#000000"));
            pokeSearch.setTextColor(Color.parseColor("#FFFFFF"));
            pokeName.setTextColor(Color.parseColor("#FFFFFF"));
            pokeType.setTextColor(Color.parseColor("#FFFFFF"));
            pokeIndex.setTextColor(Color.parseColor("#FFFFFF"));
            pokeHealth.setTextColor(Color.parseColor("#FFFFFF"));
            pokeAttack.setTextColor(Color.parseColor("#FFFFFF"));
            pokeDefense.setTextColor(Color.parseColor("#FFFFFF"));
            pokeSA.setTextColor(Color.parseColor("#FFFFFF"));
            pokeSD.setTextColor(Color.parseColor("#FFFFFF"));
            pokeSpeed.setTextColor(Color.parseColor("#FFFFFF"));
            pokeTypeTitle.setTextColor(Color.parseColor("#FFFFFF"));
            pokeIndexTitle.setTextColor(Color.parseColor("#FFFFFF"));
            pokeBaseTitle.setTextColor(Color.parseColor("#FFFFFF"));
            pokeHealthTitle.setTextColor(Color.parseColor("#FFFFFF"));
            pokeAttackTitle.setTextColor(Color.parseColor("#FFFFFF"));
            pokeDefenseTitle.setTextColor(Color.parseColor("#FFFFFF"));
            pokeSATitle.setTextColor(Color.parseColor("#FFFFFF"));
            pokeSDTitle.setTextColor(Color.parseColor("#FFFFFF"));
            pokeSpeedTitle.setTextColor(Color.parseColor("#FFFFFF"));
            color = true;
        }
    }

    public void apiCall(Context c, String name)
    {
        pokeSearch.setText("");
        RequestQueue request = Volley.newRequestQueue(c);
        request.start();
        String url = "https://pokeapi.co/api/v2/pokemon/"+name;
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET,url,null,
                response -> {
                    try {
                        //name
                        String pokemoname = response.getString("name");
                        //index
                        JSONArray gameI = response.getJSONArray("game_indices");
                        JSONObject zero = gameI.getJSONObject(0);
                        int index = zero.getInt("game_index");
                        //pokemon type
                        JSONArray types = response.getJSONArray("types");
                        JSONObject i = types.getJSONObject(0);
                        JSONObject type = i.getJSONObject("type");
                        String pokesType = type.getString("name");
                        //image
                        JSONObject sprites = response.getJSONObject("sprites");
                        JSONObject other = sprites.getJSONObject("other");
                        JSONObject off = other.getJSONObject("official-artwork");
                        String imageUrl = off.getString("front_default");
                        //getting object and array indexes
                        JSONArray stats = response.getJSONArray("stats");
                        JSONObject zerostat = stats.getJSONObject(0);
                        JSONObject onestat = stats.getJSONObject(1);
                        JSONObject twostat = stats.getJSONObject(2);
                        JSONObject threestat = stats.getJSONObject(3);
                        JSONObject fourstat = stats.getJSONObject(4);
                        JSONObject fivestat = stats.getJSONObject(5);

                        //stat value
                        String health = zerostat.getString("base_stat");
                        String attack = onestat.getString("base_stat");
                        String defense = twostat.getString("base_stat");
                        String Sattack = threestat.getString("base_stat");
                        String Sdefense = fourstat.getString("base_stat");
                        String speed = fivestat.getString("base_stat");



                        pokeName.setText(pokemoname.toUpperCase());
                        pokeIndex.setText("#"+index);
                        pokeType.setText(pokesType.toUpperCase());
                        Picasso.get()
                                .load(imageUrl)
                                .into(pokeImage);
                        pokeHealth.setText(health);
                        pokeAttack.setText(attack);
                        pokeDefense.setText(defense);
                        pokeSA.setText(Sattack);
                        pokeSD.setText(Sdefense);
                        pokeSpeed.setText(speed);


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },error -> {
            pokeName.setText("NAME");
            pokeSearch.setError("Please check the Pokemon name!");
            pokeIndex.setText("");
            pokeType.setText("");
            Picasso.get()
                    .load(R.drawable.pokeball)
                    .into(pokeImage);
            pokeHealth.setText("");
            pokeAttack.setText("");
            pokeDefense.setText("");
            pokeSA.setText("");
            pokeSD.setText("");
            pokeSpeed.setText("");
            Toast.makeText(c, "Sorry, the pokemon you are looking does not exist", Toast.LENGTH_SHORT).show();
        });
        request.add(json);
    }

}