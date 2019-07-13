package br.com.luisfernandez.github.client.mvvm.viewresource


data class DataResource<PayloadData>(
    var status: Status,
    val payloadData: PayloadData? = null
) {
    companion object {
        fun <T>loading() = DataResource<T>(
                Status.LOADING
        )
    }
}

enum class Status {
    SUCCESS, ERROR, LOADING
}