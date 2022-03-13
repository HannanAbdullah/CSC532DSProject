package com.example.csc532dsproject;

import static java.sql.DriverManager.println;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.csc532dsproject.Models.NewsApiResponse;
import com.example.csc532dsproject.Models.NewsHeadlines;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListner{

    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching last news articles in KSA");
        dialog.show();

        RequestManager manager = new RequestManager((this));
        manager.getNewsHeadlines(listener,"general", null);

    }

    private  final OnFetchDataListener <NewsApiResponse> listener = new OnFetchDataListener <NewsApiResponse>()
    {

        @Override
        public void onFechData(List<NewsHeadlines> list, String message) {
           showNews(list);
           dialog.dismiss();
        }

        @Override
        public void onError(String message) {

        }
    };

    private void showNews(List <NewsHeadlines> list)
    {
        recyclerView= findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new CustomAdapter(this,list, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnNewsClicked(NewsHeadlines headlines) {
        startActivity(new Intent(MainActivity.this, DetailsActivity.class)
        .putExtra("data",headlines));

    }
}