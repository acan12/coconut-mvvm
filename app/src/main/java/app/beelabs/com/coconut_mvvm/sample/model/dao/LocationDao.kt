package app.beelabs.com.coconut_mvvm.sample.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.beelabs.com.coconut_mvvm.sample.model.pojo.LocationEntity


@Dao
interface LocationDao {

    @Query("SELECT * FROM location ORDER BY id DESC")
    fun getLocations(): List<LocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationEntity)
}