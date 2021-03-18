package ie.toxodev.thermokingtask.supportClasses.vehicleDataModel


import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Entity
@Serializable
class UserDetail(
    @SerialName("userID")
    @ColumnInfo(name = "user_id")
    var userID: Int
)