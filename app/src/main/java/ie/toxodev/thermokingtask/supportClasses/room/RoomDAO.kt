package ie.toxodev.thermokingtask.supportClasses.room

import androidx.room.*
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.FixedVehicleData

@Dao
interface RoomDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vehicleDataResponse: FixedVehicleData): Long

    @Query("SELECT * FROM vehicle_info")
    suspend fun fetchSavedVehicles(): List<FixedVehicleData>

    @Query("SELECT * FROM vehicle_info WHERE id = :vehicleId")
    suspend fun fetchVehicleInfo(vehicleId: Long): FixedVehicleData

    @Update
    suspend fun updateVehicleInfo(vehicleData: FixedVehicleData): Int
}