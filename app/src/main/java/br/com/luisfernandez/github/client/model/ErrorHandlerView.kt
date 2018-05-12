package br.com.luisfernandez.github.client.model

import br.com.luisfernandez.github.client.ServerError

/**
 * Created by luisfernandez on 12/05/18.
 */
interface ErrorHandlerView<ERROR_BODY> {
    fun handleError(serverError: ServerError<ERROR_BODY>)
}
