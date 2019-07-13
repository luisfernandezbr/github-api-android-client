package br.com.luisfernandez.github.client.mvvm

import br.com.luisfernandez.github.client.mvvm.event.AlertData
import br.com.luisfernandez.github.client.mvvm.event.OnNeedShowAlertEvent
import br.com.luisfernandez.github.client.mvvm.event.OnNeedUpdateEvent

class TestEvent{

    fun test() {
        val onNeedUpdateEvent = OnNeedUpdateEvent<Void>()

        val onNeedShowAlertEvent = OnNeedShowAlertEvent(AlertData("Test Title", "Test Message"))
        val alertData = onNeedShowAlertEvent.payload
    }
}