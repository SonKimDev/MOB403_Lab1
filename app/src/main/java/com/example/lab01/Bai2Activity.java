package com.example.lab01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Bai2Activity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLoad;
    private Button btnNext;

    private TextView tvMessage;
    private ImageView imgAndroid;
    private Bitmap bitmap = null;
    String url = "https://api.yatta.top/hsr/assets/UI/avatar/large/1107.sm.png?vh=2023100901";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai2);
        btnLoad = findViewById(R.id.btnLoad);
        btnNext = findViewById(R.id.btnNext);
        tvMessage = findViewById(R.id.tvMessage);
        imgAndroid = findViewById(R.id.imgAndroid);
        btnLoad.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                // Xử lý thông điệp khi tải hình ảnh xong
                tvMessage.setText("Image Downloaded");
                imgAndroid.setImageBitmap(bitmap);
            }
            return false;
        }
    });
    private Bitmap downloadBitmap(String link){
        try {
            URL url1 = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            Bitmap bitmap1 = BitmapFactory.decodeStream(inputStream);
            return bitmap1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    };

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.btnLoad){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    bitmap = downloadBitmap(url);
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        } else if (viewId == R.id.btnNext) {
            Intent intent = new Intent(Bai2Activity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}