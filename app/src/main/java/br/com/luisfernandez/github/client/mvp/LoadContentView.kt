package br.com.luisfernandez.github.client.mvp

/**
 * Created by luisfernandez on 13/05/18.
 */
interface LoadContentView<CONTENT, ERROR_BODY>: ErrorHandlerView<ERROR_BODY> {
    fun showLoading()
    fun showEmpty()
    fun showContent(content: CONTENT)
}