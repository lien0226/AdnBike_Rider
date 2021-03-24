package com.arife.adnbikerider.mvp.v;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.arife.adnbikerider.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView splashImage, Logo;
    private LottieAnimationView lottieAnimationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.Logo = findViewById(R.id.logo);
        this.splashImage = findViewById(R.id.slash_image);
        this.lottieAnimationView = findViewById(R.id.lottie);

        splashImage.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        Logo.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);

    }
}