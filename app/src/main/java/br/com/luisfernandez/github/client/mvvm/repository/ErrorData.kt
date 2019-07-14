package br.com.luisfernandez.github.client.mvvm.repository

data class ErrorData<ERROR_BODY>(
    var errorBody: ERROR_BODY? = null,
    var errorMessage: String? = null
)



