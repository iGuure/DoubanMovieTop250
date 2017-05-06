package com.example.doubanmovietop250v2.view;

import com.example.doubanmovietop250v2.data.Movie;
import com.example.doubanmovietop250v2.model.IMovies;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by Guure on 2017/5/5.
 */

public interface IMainActivity {

    void showMovies(IMovies movies);

    void showProgressDialog();

    void dismissProgressDialog();

    LifecycleTransformer<List<Movie>> bindbindUntilEvent();

}
