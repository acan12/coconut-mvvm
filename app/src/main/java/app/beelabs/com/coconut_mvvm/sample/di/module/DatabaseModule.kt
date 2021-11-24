package app.beelabs.com.coconut_mvvm.sample.di.module

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

//    @Provides
//    fun provideLocationDao(appDatabase: AppDatabase) : LocationDao = appDatabase.getDao()
//
//    @Provides
//    @Singleton
//    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
//        AppDatabase.getInstance(context)

}