package br.com.luisfernandez.github.client.http

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Solution from https://gist.github.com/polbins/1c7f9303d2b7d169a3b1
 * https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.4
 */
class ForceCacheInterceptor() : Interceptor
{
    override fun intercept(chain: Interceptor.Chain?): Response
    {
        val request = chain!!.request()

        if (request.method().equals("GET"))
        {
            request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                    .build()
        }

        val response = chain.proceed(request)

        // Re-write response CC header to force use of cache
        return response.newBuilder()
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, max-age=86400") // 1 day
                .build()
    }
}