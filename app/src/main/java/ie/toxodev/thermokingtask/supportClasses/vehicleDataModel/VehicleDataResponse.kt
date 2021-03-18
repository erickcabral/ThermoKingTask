package ie.toxodev.thermokingtask.supportClasses.vehicleDataModel


import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
class VehicleDataResponse(
    @SerialName("requestDetails")
    @ColumnInfo(name = "request_details")
    var requestDetails: List<RequestDetail> = emptyList(),

    @SerialName("userDetails")
    @ColumnInfo(name = "user_details")
    var userDetails: List<UserDetail> = emptyList(),

    @SerialName("vehicleDetails")
    @ColumnInfo(name = "vehicle_details")
    var vehicleDetails: MutableList<VehicleDetail> = mutableListOf()

) {
//    @PrimaryKey(autoGenerate = true)
//    var id: Int = 0
}