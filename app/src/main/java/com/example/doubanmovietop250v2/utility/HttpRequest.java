package com.example.doubanmovietop250v2.utility;

import com.example.doubanmovietop250v2.data.Movie;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Guure on 2017/5/5.
 */

public class HttpRequest {

    String baseUrl = "https://api.douban.com/v2/movie/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private DoubanMovieService doubanMovieService;

    private static HttpRequest instance = new HttpRequest();

    public static HttpRequest getInstance() {
        return instance;
    }

    private HttpRequest() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        doubanMovieService = retrofit.create(DoubanMovieService.class);
    }

    public void getMovies(Observer<List<Movie>> observer, int start, int count, LifecycleTransformer<List<Movie>> listLifecycleTransformer) {
        Observable<HttpResult<List<Movie>>> observable = doubanMovieService.getMovies(start, count);
        observable.map(function)
                .compose(listLifecycleTransformer)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    Function<HttpResult<List<Movie>>, List<Movie>> function = new Function<HttpResult<List<Movie>>, List<Movie>>() {
        @Override
        public List<Movie> apply(HttpResult<List<Movie>> listHttpResult) {
            return listHttpResult.getSubjects();
        }
    };

}
