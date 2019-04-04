package br.com.luisfernandez.github.client.android

import android.app.Application
import br.com.luisfernandez.github.client.koin.appModules
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.startKoin

/**
 * Created by luisfernandez on 13/05/18.
 */
class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val fabric = Fabric.Builder(this)
                .kits(Crashlytics(), Answers())
                .debuggable(true)
                .build()
        Fabric.with(fabric)

        // start Koin context
        startKoin(this, appModules)
    }
}