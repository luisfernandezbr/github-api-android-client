package br.com.luisfernandez.github.client.mvvm

internal enum class Status private constructor(val key: String, val value: Int?) {

    ACTIVE("Active", 1), IN_ACTIVE("In Active", 2)
}