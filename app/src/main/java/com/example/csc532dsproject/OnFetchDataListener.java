package com.example.csc532dsproject;

import com.example.csc532dsproject.Models.NewsHeadlines;
import java.util.List;

public interface OnFetchDataListener <NewsApiResponse>{

    void onFechData(List<NewsHeadlines> list, String message);
    void onError(String message);
}
