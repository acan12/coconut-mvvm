package app.beelabs.coconut.mvvm.di.module

import app.beelabs.coconut.mvvm.base.interfaces.IApi
import app.beelabs.coconut.mvvm.di.manager.ApiManager
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
    fun provideApi(): IApi = ApiManager()
}