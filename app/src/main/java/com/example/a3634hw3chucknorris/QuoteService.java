package com.example.a3634hw3chucknorris;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuoteService {
    @GET("jokes/random?")
    Call<QuoteResponse> getQuote(
            @Query("category") String category
    );
}
