package br.com.luisfernandez.github.client

/**
 * Created by luisfernandez on 12/05/18.
 */
interface OnItemClick<T> {
    fun onItemClick(type: T)
}