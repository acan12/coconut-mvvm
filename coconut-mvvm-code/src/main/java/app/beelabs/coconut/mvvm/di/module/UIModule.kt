package app.beelabs.coconut.mvvm.di.module

import app.beelabs.coconut.mvvm.component.dialog.ProgressDialogComponent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UIModule {

    @Provides
    @Singleton
    fun provideProgressDialog(): ProgressDialogComponent = ProgressDialogComponent()
}