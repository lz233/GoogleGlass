package moe.lz233.googleglass.baidumaps.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import com.baidu.mapapi.map.*
import moe.lz233.googleglass.ui.BaseActivity
import moe.lz233.googleglass.util.LogUtil
import java.util.*


class HomeActivity : BaseActivity() {

    /*private val cardAdapter by lazy {
        CardAdapter(
            mutableListOf(
                CardBuilder(this, CardBuilder.Layout.EMBED_INSIDE)
                    .setEmbeddedLayout()
            ),
            mutableListOf(
                null
            )
        )
    }*/

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mapView = MapView(this, BaiduMapOptions().apply {

        })
        setContentView(mapView)
        val map = mapView.map
        //map.isMyLocationEnabled = true
        /*val locationClient = LocationClient(this).apply {
            locOption = LocationClientOption().apply {
                openGps = true
                setCoorType("bd09ll")
                setScanSpan(1000)
                //setIsNeedLocationDescribe(true)
                //setIsNeedAddress(true)
                //setNeedNewVersionRgc(true)
            }
            registerLocationListener { location ->
                map.setMyLocationData(
                    MyLocationData.Builder()
                        .accuracy(location.radius)
                        .direction(location.direction)
                        .latitude(location.latitude)
                        .longitude(location.longitude)
                        .build()
                )
                map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(
                    MapStatus.Builder()
                        .target(LatLng(location.latitude,location.longitude))
                        .zoom(18.0f)
                        .build()
                ))
                //LogUtil.d(location.gnssProvider)
            }
            start()
        }*/
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers = locationManager.getProviders(Criteria().apply {
            accuracy = Criteria.ACCURACY_FINE
            isAltitudeRequired = false
        }, true)
        providers.forEach {
            LogUtil.d(it)
            locationManager.requestLocationUpdates(it, 1000, 10f, object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    LogUtil.d("$it ${location.latitude} ${location.longitude} ${location.altitude}")
                }
                override fun onProviderEnabled(provider: String) {}

                override fun onProviderDisabled(provider: String) {}

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            })
        }
        LogUtil.d(Build.TYPE)
        //cardScrollerView.setActionBoundAdapter(cardAdapter)

    }
}