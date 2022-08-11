package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.network.GitHubClient;
import com.example.myapplication.network.Repo;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final GitHubAdapter adapter = new GitHubAdapter();
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(R.id.list_view_repos);
        listView.setAdapter(adapter);
        final EditText editTextUsername = (EditText) findViewById(R.id.edit_text_username);
        final Button buttonSearch = (Button) findViewById(R.id.button_search);

        buttonSearch.setOnClickListener((view) -> {
            final String username = editTextUsername.getText().toString();
            if (!TextUtils.isEmpty(username)){
                getStarredRepos(username);
            }
        });
    }

    @Override protected void onDestroy() {
        if(subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }

        super.onDestroy();
    }

    private void getStarredRepos(String username){
        subscription = GitHubClient.getInstance()
                .getStarredRepos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Repo>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "In OnCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "In " + e);
                    }

                    @Override
                    public void onNext(List<Repo> repos) {
                        Log.d(TAG, "In onNext()");
                        adapter.setGitHubRepos(repos);
                    }
                });
    }
}