package com.example.handlerproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        final ImageView imageView = findViewById(R.id.image);
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case 1:
                        imageView.setImageBitmap((Bitmap) msg.obj);
                    break;
                    case 2:
                        Toast.makeText(getApplicationContext(),"失败",Toast.LENGTH_SHORT).show();
                        //Toast.makeText(this,"失败",Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            //URL url = new URL("https://lmg2.mukewang.com/5adfee7f0001cbb906000338-240-135.jpg");
                            URL url = new URL("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=false&word=%E6%A2%85%E8%A5%BF%E5%9B%BE%E7%89%87&step_word=&hs=2&pn=0&spn=0&di=121550&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=3579902267%2C4062416809&os=2593893976%2C3139596198&simid=3255720862%2C3618141671&adpicid=0&lpn=0&ln=2744&fr=&fmq=1596721288469_R&fm=&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=star&bdtype=0&oriquery=&objurl=http%3A%2F%2Fimg1.xiazaizhijia.com%2Fwalls%2F20150918%2Fmiddle_d97c23ee72b3a48.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bxtwzwtzit3tw_z%26e3Bv54AzdH3FktzitAzdH3Fpw2AzdH3Fbm9c9_d_z%26e3Bip4s&gsm=1&rpstart=0&rpnum=0&islist=&querylist=&force=undefined");
                            //URLConnection urlConnection = url.openConnection();
                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setRequestMethod("GET");
                            urlConnection.setReadTimeout(2000);
                            urlConnection.setConnectTimeout(2000);
                            urlConnection.connect();
                            if (urlConnection.getResponseCode() == 200){
                                InputStream inputStream = urlConnection.getInputStream();
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                Message message = new Message();
                                message.obj = bitmap;
                                message.what = 1;
                                handler.sendMessage(message);
                            }
                            else {
                                Message message = handler.obtainMessage();
                                message.what = 0;
                                handler.sendMessage(message);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        });
    }
}
