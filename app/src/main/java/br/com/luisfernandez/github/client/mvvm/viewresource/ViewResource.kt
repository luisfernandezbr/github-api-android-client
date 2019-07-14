package br.com.luisfernandez.github.client.mvvm.viewresource

sealed class ViewResource<out PayloadType : Payload>(
        open val viewStatus: ViewStatus, open val payload: PayloadType
)

data class ViewResourceError<T : Payload>(
        override var payload: T
) : ViewResource<T>(ViewStatus.ERROR, payload)

data class ViewResourceSuccess<T : Payload>(
        override var payload: T
) : ViewResource<T>(ViewStatus.SUCCESS, payload)


data class ViewResourceLoading<T : Payload>(
        override var payload: T
) : ViewResource<T>(ViewStatus.PROGRESS, payload)

object VoidLoading

interface Payload
