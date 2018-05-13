package br.com.luisfernandez.github.client.android

import android.app.Application
import br.com.luisfernandez.github.client.di.ApplicationModule
import br.com.luisfernandez.github.client.di.DaggerMainComponent
import br.com.luisfernandez.github.client.di.MainComponent

/**
 * Created by luisfernandez on 13/05/18.
 */
class AppApplication : Application() {
    companion object {
        lateinit var component: MainComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerMainComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}