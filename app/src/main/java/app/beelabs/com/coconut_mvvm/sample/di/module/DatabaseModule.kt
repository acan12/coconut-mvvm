package app.beelabs.com.coconut_mvvm.sample.di.module

import android.content.Context
import androidx.room.Room
import app.beelabs.coconut.mvvm.di.manager.BaseDatabase
import app.beelabs.com.coconut_mvvm.sample.model.dao.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context.applicationContext, AppDatabase::class.java, "MyDB"
        ).allowMainThreadQueries()
            .build()
}