package ie.toxodev.thermokingtask.supportClasses.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ie.toxodev.thermokingtask.supportClasses.room.VehicleDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesVehicleDatabase(@ApplicationContext context: Context): VehicleDatabase {
        return VehicleDatabase.invoke(context)
    }

    @Provides
    fun providesGson() = Gson()

}