package com.nlabs.trakingapp

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nlabs.trakingapp.databinding.ActivityMainBinding
import com.nlabs.trakingapp.location_data.BaseLocation
import com.nlabs.trakingapp.location_data.BaseLocationViewModel

class MainActivity : AppCompatActivity() {

    // Variables
    private var clicked = false // Buttons status to set animations
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mBaseLocationViewModel: BaseLocationViewModel

    // Declaring Bindings
    private lateinit var mainBinding: ActivityMainBinding

    // Declaring button Animations
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_animation) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_animation) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_animation) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_animation) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize binding instances
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        // Initialize location instances
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // Initializing ViewModel to communicate data to list fragment
        mBaseLocationViewModel = ViewModelProvider(this)[BaseLocationViewModel::class.java]

        // Initializing button listeners
        mainBinding.addFloatingButton.setOnClickListener{
            addBtnAnimation()
        }

        mainBinding.addRouteFloatingButton.setOnClickListener {
            addBtnAnimation()
        }

        mainBinding.addLocationFloatingButton.setOnClickListener {
            addBtnAnimation()
            fetchLocation()
        }

    }

    private fun addBtnAnimation(){
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean){
        if(!clicked){
            mainBinding.addRouteFloatingButton.startAnimation(fromBottom)
            mainBinding.addLocationFloatingButton.startAnimation(fromBottom)
            mainBinding.addFloatingButton.startAnimation(rotateOpen)
        } else {
            mainBinding.addRouteFloatingButton.startAnimation(toBottom)
            mainBinding.addLocationFloatingButton.startAnimation(toBottom)
            mainBinding.addFloatingButton.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean){
        if (!clicked){
            mainBinding.addRouteFloatingButton.visibility = View.VISIBLE
            mainBinding.addLocationFloatingButton.visibility = View.VISIBLE
        } else {
            mainBinding.addRouteFloatingButton.visibility = View.INVISIBLE
            mainBinding.addLocationFloatingButton.visibility = View.INVISIBLE
        }
    }

    /**
    * This method gets the location and makes an action when and if it is
     * obtained with success.
    * */
    private fun fetchLocation() {
        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }

        task.addOnSuccessListener {
            if (it != null) {
                //Toast.makeText(this, "${it.latitude}, ${it.longitude}", Toast.LENGTH_SHORT).show()
                mBaseLocationViewModel.upsertLocation(BaseLocation(it.latitude, it.longitude, it.altitude))
                Toast.makeText(this, "location added...", Toast.LENGTH_SHORT).show()
            }
        }
    }
}