package com.example.touniversity2;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface NewsApiService {
    @GET("everything?sortBy=publishedAt&q=ВУЗы&q=Россия&q=ВШЭ")
    Observable<NewsResponse> getNews();
}
