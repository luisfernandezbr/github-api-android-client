package br.com.luisfernandez.github.client.di

import br.com.luisfernandez.github.client.BuildConfig
import br.com.luisfernandez.github.client.android.AppApplication
import br.com.luisfernandez.github.client.http.GitHubService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
open class NetworkModule
{
    companion object
    {
        private const val CACHE: Long = 10 * 1024 * 1024
        private const val TIME_OUT: Long = 15L
        private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    }

    @Provides
    @Singleton
    @Named("HTTP_URL")
    fun provideHost(): String = "https://api.github.com"

    @Provides
    @Singleton
    fun provideGson(): Gson =
            GsonBuilder()
                    //.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .setDateFormat(DATE_FORMAT)
                    .create()

    @Provides
    @Singleton
    fun provideHttpLogging(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(
                    if (BuildConfig.DEBUG)
                        HttpLoggingInterceptor.Level.BODY
                    else
                        HttpLoggingInterceptor.Level.NONE
            )

    @Provides
    @Singleton
    fun provideGSONConverter(gson: Gson): GsonConverterFactory =
            GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideCache(
            paterApplication: AppApplication
    ): Cache = Cache(paterApplication.cacheDir, CACHE)

    @Provides
    @Singleton
    fun provideOkHttp(
            httpLoggingInterceptor: HttpLoggingInterceptor,
            cache: Cache
    ): OkHttpClient {
        return OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .followSslRedirects(false)
                .followRedirects(false)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@Named("HTTP_URL") baseUrl: String, okHttpClient: OkHttpClient, GSONConverterFactory: GsonConverterFactory): Retrofit =
            Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GSONConverterFactory)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): GitHubService = retrofit.create(GitHubService::class.java)
}