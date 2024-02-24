package com.example.finalproject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Random;

public class Jokes extends AppCompatActivity {

    RequestQueue requestQueue;
    TextView joke_text_view, jokeNum;
    Button get_joke_button, btnBack;
    TextToSpeech textToSpeechGirl, textToSpeechBoy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes);

        requestQueue = Volley.newRequestQueue(this);
        joke_text_view = findViewById(R.id.joke_text_view);
        get_joke_button = findViewById(R.id.get_joke_button);
        btnBack = findViewById(R.id.btnBack);
        jokeNum = findViewById(R.id.jokeNum);

        textToSpeechGirl = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR){
                    textToSpeechGirl.setLanguage(Locale.ENGLISH);
                }
            }
        });

        textToSpeechBoy = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR){
                    textToSpeechBoy.setLanguage(Locale.ENGLISH);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeechGirl.speak("   ", TextToSpeech.QUEUE_FLUSH, null);
                textToSpeechGirl.speak("    ", TextToSpeech.QUEUE_ADD, null);
                textToSpeechBoy.speak("    ", TextToSpeech.QUEUE_FLUSH, null);
                finish();
                startActivity(new Intent(Jokes.this, WelcomeActivity.class));
            }
        });


        get_joke_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://official-joke-api.appspot.com/random_joke";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    String id = String.valueOf(response.getInt("id"));
                                    String setup = response.getString("setup");
                                    String punchline = response.getString("punchline");

                                    jokeNum.setText("Joke Number: " + id);
                                    joke_text_view.setText(setup + "\n\n" + punchline);
                                    textToSpeechGirl.setPitch(1);
                                    textToSpeechGirl.setSpeechRate(0.7f);
                                    textToSpeechGirl.speak(setup, TextToSpeech.QUEUE_FLUSH, null);
                                    textToSpeechGirl.speak("   ", TextToSpeech.QUEUE_ADD, null);
                                    textToSpeechBoy.setPitch(1);
                                    textToSpeechBoy.setSpeechRate(0.7f);
                                    textToSpeechBoy.speak(punchline, TextToSpeech.QUEUE_FLUSH, null);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    joke_text_view.setText("Error: " + e.getMessage());
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                joke_text_view.setText("Error: " + error.getMessage());
                            }
                        });

                requestQueue.add(jsonObjectRequest);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        requestQueue.cancelAll(this);
    }
}
