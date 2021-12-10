package app.beelabs.coconut.mvvm.di.module

import app.beelabs.coconut.mvvm.base.interfaces.IProgress
import app.beelabs.coconut.mvvm.component.dialog.ProgressDialogComponent
import app.beelabs.coconut.mvvm.di.manager.ProgressManager
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