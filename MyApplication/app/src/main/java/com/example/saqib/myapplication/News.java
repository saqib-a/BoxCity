package com.example.saqib.myapplication;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.example.saqib.myapplication.Adapter.FeedAdapter;
import com.example.saqib.myapplication.Common.HTTPDataHandler;
import com.example.saqib.myapplication.Model.RSSObject;
import com.google.gson.Gson;
import java.util.*;

//credit to: https://www.youtube.com/watch?v=APInjVO0WkQ
public class News extends AppCompatActivity implements View.OnClickListener{

    //private Toolbar tb;
    private RecyclerView rv;
    private RSSObject rssObject;
    private Button backButton;


    private final String RSSLink="https://www.badlefthook.com/rss/current";
    private final String JSONLink=" https://api.rss2json.com/v1/api.json?rss_url=";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(this);

       // tb = (Toolbar) findViewById(R.id.toolbar);
     //   tb.setTitle("Latest Updates");
        //setSupportActionBar(tb);

        rv = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager lLM = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(lLM);

        loadRSS();
    }

    private void loadRSS() {
        AsyncTask<String, String, String> loadRSSAsync = new AsyncTask<String, String, String>() {

            ProgressDialog mDialog = new ProgressDialog(News.this);

            @Override
            protected void onPreExecute() {
                mDialog.setMessage("Please wait...");
                mDialog.show();
            }

            @Override

            protected String doInBackground(String... strings) {
                String result;
                HTTPDataHandler httpDataHandler = new HTTPDataHandler();
                result = httpDataHandler.getHTTPData(strings[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                mDialog.dismiss();
                rssObject = new Gson().fromJson(s, RSSObject.class);
                FeedAdapter feedAdapter = new FeedAdapter(rssObject, getBaseContext());
                rv.setAdapter(feedAdapter);
                feedAdapter.notifyDataSetChanged();
            }
        };

        String stringOutput;
        StringBuilder getURLData = new StringBuilder(JSONLink);
        getURLData.append(RSSLink);
        //stringOutput = RSSLink.replaceAll("(<(!--|script)(.|\n[^<])(--|script)>)|(<|&lt;)(/?[\w!?]+)\s?[^<](>|&gt;)|(&[\w]+;)", " ");
        loadRSSAsync.execute(getURLData.toString().replaceAll("(<(!--|script)(.|\n[^<])(--|script)>)|(<|&lt;)(/?[\\w!?]+)\\s?[^<](>|&gt;)|(&[\\w]+;)", " "));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refreshMenu)
            loadRSS();
        return true;
    }

    @Override
    public void onClick(View v)
    {
        if (v == backButton)
        {
            finish();
            startActivity(new Intent(this, Profile.class));
        }
    }
}
