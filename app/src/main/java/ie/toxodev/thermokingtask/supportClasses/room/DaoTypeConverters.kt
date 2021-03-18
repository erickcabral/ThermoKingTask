package ie.toxodev.thermokingtask.supportClasses.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.RequestDetail
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.SensorDetail
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.UserDetail
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.VehicleDetail

open class DaoTypeConverters {

    private val gson = Gson()

    //================= TrainingModelList Converter ================= //
    @TypeConverter
    fun fromListOfTrainingModel(list: List<VehicleDetail>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toListOfTrainingModel(jListString: String): List<VehicleDetail> {
        val listOfTrainingSets = gson.fromJson(
            jListString,
            List::class.java
        ) as List<VehicleDetail>

        return listOfTrainingSets
    }

    // ====================== SENSOR DETAILS ===================== //
    @TypeConverter
    fun sensorDetails(sensorDetail: SensorDetail): String {
        return gson.toJson(sensorDetail, SensorDetail::class.java)
    }

    @TypeConverter
    fun jsonSensorDetail(jListString: String): SensorDetail {
        return gson.fromJson(jListString, SensorDetail::class.java)
    }

    @TypeConverter
    fun fromSensorListToJason(list: List<SensorDetail>): String {
        return gson.toJson(list, List::class.java)
    }

    @TypeConverter
    fun toListOfSensors(jListString: String): List<SensorDetail> {
        gson.fromJson(jListString, List::class.java).run {
            val mutableList: MutableList<SensorDetail> = mutableListOf()
            this.forEach { model ->
                model as LinkedTreeMap<String, Any>
                SensorDetail().apply {
                    macAddress = model["serialNumber"] as String //SERIAL NUMBER AND MAC ATTRIBUTES
                    serialNumber = model["macAddress"] as String // ARE SWITCHED IN THE ORIGINAL JSON DATA
                    sensorName = model["sensorName"] as String
                    sensorZone = (model["sensorZone"] as Double).toInt()
                    sensorType = (model["sensorType"] as Double).toInt()
                    sensorLocation = (model["sensorLocation"] as Double).toInt()
                }.also {
                    mutableList.add(it)
                }
            }
            return mutableList
        }
    }
    // ======================================================================== //

    @TypeConverter
    fun fromRequestDetailToJson(list: List<RequestDetail>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toListOfRequestDetail(jListString: String): List<RequestDetail> {
        val listOfTrainingSets = gson.fromJson(
            jListString,
            List::class.java
        ) as List<RequestDetail>

        return listOfTrainingSets
    }

    @TypeConverter
    fun fromUserDetailListToJson(list: List<UserDetail>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toListOfUserDetail(jListString: String): List<UserDetail> {
        val listOfTrainingSets = gson.fromJson(
            jListString,
            List::class.java
        ) as List<UserDetail>

        return listOfTrainingSets
    }

    //========================= User Detail ================ //
    @TypeConverter
    fun userDetailToJson(userDetail: UserDetail): String {
        return gson.toJson(userDetail)
    }

    @TypeConverter
    fun jsonToUserDetail(jListString: String): UserDetail {
        return gson.fromJson(jListString, UserDetail::class.java)
    }

    //========================= User Detail ================ //
    @TypeConverter
    fun requestDetailToJson(requestDetail: RequestDetail): String {
        return gson.toJson(requestDetail)
    }

    @TypeConverter
    fun jsonToRequestDetail(jListString: String): RequestDetail {
        return gson.fromJson(jListString, RequestDetail::class.java)
    }

    //========================= Vehicle Detail ================ //
    @TypeConverter
    fun vehicleDetailToJson(vehicleDetail: VehicleDetail): String {
        return gson.toJson(vehicleDetail)
    }

    @TypeConverter
    fun jsonToVehicleDetail(jListString: String): VehicleDetail {
        return gson.fromJson(jListString, VehicleDetail::class.java)
    }
}