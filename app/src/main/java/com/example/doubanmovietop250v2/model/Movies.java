package com.example.doubanmovietop250v2.model;

import com.example.doubanmovietop250v2.data.Movie;

import java.util.List;

/**
 * Created by Guure on 2017/5/5.
 */

public class Movies implements IMovies {

    private List<Movie> movieList;

    @Override
    public void setMovies(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @Override
    public List<Movie> getMovies() {
        return movieList;
    }

}
