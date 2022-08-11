package com.example.myapplication.view;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.network.Repo;

import java.util.ArrayList;
import java.util.List;

public class GitHubAdapter extends BaseAdapter {
    private final List<Repo> gitHubRepos = new ArrayList<>();

    @Override
    public int getCount() {
        return gitHubRepos.size();
    }

    @Override
    public Repo getItem(int i) {
        if(i < 0  | i >= gitHubRepos.size()){
        return null;
        }else {
           return gitHubRepos.get(i);
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final View view = (convertView != null ? convertView: createView(viewGroup));

        final GitHubRepoViewHolder viewHolder = (GitHubRepoViewHolder) view.getTag();
        viewHolder.setGitHubRepo((Repo) getItem(i));
        return view;
    }

    public void setGitHubRepos(@Nullable List<Repo> repos) {
        if (repos == null){
            return;
        }
        gitHubRepos.clear();
        gitHubRepos.addAll(repos);
        notifyDataSetChanged();
    }

    private View createView(ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_github_repo, parent, false);
        final GitHubRepoViewHolder viewHolder = new GitHubRepoViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    private static class GitHubRepoViewHolder{
        private final TextView textRepoName;
        private final TextView textRepoDescription;
        private final TextView textLanguage;
        private final TextView textStars;

        public GitHubRepoViewHolder(View view){
            textRepoName = (TextView) view.findViewById(R.id.text_repo_name);
            textRepoDescription = (TextView) view.findViewById(R.id.text_repo_description);
            textLanguage = (TextView) view.findViewById(R.id.text_language);
            textStars = (TextView) view.findViewById(R.id.text_stars);
        }


        @SuppressLint("SetTextI18n")
        public void setGitHubRepo(Repo gitHubRepo){
            textRepoName.setText(gitHubRepo.name);
            textRepoDescription.setText(gitHubRepo.description);
            textLanguage.setText("Language: " + gitHubRepo.language);
            textStars.setText("Stars: " + gitHubRepo.stargazersCount);
        }
    }


}
