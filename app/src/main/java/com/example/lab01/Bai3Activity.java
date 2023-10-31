package com.example.lab01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

public class Bai3Activity extends AppCompatActivity implements View.OnClickListener, Listener{
    @Override
    public void onImageLoader(Bitmap bitmap) {
        imgImage.setImageBitmap(bitmap);
        tvMesssage.setText("Image Downloaded");
    }

    @Override
    public void onError() {
        tvMesssage.setText("Error download image");
    }

    public class LoadImageTask extends AsyncTask<String, Void, Bitmap>{

        private Listener mListener;
        private ProgressDialog progressDialog;
        public LoadImageTask(Listener listener, Context context){
            mListener = listener;
            progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Downloading image...");
            progressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                return BitmapFactory.decodeStream((InputStream) new URL(strings[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            if(bitmap != null){
                mListener.onImageLoader(bitmap);
            }else {
                mListener.onError();
            }
        }
    }

    private TextView tvMesssage;
    private Button btnLoad;
    private Button btnNext;

    private ImageView imgImage;
    public static final String IMAGE_URL = "https://api.yatta.top/hsr/assets/UI/avatar/large/1107.sm.png?vh=2023100901";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai3);
        tvMesssage = findViewById(R.id.tvMessage);
        btnLoad = findViewById(R.id.btnLoad);
        btnNext = findViewById(R.id.btnNext);
        imgImage = findViewById(R.id.imgImage);
        btnLoad.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.btnLoad){
            new LoadImageTask(this, this).execute(IMAGE_URL);
        } else if (viewId == R.id.btnNext) {
            Intent intent = new Intent(Bai3Activity.this, Bai4Activity.class);
            startActivity(intent);
        }
    }

}