package app.beelabs.com.coconut_mvvm.sample.model.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.beelabs.com.coconut_mvvm.sample.model.api.IConfig
import app.beelabs.com.coconut_mvvm.sample.model.pojo.LocationEntity

@Database(entities = [LocationEntity::class], version = 1, exportSchema = false)
abstract class MvvmDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao

    companion object {
        @Volatile
        private var INSTANCE: MvvmDatabase? = null

        fun getDatabase(context: Context): MvvmDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MvvmDatabase::class.java,
                    IConfig.APP_DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }


}