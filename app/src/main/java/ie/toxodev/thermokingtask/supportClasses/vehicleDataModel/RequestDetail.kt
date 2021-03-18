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
    var messageType: Int =0,

    @SerialName("mobileAppID")
    @ColumnInfo(name = "mobile_app_id")
    var mobileAppID: Int =0,

    @SerialName("vID")
    @ColumnInfo(name = "v_id")
    var vID: String ="",
)