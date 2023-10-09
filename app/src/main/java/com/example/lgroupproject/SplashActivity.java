package com.example.lgroupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    ImageView logo;
    TextView logonem;
    Animation top,bottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo=findViewById(R.id.splashImage);
        logonem=findViewById(R.id.app_name);

        top= AnimationUtils.loadAnimation(this,R.anim.movedown);
        bottom= AnimationUtils.loadAnimation(this,R.anim.moveup);
        logo.setAnimation(top);
        logonem.setAnimation(bottom);

        //for remove statusbar in your splash Screen.
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);

    }
}