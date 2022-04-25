package com.example.newreaderapp;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsNews extends AppCompatActivity {
    WebView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_news);
        view = (WebView) findViewById(R.id.webDetailNews);

        Intent intent = getIntent();
        String link;
        if(intent != null){
            link = intent.getStringExtra("link");

            view.loadUrl(link);
            view.setWebViewClient(new WebViewClient());
        }
    }
}