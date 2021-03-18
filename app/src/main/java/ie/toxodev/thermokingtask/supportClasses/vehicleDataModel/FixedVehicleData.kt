package ie.toxodev.thermokingtask.supportClasses.vehicleDataModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicle_info")
class FixedVehicleData {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0

    @ColumnInfo(name = "request_detail")
    var requestDetail: RequestDetail? = null //= vehicleData.requestDetails[0]

    @ColumnInfo(name = "user_detail")
    var userDetail: UserDetail? = null//= vehicleData.userDetails[0]

    @ColumnInfo(name = "vehicle_detail")
    var vehicleDetail: VehicleDetail? = null//= vehicleData.vehicleDetails[0]

    @ColumnInfo(name = "sensors")
    var sensors: MutableList<SensorDetail> = mutableListOf()//= vehicleData.vehicleDetails[1].sensorDetails


    fun setData(vehicleData: VehicleDataResponse) {
        this.id = vehicleData.vehicleDetails[0].vehicleID.toInt()
        this.vehicleDetail = vehicleData.vehicleDetails[0]
        this.requestDetail = vehicleData.requestDetails[0]
        this.userDetail = vehicleData.userDetails[0]
        this.sensors = vehicleData.vehicleDetails[1].sensorDetails
    }

//    init {
//        this.requestDetail = vehicleData.requestDetails[0]
//        this.userDetail = vehicleData.userDetails[0]
//        this.vehicleDetail = vehicleData.vehicleDetails[0]
//        this.sensors = vehicleData.vehicleDetails[1].sensorDetails
//    }
}