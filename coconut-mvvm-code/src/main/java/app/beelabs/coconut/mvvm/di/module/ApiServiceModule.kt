package app.beelabs.coconut.mvvm.di.module

import android.content.Context
import app.beelabs.coconut.mvvm.base.interfaces.IApiService
import app.beelabs.coconut.mvvm.di.manager.ApiServiceManager
import app.beelabs.coconut.mvvm.support.util.NetworkMonitorUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiServiceModule {

    @Provides
    @Singleton
    fun provideApiService(): IApiService = ApiServiceManager()

}