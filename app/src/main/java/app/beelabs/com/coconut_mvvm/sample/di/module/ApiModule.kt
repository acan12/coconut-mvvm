package app.beelabs.com.coconut_mvvm.sample.di.module

import app.beelabs.coconut.mvvm.base.interfaces.IApiService
import app.beelabs.coconut.mvvm.di.manager.ApiServiceManager
import app.beelabs.com.coconut_mvvm.sample.model.api.Api
import app.beelabs.com.coconut_mvvm.sample.model.api.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideRemoteData(): RemoteDataSource = RemoteDataSource(provideApi())

    @Provides
    @Singleton
    fun provideApi(): Api = Api(provideApiService())

    @Provides
    @Singleton
    fun provideApiService(): IApiService = ApiServiceManager()
}