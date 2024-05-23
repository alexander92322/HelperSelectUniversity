package com.example.touniversity2;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface NewsApiService {
    @GET("everything?sortBy=publishedAt&apiKey=9f6e410679994ec1a9f0a8135d5475f5&q=ВУЗы&q=Россия&q=ВШЭ")
    Observable<NewsResponse> getNews();
}