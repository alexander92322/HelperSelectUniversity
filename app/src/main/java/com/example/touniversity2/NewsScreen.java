package com.example.touniversity2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class NewsScreen extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    ProgressBar progressBar;
    private List<UniversityNews> universityNewsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_screen);
        recyclerView = findViewById(R.id.recyclerView);
        Retrofit retrofit = ApiClient.getClient();
        NewsApiService apiService = retrofit.create(NewsApiService.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter(this, universityNewsList);
        recyclerView.setAdapter(newsAdapter);
        progressBar = findViewById(R.id.progressBar);

        apiService.getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError);

    }
    private void handleResponse(NewsResponse response) {
        for (Article article : response.getArticles()) {
            Log.d("News", "Title: " + article.getTitle());
            Log.d("News", "Description: " + article.getDescription());
            Log.d("News", "URL: " + article.getUrl());
            Log.d("News", "Image URL: " + article.getUrlToImage());
            Log.d("News", "---------------------");
            UniversityNews universityNews = new UniversityNews(article.getTitle(), article.getDescription(), article.getUrl(), article.getUrlToImage());
            universityNewsList.add(universityNews);
        }
        newsAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void handleError(Throwable error) {
        Log.e("News", "Error: " + error.getLocalizedMessage());
    }
    public void ClickonBack(View view){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}