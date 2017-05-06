package com.example.doubanmovietop250v2.model;

import com.example.doubanmovietop250v2.data.Movie;

import java.util.List;

/**
 * Created by Guure on 2017/5/5.
 */

public interface IMovies {

    void setMovies(List<Movie> movieList);

    List<Movie> getMovies();

}
