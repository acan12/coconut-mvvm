package app.beelabs.coconut.mvvm.di.module

import app.beelabs.coconut.mvvm.base.interfaces.IApiService
import app.beelabs.coconut.mvvm.di.manager.ApiServiceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiServiceModule {

    @Provides
    @Singleton
    fun provideApiService(): IApiService = ApiServiceManager()
}