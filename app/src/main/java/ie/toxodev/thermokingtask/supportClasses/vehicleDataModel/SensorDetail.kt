package ie.toxodev.thermokingtask.supportClasses.vehicleDataModel


import androidx.room.ColumnInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SensorDetail(
    @SerialName("macAddress") // MAC and SERIAL attributes are swapped in the original JSON
    @ColumnInfo(name = "mac")         // The original SERIAL NUM attr would be the MAC ADDRESS
    var macAddress: String = "",

    @SerialName("serialNumber")       // MAC and SERIAL attributes are swapped in the original JSON
    @ColumnInfo(name = "serial_number")     // The original MAC attr would be the SERIAL NUM
    var serialNumber: String = "",

    @SerialName("sensorLocation")
    @ColumnInfo(name = "sensor_location")
    var sensorLocation: Int = 0,

    @SerialName("sensorName")
    @ColumnInfo(name = "sensor_name")
    var sensorName: String = "",

    @SerialName("sensorType")
    @ColumnInfo(name = "sensor_type")
    var sensorType: Int = 0,

    @SerialName("sensorZone")
    @ColumnInfo(name = "sensor_zone")
    var sensorZone: Int = 0

)