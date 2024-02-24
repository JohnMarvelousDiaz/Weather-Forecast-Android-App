package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText etRegEmail, etRegPassword, etRegFName, etRegLName;
    Button btnRegister;
    TextView tvLogIn, link;
    VideoView videoView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPassword);
        etRegFName = findViewById(R.id.etRegFName);
        etRegLName = findViewById(R.id.etRegLName);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogIn = findViewById(R.id.tvLogIn);
        link = findViewById(R.id.link);
        videoView = findViewById(R.id.videoView);



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        tvLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bg);
        videoView.setVideoURI(uri);
        videoView.setClickable(false);
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.start();
            }
        });

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://www.facebook.com/jm2d8";

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));

                startActivity(intent);
            }
        });
    }

    private void addName()
    {
        FullName name = new FullName(etRegFName.getText().toString().trim(), etRegLName.getText().toString().trim());
        DAOName dao = new DAOName();
        dao.add(name).addOnSuccessListener(suc->{

        }).addOnFailureListener(er->{

        });
    }

    private void register() {
        String username = etRegEmail.getText().toString().trim();
        String password = etRegPassword.getText().toString().trim();

        if(username.isEmpty())
        {
            etRegEmail.setError("");
        }
        if(password.isEmpty())
        {
            etRegPassword.setError("");
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        addName();
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, WelcomeActivity.class));
                        etRegEmail.setText("");
                        etRegPassword.setText("");
                        etRegFName.setText("");
                        etRegLName.setText("");
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Registration Failed " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}