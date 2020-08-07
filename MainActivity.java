package com.example.asnyctasktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {


    private Button mbutton;
    private ProgressBar mprograssBar;
    private TextView mtextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
        setData();
    }

    private void setData() {
        mprograssBar.setProgress(0);
    }

    private void setListener() {
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Download download = new Download();
                download.execute("http://download.sj.qq.com/upload/connAssitantDownload/upload/MobileAssistant_1.apk");

            }
        });
    }

    private void initView() {
        mbutton = findViewById(R.id.button);
        mprograssBar = findViewById(R.id.progressBar);
        mtextView = findViewById(R.id.textView);
    }
    public  class Download extends AsyncTask<String,Integer, Boolean> {
        // string:入参          integer:进度        boolean:结果，返回值
        String mFilePath;
        @Override
        protected Boolean doInBackground(String... strings) {
            if (strings != null&& strings.length > 0){
                String url = strings[0];
                try {
                    URL url1 = new URL(url);
                    URLConnection urlConnection = url1.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    int contentLength = urlConnection.getContentLength();
                    mFilePath = Environment.getExternalStorageDirectory()
                            + File.separator ;
                    int downLoadSize = 0;
                    byte[] bytes = new byte[1024];
                    int length;
                    FileOutputStream fileOutputStream = new FileOutputStream(mFilePath);

                    while ((length = inputStream.read(bytes))!=-1){
                        fileOutputStream.write(bytes,0,length);
                        downLoadSize +=length;
                        publishProgress(downLoadSize*100/contentLength);
                    }
                    inputStream.close();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mbutton.setText("下载中");
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mbutton.setText("结束");
            mtextView.setText("结束");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mprograssBar.setProgress(values[0]);
        }


    }
}
