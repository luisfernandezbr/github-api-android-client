package br.com.luisfernandez.github.client.mvvm.repository

import br.com.luisfernandez.github.client.http.model.ServerError
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