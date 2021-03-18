package ie.toxodev.thermokingtask.supportClasses.vehicleDataModel


import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Entity
@Serializable
class RequestDetail(
    @SerialName("messageType")
    @ColumnInfo(name = "message_type")
    var messageType: Int,

    @SerialName("mobileAppID")
    @ColumnInfo(name = "mobile_app_id")
    var mobileAppID: Int,

    @SerialName("vID")
    @ColumnInfo(name = "v_id")
    var vID: String,
)