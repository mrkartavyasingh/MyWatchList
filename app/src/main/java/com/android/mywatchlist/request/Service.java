package com.android.mywatchlist.request;

import com.android.mywatchlist.utils.Credentials;
import com.android.mywatchlist.utils.ItemApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {
    private static final Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Credentials.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
    private static final Retrofit retrofit = retrofitBuilder.build();
    private static final ItemApi ITEM_API = retrofit.create(ItemApi.class);
    public static ItemApi getItemApi() {
        return ITEM_API;
    }
}