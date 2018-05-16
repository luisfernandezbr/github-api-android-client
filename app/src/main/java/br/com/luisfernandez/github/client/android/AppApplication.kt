package br.com.luisfernandez.github.client.android

import android.app.Application
import br.com.luisfernandez.github.client.di.ApplicationModule
import br.com.luisfernandez.github.client.di.DaggerMainComponent
import br.com.luisfernandez.github.client.di.MainComponent
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import io.fabric.sdk.android.Fabric

/**
 * Created by luisfernandez on 13/05/18.
 */
class AppApplication : Application() {
    companion object {
        lateinit var component: MainComponent
    }

    override fun onCreate() {
        super.onCreate()

        val fabric = Fabric.Builder(this)
                .kits(Crashlytics(), Answers())
                .debuggable(true)
                .build()
        Fabric.with(fabric)

        component = DaggerMainComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()

    }
}