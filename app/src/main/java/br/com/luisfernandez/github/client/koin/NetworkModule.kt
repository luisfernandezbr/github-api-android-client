package br.com.luisfernandez.github.client.koin

import br.com.luisfernandez.github.client.http.GitHubService
import br.com.luisfernandez.github.client.http.livedata.AppExecutors
import br.com.luisfernandez.github.client.http.livedata.LiveDataCallAdapterFactory

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = applicationContext {
    // provided web components
    bean { createOkHttpClient() }
    // Fill property
    bean { createWebService<GitHubService>(get(), "https://api.github.com") }

    single { AppExecutors() }
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor).build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory()).build()
    return retrofit.create(T::class.java)
}