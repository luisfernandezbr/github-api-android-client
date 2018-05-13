package br.com.luisfernandez.github.client.di

import br.com.luisfernandez.github.client.android.AppApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by luisfernandez on 13/05/18.
 */
@Module
open class ApplicationModule(private val appApplication: AppApplication)
{
    @Provides
    @Singleton
    fun provideApplication(): AppApplication = appApplication
}