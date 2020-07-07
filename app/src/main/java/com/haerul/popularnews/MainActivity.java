package com.haerul.popularnews;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haerul.popularnews.api.ApiClient;
import com.haerul.popularnews.api.ApiInterface;
import com.haerul.popularnews.models.Article;
import com.haerul.popularnews.models.News;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Stack;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.telephony.TelephonyManager;

public class MainActivity<rand_int> extends AppCompatActivity implements  SwipeRefreshLayout.OnRefreshListener{

    //Enter your API here also if you have multiple API then this will randomly use any so you could have more double news request per day.
    public static final String API_KEY1 []= {""};
    Random rand = new Random();
    public static String API_KEY="";
    
    // Generate random integers in range 0 to 999
    int rand_int = 0;

    static String name="";
    static String country="in";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private Adapter adapter;
    private String TAG = MainActivity.class.getSimpleName();
    static String category="";
    private TextView topHeadline;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;
    static Stack <String> stack=new <String> Stack();

    Main2Activity act=new Main2Activity();

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        topHeadline = findViewById(R.id.topheadelines);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        rand_int=rand.nextInt(1000);
        country = getUserCountry(recyclerView.getContext());

        if(rand_int >= 500 ){
            API_KEY=API_KEY1[1];
            name="Harsh";
        }
        else{
            API_KEY=API_KEY1[0];
            name="Kalp";
        }

        if(act.hasCategory()){ //&& act.getUrl()!=null
            onLoadingSwipeRefresh("",act.getCategory(),country);
        }
        else {
            onLoadingSwipeRefresh("","",country);
        }
        Toast.makeText(this, "Welcome !", Toast.LENGTH_SHORT).show();

        errorLayout = findViewById(R.id.errorLayout);
        errorImage = findViewById(R.id.errorImage);
        errorTitle = findViewById(R.id.errorTitle);
        errorMessage = findViewById(R.id.errorMessage);
        btnRetry = findViewById(R.id.btnRetry);

    }


    public void LoadJson(String keyword,String category,String country){

        errorLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        String language = Utils.getLanguage();

        Call<News> call;
        int pageSize=50;

        if (keyword.length() > 0 ){
            call = apiInterface.getNewsSearch(keyword, language, "publishedAt", API_KEY);
        }
        else if(category.length() > 0){
            //Toast.makeText(this, "category", Toast.LENGTH_SHORT).show();
            call = apiInterface.getCatNews(country,category,pageSize,API_KEY);
        }
        else {
            //Toast.makeText(this, "no category", Toast.LENGTH_SHORT).show();
            call = apiInterface.getNews(country,pageSize,API_KEY);
        }

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticle() != null){

                    if (!articles.isEmpty()){
                        articles.clear();
                    }

                    articles = response.body().getArticle();
                    adapter = new Adapter(articles, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    initListener();

                    topHeadline.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);


                } else {

                    topHeadline.setVisibility(View.INVISIBLE);
                    swipeRefreshLayout.setRefreshing(false);

                    String errorCode;
                    switch (response.code()) {
                        case 404:
                            errorCode = "404 not found";
                            break;
                        case 500:
                            errorCode = "500 server broken";
                            break;
                        default:
                            errorCode = "unknown error";
                            break;
                    }

                    showErrorMessage(
                            R.drawable.no_result,
                            "No Result",
                            "Please Try Again!\n"+
                            errorCode);

                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                topHeadline.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                showErrorMessage(
                        R.drawable.oops,
                        "Oops..",
                        "Network failure, Please Try Again\n"+
                                t.toString());
            }
        });

    }

    public void onResume(){
        super.onResume();
        swipeRefreshLayout.setRefreshing(false);
        Main2Activity act2=new Main2Activity();
        Main3Activity act3=new Main3Activity();

        if(stack.size()!=0) {
            if (stack.peek() == "act2") {
                if (act2.hasCategory() && act2.getCategory() != category) {
                    onLoadingSwipeRefresh("", act.getCategory(), country);
                    stack.pop();
                    Toast.makeText(this, "You are watching "+  act.getCategory() + " news ", Toast.LENGTH_SHORT).show();
                }
            } else if (stack.peek() == "act3") {
                if (act3.hasCountry() && act3.getCountry() != country) {
                    country = act3.getCountry();
                    onLoadingSwipeRefresh("", act.getCategory(), country);
                    stack.pop();
                    Toast.makeText(this, "Here " + act3.getCountry() + " done " + country, Toast.LENGTH_SHORT).show();
                }
            }
        }

        category=act.getCategory();
    }

    private void initListener(){

        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView imageView = view.findViewById(R.id.img);
                Intent intent = new Intent(MainActivity.this, NewsDetailActivity.class);

                Article article = articles.get(position);
                intent.putExtra("url", article.getUrl());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("img",  article.getUrlToImage());
                intent.putExtra("date",  article.getPublishedAt());
                intent.putExtra("source",  article.getSource().getName());
                intent.putExtra("author",  article.getAuthor());

                Pair<View, String> pair = Pair.create((View)imageView, ViewCompat.getTransitionName(imageView));
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        MainActivity.this,
                        pair
                );


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(intent, optionsCompat.toBundle());
                }else {
                    startActivity(intent);
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search Latest News...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2){
                    onLoadingSwipeRefresh(query,category,country);
                }
                else {
                    Toast.makeText(MainActivity.this, "Type more than two letters!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false, false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.action_ch_category:
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                stack.push("act2");
                break;
            case R.id.action_ch_country:
                startActivity(new Intent(MainActivity.this, Main3Activity.class));
                stack.push("act3");
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        LoadJson("",category,country);
    }

    private void onLoadingSwipeRefresh(final String keyword, final String category,final String country){

        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        LoadJson(keyword,category,country);
                    }
                }
        );

    }

    private void showErrorMessage(int imageView, String title, String message){

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }

        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoadingSwipeRefresh("",category,country);
            }
        });

    }

    public static String getUserCountry(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                return simCountry.toLowerCase(Locale.US);
            }
            else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                    return networkCountry.toLowerCase(Locale.US);
                }
            }
        }
        catch (Exception e) { }
        return null;
    }

    public String getCountry()
    {
        return country;
    }

}
