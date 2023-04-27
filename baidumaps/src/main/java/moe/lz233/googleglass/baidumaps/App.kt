package moe.lz233.googleglass.baidumaps

import androidx.multidex.MultiDexApplication
import com.baidu.location.LocationClient
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        SDKInitializer.setAgreePrivacy(this, true)
        LocationClient.setAgreePrivacy(true)
        SDKInitializer.initialize(this)
        SDKInitializer.setHttpsEnable(false)
        SDKInitializer.setCoordType(CoordType.BD09LL)
    }
}