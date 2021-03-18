package ie.toxodev.thermokingtask.supportClasses.vehicleDataModel


import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
class VehicleDetail(
    @SerialName("vehicleID")
    @ColumnInfo(name = "vehicle_id")
    var vehicleID: String,

    @SerialName("customer")
    @ColumnInfo(name = "customer")
    var customer: String = "",

    @SerialName("mobileNumber")
    @ColumnInfo(name = "mobile_number")
    var mobileNumber: String = "",

    @SerialName("name")
    @ColumnInfo(name = "name")
    var name: String = "",

    @SerialName("reeferSerial")
    @ColumnInfo(name = "reefer_serial")
    var reeferSerial: String = "",

    @SerialName("sensorDetails")
    @ColumnInfo(name = "sensor_detail")
    var sensorDetails: MutableList<SensorDetail> = mutableListOf()

    )