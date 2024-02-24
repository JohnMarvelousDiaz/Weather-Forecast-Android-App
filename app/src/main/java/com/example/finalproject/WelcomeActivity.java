package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class WelcomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    LinearLayout btnNASA, btnPokedex, btnJokes, btnMessenger, btnDictionary;
    Button btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mAuth = FirebaseAuth.getInstance();

        btnDictionary = findViewById(R.id.btnDictionary);
        btnNASA = findViewById(R.id.btnNASA);
        btnPokedex = findViewById(R.id.btnPokedex);
        btnJokes = findViewById(R.id.btnJokes);
        btnMessenger = findViewById(R.id.btnMessenger);
        btnLogOut = findViewById(R.id.btnLogOut);

        btnDictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, DictionaryActivity.class));
            }
        });

        btnNASA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClassName("com.example.covid_19tracker", "com.example.covid_19tracker.MainActivity");
                startActivity(intent);
            }
        });

        btnPokedex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, Pokedex.class));
            }
        });

        btnJokes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, Jokes.class));
            }
        });

        btnMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, WeatherActivity.class));
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null)
        {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        }
    }
}