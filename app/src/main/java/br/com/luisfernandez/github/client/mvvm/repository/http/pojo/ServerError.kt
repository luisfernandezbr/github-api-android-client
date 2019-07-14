package br.com.luisfernandez.github.client.mvvm.repository.http.pojo

/**
 * Created by luisfernandez on 12/05/18.
 */

data class ServerError<ERROR_BODY>(
        var errorBody: ERROR_BODY?,
        var httpStatus: Int,
        var errorMessage: String?
)



