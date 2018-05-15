package br.com.luisfernandez.github.client.mvp

import br.com.luisfernandez.github.client.http.model.ServerError

/**
 * Created by luisfernandez on 12/05/18.
 */
interface ErrorHandlerView<ERROR_BODY> {
    fun handleError(serverError: ServerError<ERROR_BODY>)
}
