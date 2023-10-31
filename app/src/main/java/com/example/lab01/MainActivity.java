package com.example.lab01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLoad;
    private Button btnNext;
    private TextView tvMessage;
    private ImageView imgAndroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLoad = findViewById(R.id.btnLoad);
        btnNext = findViewById(R.id.btnNext);
        tvMessage = findViewById(R.id.tvMessage);
        imgAndroid = findViewById(R.id.imgAndroid);
        btnLoad.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.btnLoad){
            final Thread myThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    final Bitmap bitmap = loadImageFromNetwork("https://api.yatta.top/hsr/assets/UI/avatar/large/1107.sm.png?vh=2023100901");
                    imgAndroid.post(new Runnable() {
                        @Override
                        public void run() {
                            tvMessage.setText("Image Downloaded");
                            imgAndroid.setImageBitmap(bitmap);
                        }
                    });
                }
            });
            myThread.start();
        } else if (viewId == R.id.btnNext) {
            Intent intent = new Intent(MainActivity.this, Bai3Activity.class);
            startActivity(intent);
        }
    }

    private Bitmap loadImageFromNetwork(String link){
        URL url;
        Bitmap bmp = null;
        try {
            url = new URL(link);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }
}