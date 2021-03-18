package ie.toxodev.thermokingtask.supportClasses.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.FixedVehicleData

@Database(entities = [FixedVehicleData::class], version = 1)
@TypeConverters(DaoTypeConverters::class)
abstract class VehicleDatabase : RoomDatabase() {

    abstract fun getDao(): RoomDAO

    companion object {
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            VehicleDatabase::class.java,
            "vehicleDatabase"
        ).build()

        @Volatile
        private var instance: VehicleDatabase? = null
        var lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            buildDatabase(context).also {
                instance = it
            }
        }
    }
}