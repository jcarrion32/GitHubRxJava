package com.example.myapplication.network;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface Service {
    @GET("users/{user}/repos")
    Observable<List<Repo>>
    getStarredRepositories(@Path("user") String userName);
}
