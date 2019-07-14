package br.com.luisfernandez.github.client.mvvm.view.event

class OnNeedShowAlertEvent<AlertData> : ViewEvent<AlertData> {
    constructor(alertData: AlertData) : super(alertData)
}

data class AlertData(
        val title: String,
        val message: String
)