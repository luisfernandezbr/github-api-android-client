package br.com.luisfernandez.github.client.http

import java.util.HashMap

data class ResultWrapper<SUCCESS, ERROR>(
        var success: SUCCESS? = null,
        var error: ERROR? = null,
        var keyValueMap: MutableMap<String, String>? = null,
        var statusCode: Int  = -1,
        var genericErrorMessage: String? = null
) {
    fun addKeyValue(key: String, value: String) {
        if (this.keyValueMap == null) {
            this.keyValueMap = HashMap()
        }

        this.keyValueMap!![key] = value
    }

    fun getValue(key: String): String? {
        var result: String? = null

        if (keyValueMap != null) {
            result = keyValueMap!![key]
        }

        return result
    }
}

abstract class ResultWrapperAdapter<FROM_SUCCESS, FROM_ERROR, TO_SUCCESS, TO_ERROR> {
    fun adapt(
            resultWrapper: ResultWrapper<FROM_SUCCESS, FROM_ERROR>
    ): ResultWrapper<TO_SUCCESS, TO_ERROR> {

        val transformSuccess = this.adaptSuccess(resultWrapper.success)
        val transformError = this.adaptError(resultWrapper.error)
        val transformStatusCode = this.adaptStatusCode(resultWrapper.statusCode)

        val wrapper: ResultWrapper<TO_SUCCESS, TO_ERROR> = ResultWrapper(
                success = transformSuccess,
                error = transformError,
                statusCode = transformStatusCode
        )

        resultWrapper.keyValueMap?.run {
            wrapper.keyValueMap = this@ResultWrapperAdapter.adaptKeyValueMap(this)
        }

        return wrapper
    }

    private fun adaptStatusCode(statusCode: Int) : Int {
        return statusCode
    }

    open fun adaptKeyValueMap(fromKeyValueMap: MutableMap<String, String>) : MutableMap<String, String>? {
        return fromKeyValueMap
    }

    abstract fun adaptSuccess(fromSuccess: FROM_SUCCESS?): TO_SUCCESS?

    abstract fun adaptError(fromError: FROM_ERROR?): TO_ERROR?
}