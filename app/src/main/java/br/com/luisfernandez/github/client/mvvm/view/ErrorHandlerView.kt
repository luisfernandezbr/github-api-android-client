package br.com.luisfernandez.github.client.mvvm.view

import br.com.luisfernandez.github.client.mvvm.repository.http.pojo.ServerError

/**
 * Created by luisfernandez on 12/05/18.
 */
interface ErrorHandlerView<ERROR_BODY> {
    fun handleError(serverError: ServerError<ERROR_BODY>)
}
