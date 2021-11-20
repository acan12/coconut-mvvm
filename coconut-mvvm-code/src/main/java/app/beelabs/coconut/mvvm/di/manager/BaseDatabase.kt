package app.beelabs.coconut.mvvm.di.manager

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.reflect.KClass

abstract class BaseDatabase : RoomDatabase() {
    companion object {
        fun getAppDatabase(dbname: String, databaseClass: Class<BaseDatabase>, context: Context) =
            Room.databaseBuilder(
                context.applicationContext, databaseClass, dbname
            ).allowMainThreadQueries()
                .build()
    }
}