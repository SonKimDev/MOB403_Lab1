package com.example.lab01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Bai4Activity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextSeconds;
    private Button startCountdownButton;
    private ProgressDialog progressDialog;

    public class AsyncTaskRunner extends AsyncTask<String, String, String> {
        private String resp;
        ProgressDialog progressDialog;
        TextView tvResult;
        EditText time;
        Context context;

        public AsyncTaskRunner(Context context, TextView tvResult, EditText time){
            this.tvResult = tvResult;
            this.context = context;
            this.time = time;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(context, "ProgressDialog", "Wait for " + time.getText().toString()+ " seconds");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            tvResult.setText(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            publishProgress("Sleeping...");
            try {
                int time = Integer.parseInt(strings[0]) * 1000;
                Thread.sleep(time);
                resp = "Slept for " + strings[0] + " seconds";
            }catch (Exception e){
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai4);
        editTextSeconds = findViewById(R.id.editTextSeconds);
        startCountdownButton = findViewById(R.id.startCountdownButton);
        startCountdownButton.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        startCountdownButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int seconds = Integer.parseInt(editTextSeconds.getText().toString());
        CountdownTask countdownTask = new CountdownTask();
        countdownTask.execute(seconds);
    }

    private class CountdownTask extends AsyncTask<Integer, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Counting down...");
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Integer... params) {
            int seconds = params[0];
            for (int i = seconds; i >= 0; i--) {
                publishProgress(i);
                try {
                    Thread.sleep(1000); // Sleep for 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0] * 100 / progressDialog.getMax());
            editTextSeconds.setText(String.valueOf(values[0]));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }
    }
}