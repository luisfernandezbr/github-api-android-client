package br.com.luisfernandez.github.client.mvvm.view.event

import br.com.luisfernandez.github.client.mvvm.view.event.AlertData
import br.com.luisfernandez.github.client.mvvm.view.event.OnNeedShowAlertEvent
import br.com.luisfernandez.github.client.mvvm.view.event.OnNeedUpdateEvent

class TestEvent {

    fun test() {
        val onNeedUpdateEvent = OnNeedUpdateEvent<Void>()

        val onNeedShowAlertEvent = OnNeedShowAlertEvent(AlertData("Test Title", "Test Message"))
        val alertData = onNeedShowAlertEvent.payload
    }
}