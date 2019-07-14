package br.com.luisfernandez.github.client.mvvm.repository.http

import android.util.Log
import br.com.luisfernandez.github.client.mvvm.repository.ErrorData
import br.com.luisfernandez.github.client.mvvm.repository.ResultWrapper
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class DeferredResponseHandler {
    suspend inline fun <SUCCESS, reified ERROR : Any> handle(
            deferredResponse: Deferred<Response<SUCCESS>>
    ): ResultWrapper<SUCCESS, ErrorData<ERROR>> {

        val resultWrapper = ResultWrapper<SUCCESS, ErrorData<ERROR>>()

        try {
            val response = deferredResponse.await()

            if (response.isSuccessful) {
                resultWrapper.success = if (response.body() != null) {
                    @Suppress("UNCHECKED_CAST")
                    response.body() as SUCCESS
                } else {
                    null
                }
            } else {
                val string = response.errorBody()?.string()

                val clazz = ERROR::class.java

                if (clazz.name == "java.lang.String") {
                    resultWrapper.error = ErrorData(errorMessage = string)
                } else {
                    val fromJson = Gson().fromJson(string, clazz)

                    val errorData = ErrorData(
                            errorBody = fromJson
                    )

                    resultWrapper.error = errorData
                }
            }

            resultWrapper.statusCode = response.code()

            val headers = response.headers()
            headers.names().map { headerKey ->
                val headerValue = headers.get(headerKey)
                resultWrapper.addKeyValue(headerKey, headerValue ?: "")
            }

        } catch (exception: Exception) {
            Log.e(TAG, "Error doing request. Error: ${exception.message}", exception)

            val customStatusCode = when (exception) {
                is SocketTimeoutException -> {
                    2100
                }
                is UnknownHostException -> {
                    2200
                }
                is ConnectException -> {
                    2300
                }
                is NoRouteToHostException -> {
                    2400
                }
                is IOException -> {
                    2500
                }
                else -> {
                    2000
                }
            }

            resultWrapper.statusCode = customStatusCode
            resultWrapper.error = ErrorData(
                    errorMessage = exception.message
            )
        }

        return resultWrapper
    }

    companion object {
        const val TAG = "DeferredResponseHandler"
    }
}