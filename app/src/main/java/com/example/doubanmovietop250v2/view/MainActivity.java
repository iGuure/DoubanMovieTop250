package com.example.doubanmovietop250v2.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.doubanmovietop250v2.R;
import com.example.doubanmovietop250v2.data.Movie;
import com.example.doubanmovietop250v2.model.IMovies;
import com.example.doubanmovietop250v2.presenter.MainPresenter;
import com.example.doubanmovietop250v2.utility.MovieAdapter;
import com.orhanobut.hawk.Hawk;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.RxActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/* *
 *
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 *
 * ━━━━━━感觉萌萌哒━━━━━━
 *
 * */

public class MainActivity extends RxActivity implements IMainActivity {

    private MainPresenter mainPresenter;

    private ArrayAdapter<String> arrayAdapter;
    private List<String> dataList;

    private int page;

    private ProgressDialog progressDialog;

    @BindView(R.id.pageSelector)
    Spinner pageSelector;

    @BindView(R.id.content)
    ListView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Hawk.init(getApplicationContext()).build();
        mainPresenter = new MainPresenter(this);
        init();
    }

    void init() {
        initSpinner();
        initProgressDialog();
    }

    void initSpinner() {
        dataList = new ArrayList<String>();
        for (int i = 1; i <= 25; i++) {
            dataList.add("第" + i + "页");
        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pageSelector.setAdapter(arrayAdapter);
        pageSelector.setSelection(0);

        page = 1;

        pageSelector.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                page = position + 1;
                mainPresenter.showSpecPageMovies(page);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void initProgressDialog() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading good movies");
        progressDialog.setCancelable(false);
    }

    @Override
    public void showMovies(IMovies movies) {
//        for (Movie movie : movies.getMovies())
//            Log.d("TAG", movie.getTitle());
        MovieAdapter movieAdapter = new MovieAdapter(MainActivity.this, R.layout.movie_item, movies.getMovies());
        content.setAdapter(movieAdapter);
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public LifecycleTransformer<List<Movie>> bindbindUntilEvent() {
        return this.<List<Movie>>bindUntilEvent(ActivityEvent.PAUSE);
    }

}
