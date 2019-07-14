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

            this.handleResponse(resultWrapper, retrofitResponse)
            this.handleStatusCode(resultWrapper, retrofitResponse)
            this.handleHeaders(resultWrapper, retrofitResponse)

        } catch (exception: Exception) {
            Log.e(TAG, "Error doing request. Error: ${exception.message}", exception)

            this.handleExceptionResponse(resultWrapper, exception)
        }

        return resultWrapper
    }

    fun <SUCCESS, ERROR> handleExceptionResponse(resultWrapper: ResultWrapper<SUCCESS, ErrorData<ERROR>>, exception: Exception) {

        resultWrapper.statusCode = this.getStatusCodeFromException(exception)
        resultWrapper.error = ErrorData(
                errorMessage = exception.message
        )
    }

    inline fun <SUCCESS, reified ERROR : Any> handleResponse(resultWrapper: ResultWrapper<SUCCESS, ErrorData<ERROR>>, retrofitResponse: Response<SUCCESS>) {
        if (retrofitResponse.isSuccessful) {
            this.handleSuccessResponse(resultWrapper, retrofitResponse)
        } else {
            this.handleErrorResponse(resultWrapper, retrofitResponse)
        }
    }

    inline fun <SUCCESS, reified ERROR : Any> handleErrorResponse(resultWrapper: ResultWrapper<SUCCESS, ErrorData<ERROR>>, retrofitResponse: Response<SUCCESS>) {
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

    fun <SUCCESS, ERROR> handleSuccessResponse(resultWrapper: ResultWrapper<SUCCESS, ErrorData<ERROR>>, retrofitResponse: Response<SUCCESS>) {
        resultWrapper.success = if (retrofitResponse.body() != null) {
            @Suppress("UNCHECKED_CAST")
            retrofitResponse.body() as SUCCESS
        } else {
            null
        }
    }

    fun <SUCCESS, ERROR> handleStatusCode(resultWrapper: ResultWrapper<SUCCESS, ErrorData<ERROR>>, retrofitResponse: Response<SUCCESS>) {
        resultWrapper.statusCode = retrofitResponse.code()
    }

    fun <SUCCESS, ERROR> handleHeaders(resultWrapper: ResultWrapper<SUCCESS, ErrorData<ERROR>>, retrofitResponse: Response<SUCCESS>) {
        val headers = retrofitResponse.headers()
        headers.names().map { headerKey ->
            val headerValue = headers.get(headerKey)
            resultWrapper.addKeyValue(headerKey, headerValue ?: "")
        }
    }

    private fun getStatusCodeFromException(exception: Exception): Int {
        return when (exception) {
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
    }

    companion object {
        const val TAG = "DeferredResponseHandler"
    }
}