package com.example.englishpremierleague.core.source.remote

import com.example.englishpremierleague.BuildConfig
import com.example.englishpremierleague.core.util.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiBuilder {
    private val okHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(Interceptor { chain ->
            chain.request().let {
                val newRequest = it.newBuilder()
                    .addHeader(Constants.Headers.X_AUTH_TOKEN, BuildConfig.X_Auth_Token)
                    .build()
                chain.proceed(newRequest)
            }
        }).addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()

    private fun retrofit(): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    object Companion {
        fun apiService(): ApiService = retrofit().create(ApiService::class.java)
    }
}