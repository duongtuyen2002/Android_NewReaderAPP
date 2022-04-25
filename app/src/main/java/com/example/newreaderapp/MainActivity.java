package com.example.newreaderapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    ListView lstView;
    NewsAdapter newsAdapter;
    ArrayList<News> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstView = findViewById(R.id.lvNews);
        arrayList = new ArrayList<News>();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadData().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
            }
        });

        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,DetailsNews.class);
                intent.putExtra("link",arrayList.get(i).getLink());
                startActivity(intent);

            }
        });
    }

    private class ReadData extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            return docNoidung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);

            NodeList list = document.getElementsByTagName("item");
            NodeList Decription = document.getElementsByTagName("description");
            String day = "";
            String title = "";
            String link ="";
            String anh = "";
            for(int i = 0; i<list.getLength();i++){
                String data = Decription.item(i+1).getTextContent();

                Pattern pattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher = pattern.matcher(data);

                if(matcher.find()){
                    anh = matcher.group(1);
                }
                Element element = (Element) list.item(i);
                title = parser.getValue(element,"title");
                link = parser.getValue(element,"link");
                arrayList.add(new News(title,link,anh));

            }
            newsAdapter = new NewsAdapter(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
            lstView.setAdapter(newsAdapter);
            super.onPostExecute(s);
        }

    }

    private String docNoidung_Tu_URL(String theURL) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(theURL);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            while ((line =bufferedReader.readLine()) != null){
                content.append(line +"\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}