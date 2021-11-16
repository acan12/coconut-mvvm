package app.beelabs.com.coconut_mvvm.sample.di.module

import app.beelabs.com.coconut_mvvm.sample.model.api.Api
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
    fun provideApi(): Api = Api()
}