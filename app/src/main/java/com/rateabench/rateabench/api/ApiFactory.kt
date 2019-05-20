package com.rateabench.rateabench.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

object ApiFactory {

    private val API_URL = "http://192.168.1.248:8080/v1/"
    //Creating Auth Interceptor to add api_key query in front of all the requests.
    private val authInterceptor = Interceptor { chain ->
        chain.proceed(chain.request())
    }
    private val logInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
        Timber.tag("OkHttp").d(message)
    }).setLevel(HttpLoggingInterceptor.Level.NONE)

    private val apiClient = OkHttpClient().newBuilder().addInterceptor(authInterceptor).addInterceptor(
        logInterceptor
    ).build()
    val benchService: ApiService = retrofit().create(ApiService::class.java)


    private fun retrofit(): Retrofit = Retrofit.Builder().client(apiClient).baseUrl(
        API_URL
    ).addConverterFactory(
        GsonConverterFactory.create()
    ).addCallAdapterFactory(CoroutineCallAdapterFactory()).build()


}