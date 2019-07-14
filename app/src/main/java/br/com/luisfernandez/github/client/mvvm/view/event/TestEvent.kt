package br.com.luisfernandez.github.client.mvvm.view.event

class TestEvent {

    fun test() {
        val onNeedUpdateEvent = OnNeedUpdateEvent<Void>()

        val onNeedShowAlertEvent = OnNeedShowAlertEvent(AlertData("Test Title", "Test Message"))
        val alertData = onNeedShowAlertEvent.payload
    }
}