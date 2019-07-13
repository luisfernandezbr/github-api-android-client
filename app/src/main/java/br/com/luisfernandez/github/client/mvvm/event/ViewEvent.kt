package br.com.luisfernandez.github.client.mvvm.event

open class ViewEvent<EventPayload> {

    var payload: EventPayload? = null

    constructor()

    constructor(eventPayload: EventPayload) {
        this.payload= eventPayload
    }
}
