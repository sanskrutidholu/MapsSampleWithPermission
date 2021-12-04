package com.example.mapssamplewithpermission

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mapssamplewithpermission.Map_2.GetAddress
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class SampleClasses {

    // this method helps to set marker on wherever we long click on map
    fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener {

            // this is use to show value of lat lng onTouch of map
            val sinippet = String.format(
                Locale.getDefault(),
                "Lat: %1\$.5f, Long: %2\$.5f",
                it.latitude,it.longitude
            )

            map.addMarker(
                MarkerOptions()
                    .position(it)
                    .title("DROPPED PIN")
                    .snippet(sinippet).icon(
                        BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_BLUE
                        ))

            )
        }
    }



    // use to get name of position instead of lat lang for public places like park,school,market etc.
    fun setPOIClick(map: GoogleMap) {
        map.setOnPoiClickListener {
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(it.latLng)
                    .title(it.name).icon(
                        BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_BLUE
                        ))
            )
            poiMarker!!.showInfoWindow()
        }
    }



    // for getting permission for location
    fun isPermissionGranted(context: Context) : Boolean {
        val checking = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

        return checking
    }

    // to enable location for tracking
    @SuppressLint("MissingPermission")
    fun enableMyLocation(context: Context, map: GoogleMap) {
        if (isPermissionGranted(context)) {
            map.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
    }

    // for placing marker
    fun placeMarkerOnMap(context: Context, location: LatLng, map: GoogleMap) {
        val markerOptions = MarkerOptions().position(location)
        val titleStr = GetAddress().getAddress(context,location)
        markerOptions.title(titleStr)
        map.addMarker(markerOptions)
    }
}