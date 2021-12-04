package com.example.mapssamplewithpermission.Map_2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.mapssamplewithpermission.R
import com.example.mapssamplewithpermission.SampleClasses
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class Maps_Activity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private lateinit var FLC : FusedLocationProviderClient

    private lateinit var lastLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        FLC = LocationServices.getFusedLocationProviderClient(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0 : GoogleMap) {
        map = p0

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(40.73, -73.99)
        map.addMarker(MarkerOptions().position(sydney).title("My Favorite City"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,12.0f))
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        setUpMap()

    }

    override fun onMarkerClick(p0: Marker) = false

    @SuppressLint("MissingPermission")
    fun setUpMap() {

        map.mapType = GoogleMap.MAP_TYPE_TERRAIN
        /*map.mapType = GoogleMap.MAP_TYPE_SATELLITE
        map.mapType = GoogleMap.MAP_TYPE_HYBRID
        map.mapType = GoogleMap.MAP_TYPE_NONE
        map.mapType = GoogleMap.MAP_TYPE_NORMAL*/

        // enabling location in device
        SampleClasses().enableMyLocation(this,map)

        // on map ready location is updated to currentLocation
        map.isMyLocationEnabled = true
        FLC.lastLocation.addOnSuccessListener {
            if (it != null) {
                lastLocation = it
                val currentLatLng = LatLng(it.latitude, it.longitude)
                SampleClasses().placeMarkerOnMap(this, currentLatLng,map)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                //SampleClasses().setPOIClick(map)
            }
        }

    }
}