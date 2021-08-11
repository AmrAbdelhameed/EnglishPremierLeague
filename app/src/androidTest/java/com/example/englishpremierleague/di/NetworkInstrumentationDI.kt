package com.example.englishpremierleague.di

import com.example.englishpremierleague.BuildConfig
import com.example.englishpremierleague.core.source.remote.ApiService
import com.example.englishpremierleague.core.util.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun configureNetworkForInstrumentationTest(baseTestApi: String) = module {
    single {
        val okHttpClient = OkHttpClient().newBuilder()
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

        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseTestApi)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    factory { get<Retrofit>().create(ApiService::class.java) }
}