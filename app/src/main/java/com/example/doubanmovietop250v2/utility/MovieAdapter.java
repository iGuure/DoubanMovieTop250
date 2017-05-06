package com.example.doubanmovietop250v2.utility;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doubanmovietop250v2.R;
import com.example.doubanmovietop250v2.data.Movie;

import java.util.List;

/**
 * Created by Guure on 2017/5/5.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    private int resourceId;

    public MovieAdapter(Context context, int textViewResourceId, List<Movie> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Movie movie = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

        ImageView movieImage = (ImageView) view.findViewById(R.id.image);
        TextView movieTitle = (TextView) view.findViewById(R.id.title);
        TextView movieOriginalTitle = (TextView) view.findViewById(R.id.originalTitle);
        TextView movieYear = (TextView) view.findViewById(R.id.year);
        TextView movieGenres = (TextView) view.findViewById(R.id.genres);
        TextView movieAlt = (TextView) view.findViewById(R.id.alt);
        LinearLayout MovieDirectorLayout = (LinearLayout) view.findViewById(R.id.directorLayout);
        LinearLayout MovieCastLayout = (LinearLayout) view.findViewById(R.id.castLayout);

        movieTitle.setText(movie.getTitle());
        movieOriginalTitle.setText(movie.getOriginal_title());
        movieYear.setText(movie.getYear());

        String genres = "";
        genres += "[ ";
        for (String s : movie.getGenres())
            genres += s + " ";
        genres += "]";
        movieGenres.setText(genres);

        movieAlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(movie.getAlt());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                getContext().startActivity(intent);
            }
        });

        Glide.with(getContext())
                .load(movie.getImages().getLarge())
                .override(650, 1000)
                .dontAnimate()
                .into(movieImage);

        for (final Movie.DirectorsBean directorsBean : movie.getDirectors()) {
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), directorsBean.getName(), Toast.LENGTH_SHORT).show();
                }
            });
            MovieDirectorLayout.addView(imageView);
            if (directorsBean.getAvatars() == null) {
                int resource = R.mipmap.ic_launcher;
                Glide.with(getContext())
                        .load(resource)
                        .override(140, 200)
                        .into(imageView);
            } else {
                Glide.with(getContext())
                        .load(directorsBean.getAvatars().getMedium())
                        .override(140, 200)
                        .into(imageView);
            }
        }

        for (final Movie.CastsBean castsBean : movie.getCasts()) {
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), castsBean.getName(), Toast.LENGTH_SHORT).show();
                }
            });
            MovieCastLayout.addView(imageView);
            if (castsBean.getAvatars() == null) {
                int resource = R.mipmap.ic_launcher;
                Glide.with(getContext())
                        .load(resource)
                        .override(140, 200)
                        .into(imageView);
            } else {
                Glide.with(getContext())
                        .load(castsBean.getAvatars().getMedium())
                        .override(140, 200)
                        .into(imageView);
            }
        }

        return view;
    }
}
