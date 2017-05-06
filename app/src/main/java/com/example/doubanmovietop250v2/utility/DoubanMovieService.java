package com.example.doubanmovietop250v2.utility;

import com.example.doubanmovietop250v2.data.Movie;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Guure on 2017/5/5.
 */

public interface DoubanMovieService {

    @GET("top250")
    Observable<HttpResult<List<Movie>>> getMovies(@Query("start") int start, @Query("count") int count);

}
