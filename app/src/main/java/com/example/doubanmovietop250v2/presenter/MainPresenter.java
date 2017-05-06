package com.example.doubanmovietop250v2.presenter;

import android.util.Log;

import com.example.doubanmovietop250v2.data.Movie;
import com.example.doubanmovietop250v2.model.IMovies;
import com.example.doubanmovietop250v2.model.Movies;
import com.example.doubanmovietop250v2.utility.HttpRequest;
import com.example.doubanmovietop250v2.view.IMainActivity;
import com.orhanobut.hawk.Hawk;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Guure on 2017/5/5.
 */

public class MainPresenter {

    private IMovies movies;
    private IMainActivity mainActivity;

    private Observer<List<Movie>> observer;

    private int page;

    public MainPresenter(IMainActivity mainActivity) {
        this.mainActivity = mainActivity;
        movies = new Movies();
    }

    public void showSpecPageMovies(int page) {
        this.page = page;
        initObserver();
        List<Movie> movieList = Hawk.get(page + "");
        if (movieList == null)
            HttpRequest.getInstance().getMovies(observer, (page - 1) * 10, 10, mainActivity.bindbindUntilEvent());
        else {
            movies.setMovies(movieList);
            mainActivity.showMovies(movies);
        }
    }

    void initObserver() {
        observer = new Observer<List<Movie>>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                mainActivity.showProgressDialog();
            }

            @Override
            public void onNext(List<Movie> movieList) {
                Hawk.put(page + "", movieList);
                movies.setMovies(movieList);
                mainActivity.showMovies(movies);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("TAG", e.getMessage());
            }

            @Override
            public void onComplete() {
                mainActivity.dismissProgressDialog();
            }
        };
    }

}
