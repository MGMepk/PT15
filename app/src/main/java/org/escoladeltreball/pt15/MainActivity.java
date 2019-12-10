package org.escoladeltreball.pt15;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button heavyTask = findViewById(R.id.heavyTask);
        Button threadsJava = findViewById(R.id.threadsJava);
        Button asyncTask = findViewById(R.id.asyncTask);
        progressBar = findViewById(R.id.progressBar);

        heavyTask.setOnClickListener(this);
        threadsJava.setOnClickListener(this);
        asyncTask.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.heavyTask:
                for (int i = 0; i <= 10; i++) {
                    unSegundo();
                    Toast.makeText(this, "Han passat " + i + " segons", Toast.LENGTH_SHORT).show();
                    Log.d("heavy", "Han passat " + i + " segons");
                }
                break;
            case R.id.threadsJava:
                Hilos();
                break;
            case R.id.asyncTask:
                ManuelAsyncTask task = new ManuelAsyncTask();
                task.execute();
                break;
        }
    }

    private void unSegundo() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void Hilos() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++) unSegundo();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Tasca Thread finalitzada", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
    }

    private class ManuelAsyncTask extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setMax(100);
            progressBar.setProgress(0);
        }

        @Override
        protected Boolean doInBackground(String... args) {
            for (int i = 1; i <= 10; i++) {
                unSegundo();
                publishProgress(i * 10);
                if (isCancelled()) {
                    break;
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean res) {
            super.onPostExecute(res);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);

        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
        }


    }

}
