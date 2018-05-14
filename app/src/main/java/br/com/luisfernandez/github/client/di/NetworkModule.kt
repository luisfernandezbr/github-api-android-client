package br.com.luisfernandez.github.client.di

import br.com.luisfernandez.github.client.BuildConfig
import br.com.luisfernandez.github.client.android.AppApplication
import br.com.luisfernandez.github.client.http.ForceCacheInterceptor
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

import okhttp3.Interceptor
import okhttp3.ConnectionSpec
import okhttp3.TlsVersion
import android.os.Build
import android.util.Log
import br.com.luisfernandez.github.client.http.Tls12SocketFactory
import javax.net.ssl.SSLContext


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
                    .setDateFormat(DATE_FORMAT)
                    .create()

    @Provides
    @Singleton
    fun provideHttpLogging(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(
                    if (BuildConfig.DEBUG)
                        HttpLoggingInterceptor.Level.HEADERS
                    else
                        HttpLoggingInterceptor.Level.HEADERS
            )

    @Provides
    @Singleton
    fun provideForceCacheInterceptor(): Interceptor = ForceCacheInterceptor()

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
            forceCacheInterceptor: ForceCacheInterceptor,
            httpLoggingInterceptor: HttpLoggingInterceptor,
            cache: Cache
    ): OkHttpClient {

        return enableTls12OnPreLollipop(OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(forceCacheInterceptor)
                .addNetworkInterceptor(forceCacheInterceptor)
                .cache(cache)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                ).build()
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

    /**
     * Solution from https://github.com/square/okhttp/issues/2372
     */
    private fun enableTls12OnPreLollipop(client: OkHttpClient.Builder): OkHttpClient.Builder {
        if (Build.VERSION.SDK_INT in Build.VERSION_CODES.JELLY_BEAN .. Build.VERSION_CODES.LOLLIPOP) {
            try {
                val sc = SSLContext.getInstance("TLSv1.2")
                sc.init(null, null, null)
                client.sslSocketFactory(Tls12SocketFactory(sc.socketFactory))
                val cs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).tlsVersions(TlsVersion.TLS_1_2).build()

                val specs = ArrayList<ConnectionSpec>()
                specs.add(cs)
                specs.add(ConnectionSpec.COMPATIBLE_TLS)
                specs.add(ConnectionSpec.CLEARTEXT)
                client.connectionSpecs(specs)
            } catch (exc: Exception) {
                Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc)
            }
        }

        return client
    }
}