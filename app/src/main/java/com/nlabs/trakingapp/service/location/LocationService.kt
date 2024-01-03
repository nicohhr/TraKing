package com.nlabs.trakingapp.service.location

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.gms.location.LocationServices
import com.nlabs.trakingapp.MainActivity
import com.nlabs.trakingapp.R
import com.nlabs.trakingapp.data.location.InstantLocation
import com.nlabs.trakingapp.data.location.InstantLocationDao
import com.nlabs.trakingapp.data.location.InstantLocationDatabase
import com.nlabs.trakingapp.data.location.InstantLocationRepository
import com.nlabs.trakingapp.data.location.InstantLocationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LocationService(): Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient
    private lateinit var instantLocationRepository: InstantLocationRepository

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        // Init location client
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
        // Init ViewModel
        val locationDao = InstantLocationDatabase.getDatabase(application).dao()
        instantLocationRepository = InstantLocationRepository(locationDao)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, "location")
            .setContentTitle("Tracking location...")
            .setContentText("Location: null")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setOngoing(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        locationClient
            .getLocationUpdates(1500L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                // Showing Data
                val lat = location.latitude.toString()
                val long = location.longitude.toString()
                val alt = String.format("%.2f", location.altitude)
                val updatedNotification = notification.setContentText(
                    "Location: ($lat, $long, $alt)"
                )
                // Create Location Instances
                instantLocationRepository.addLocation(InstantLocation(
                    location.latitude,
                    location.longitude,
                    location.altitude,
                    isFromRoute = true
                ))
                notificationManager.notify(1, updatedNotification.build())
            }
            .launchIn(serviceScope)

        startForeground(1, notification.build())
    }

    private fun stop() {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}