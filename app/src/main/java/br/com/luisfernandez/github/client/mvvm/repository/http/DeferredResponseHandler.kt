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
            val retrofitResponse = deferredResponse.await()

            if (retrofitResponse.isSuccessful) {
                resultWrapper.success = if (retrofitResponse.body() != null) {
                    @Suppress("UNCHECKED_CAST")
                    retrofitResponse.body() as SUCCESS
                } else {
                    null
                }
            } else {
                val errorString = retrofitResponse.errorBody()?.string()
                val errorClazz = ERROR::class.java

                if (errorClazz.name == "java.lang.String") {
                    resultWrapper.error = ErrorData(errorMessage = errorString)
                } else {
                    val errorBody = Gson().fromJson(errorString, errorClazz)

                    val errorData = ErrorData(
                            errorBody = errorBody
                    )

                    resultWrapper.error = errorData
                }
            }

            resultWrapper.statusCode = retrofitResponse.code()

            val headers = retrofitResponse.headers()
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