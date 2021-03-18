package ie.toxodev.thermokingtask.supportClasses.vehicleDataModel


import androidx.room.ColumnInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SensorDetail(
    @SerialName("macAddress")
    @ColumnInfo(name = "mac")
    var macAddress: String = "",

    @SerialName("serialNumber")
    @ColumnInfo(name = "serial_number")
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
) {
    init {
        /********* MAC and SERIAL attributes are swapped in the original JSON ********/
        val mac = this.serialNumber // The MAC NUM attr is the SERIAL ATTR in Original JSON
        val serial = this.macAddress // The SERIAL NUM attr is the MAC ATTR in Original JSON
        this.macAddress = mac
        this.serialNumber = serial
    }
}