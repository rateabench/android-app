package com.rateabench.rateabench

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.rateabench.rateabench.api.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

object ApiFactory {

    private val API_URL = "https://www.jsonstore.io/84955eed692c2b53d9de796489c40878631ec50e4beeb12af9c1bafefe09e0cb/"
    //    private val API_URL = "http://my-json-server.typicode.com/schurmann/json-server/"
    //Creating Auth Interceptor to add api_key query in front of all the requests.
    private val authInterceptor = Interceptor { chain ->
        //        val newUrl = chain.request().url().newBuilder().addQueryParameter("api_key", AppConstants.tmdbApiKey).build()
        //        val newRequest = chain.request().newBuilder().url(newUrl).build()

        chain.proceed(chain.request())
    }
    private val logInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
        Timber.tag("OkHttp").d(message)
    }).setLevel(HttpLoggingInterceptor.Level.BODY)

    private val apiClient =
        OkHttpClient().newBuilder().addInterceptor(authInterceptor).addInterceptor(logInterceptor).build()
    val benchService: ApiService = retrofit().create(ApiService::class.java)


    private fun retrofit(): Retrofit = Retrofit.Builder().client(apiClient).baseUrl(API_URL).addConverterFactory(
        GsonConverterFactory.create()
    ).addCallAdapterFactory(CoroutineCallAdapterFactory()).build()


}