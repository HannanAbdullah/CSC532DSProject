package com.example.csc532dsproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csc532dsproject.Models.NewsApiResponse;
import com.example.csc532dsproject.Models.NewsHeadlines;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListner, View.OnClickListener{

    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    Button b1,b2,b3,b4,b5,b6,b7;
    SearchView searchView;
    public static Spinner CountrySpinner;
    public static int CountryPosition=-1;
    public int CategoryCount;
    public String Query;
    public String Country;
    public String Category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("");
        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching last news articles of SA");
        dialog.show();

        if (CountryPosition==-1)
        {
            CountryPosition = 0;
            Country="sa";
        }


        CountrySpinner= findViewById(R.id.countrySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.country_array));
        CountrySpinner.setAdapter(adapter);
        CountrySpinner.setSelection(CountryPosition);

        CountrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dialog.setTitle("Fetching last news articles in "+ parent.getItemAtPosition(position));
                dialog.show();
                CountryPosition=position;
                switch (position)
                {
                    case 0:
                        Country="sa";
                        break;
                    case 1:
                        Country="ae";
                        break;
                    case 2:
                        Country="ar";
                        break;
                    case 3:
                        Country="au";
                        break;
                    case 4:
                        Country="be";
                        break;
                    case 5:
                        Country="br";
                        break;
                    case 6:
                        Country="ch";
                        break;
                    case 7:
                        Country="cn";
                        break;
                    case 8:
                        Country="cu";
                        break;
                    case 9:
                        Country="de";
                        break;
                    case 10:
                        Country="eg";
                        break;
                    case 11:
                        Country="fr";
                        break;
                    case 12:
                        Country="gb";
                        break;
                    case 13:
                        Country="hk";
                        break;
                    case 14:
                        Country="id";
                        break;
                    case 15:
                        Country="ie";
                        break;
                    case 16:
                        Country="in";
                        break;
                    case 17:
                        Country="it";
                        break;
                    case 18:
                        Country="jp";
                        break;
                    case 19:
                        Country="kr";
                        break;
                    case 20:
                        Country="ma";
                        break;
                    case 21:
                        Country="mx";
                        break;
                    case 22:
                        Country="my";
                        break;
                    case 23:
                        Country="ng";
                        break;
                    case 24:
                        Country="NZ";
                        break;
                    case 25:
                        Country="ph";
                        break;
                    case 26:
                        Country="pl";
                        break;
                    case 27:
                        Country="pt";
                        break;
                    case 28:
                        Country="ru";
                        break;
                    case 29:
                        Country="se";
                        break;
                    case 30:
                        Country="sg";
                        break;
                    case 31:
                        Country="th";
                        break;
                    case 32:
                        Country="tr";
                        break;
                    case 33:
                        Country="ua";
                        break;
                    case 34:
                        Country="us";
                        break;
                }//switch

                 if (Category==null)
                     Category="general";

                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsHeadlines(listener, Country,Category, Query);
                dialog.dismiss();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                Query= query;
                CategoryCount=0;
                closeKeyboard();
                searchView.setQuery("", false);

                dialog.setTitle("Fetching news articles of "+query);
                dialog.show();

                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsHeadlines(listener,Country,"general", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        b1=findViewById(R.id.btn_1);
        b1.setOnClickListener(this);
        b2=findViewById(R.id.btn_2);
        b2.setOnClickListener(this);
        b3=findViewById(R.id.btn_3);
        b3.setOnClickListener(this);
        b4=findViewById(R.id.btn_4);
        b4.setOnClickListener(this);
        b5=findViewById(R.id.btn_5);
        b5.setOnClickListener(this);
        b6=findViewById(R.id.btn_6);
        b6.setOnClickListener(this);
        b7=findViewById(R.id.btn_7);
        b7.setOnClickListener(this);

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener,Country,"general", null);

    }

    private void closeKeyboard()
    {
        View view=this.getCurrentFocus();
        if (view!= null)
        {
            InputMethodManager imm = (InputMethodManager) getSystemService((Context.INPUT_METHOD_SERVICE));
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    private  final OnFetchDataListener <NewsApiResponse> listener = new OnFetchDataListener <NewsApiResponse>()
    {

        @Override
        public void onFechData(List<NewsHeadlines> list, String message) {
            if (list.isEmpty())
            {
                CategoryCount=CategoryCount+1;

                if (CategoryCount==1)
                {
                    RequestManager manager = new RequestManager(MainActivity.this);
                    manager.getNewsHeadlines(listener,Country, "business", Query);
                }
                else
                if (CategoryCount==2)
                {
                    RequestManager manager = new RequestManager(MainActivity.this);
                    manager.getNewsHeadlines(listener, Country,"entertainment", Query);
                }
                else
                if (CategoryCount==3)
                {
                    RequestManager manager = new RequestManager(MainActivity.this);
                    manager.getNewsHeadlines(listener, Country,"health", Query);
                }
                else
                if (CategoryCount==4)
                {
                    RequestManager manager = new RequestManager(MainActivity.this);
                    manager.getNewsHeadlines(listener,Country, "science", Query);
                }
                else
                if (CategoryCount==5)
                {
                    RequestManager manager = new RequestManager(MainActivity.this);
                    manager.getNewsHeadlines(listener,Country, "sports", Query);
                }
                else
                if (CategoryCount==6)
                {
                    RequestManager manager = new RequestManager(MainActivity.this);
                    manager.getNewsHeadlines(listener,Country, "technology", Query);
                }
                else
                if (CategoryCount==7)
                {
                    Toast.makeText(MainActivity.this, "No data found!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
           else
           {
               showNews(list);
               dialog.dismiss();
           }

        }

        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this, "An Error Occurred!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        Category = button.getText().toString();
        dialog.setTitle("Fetching last news articles of "+Category);
        dialog.show();

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener,Country,Category, null);
    }


    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onRestart() {
        super.onRestart();
    }
}