package br.com.luisfernandez.github.client.http

import br.com.luisfernandez.github.client.BuildConfig
import br.com.luisfernandez.github.client.http.annotations.URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by luisfernandez on 10/05/18.
 */
class ServiceFactory {
    companion object {
        fun getClient(timeout: Int): okhttp3.OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG)
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                    .connectTimeout(timeout.toLong(), TimeUnit.SECONDS)
                    .readTimeout(timeout.toLong(), TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .build()
        }

        fun getGsonBuilder(): Gson {
            return GsonBuilder()
                    .create()
        }

        fun <T> createRetrofitService(clazz: Class<T>): T {
            val url = loadUrlFromServiceAnnotation(clazz)

            return createService(clazz, url)
        }

        private fun <T> loadUrlFromServiceAnnotation(clazz: Class<T>): String {
            val annotations = clazz.annotations

            var url = ""

            for (a in annotations) {
                if (a is URL) {
                    url = a.value
                    break
                }
            }
            return url
        }

        private fun <T> createService(clazz: Class<T>, url: String): T {
            val retrofit = getRetrofit(url)

            return retrofit.create(clazz)
        }

        private fun getRetrofit(url: String): Retrofit {
            val gsonBuilder = getGsonBuilder()
            val okHttpClient = getClient(30)

            return Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()
        }
    }
}