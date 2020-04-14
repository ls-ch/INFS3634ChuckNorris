package com.example.a3634hw3chucknorris;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private String TAG = "Main Activity";
    TextView quote;
    TextView author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting textviews, with tvAuthor being invisible until button is pressed
        author = (TextView) findViewById(R.id.tvAuthor);
        author.setVisibility(View.INVISIBLE);
        quote = (TextView) findViewById(R.id.tvQuote);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getQuote();

            }
        });
    }

    private void getQuote() {
        // Making a retrofit to automate data retrieval from API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.chucknorris.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QuoteService service = retrofit.create(QuoteService.class);
        Call<QuoteResponse> quoteResponseCall = service.getQuote("dev");

        // Execute an API call from Quote
        quoteResponseCall.enqueue(new Callback<QuoteResponse>() {
            @Override
            public void onResponse(Call<QuoteResponse> call, Response<QuoteResponse> response) {
                if (response.isSuccessful()) {
                    quote.setText(response.body().getValue());
                    author.setVisibility(View.VISIBLE);
                    Log.d(TAG, "onResponse: Successful API response");

                } else {
                    Log.d(TAG, "onResponse: ERROR IS:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<QuoteResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: ON FAILURE IS" + t.getLocalizedMessage());
            }

        });
    }
}
