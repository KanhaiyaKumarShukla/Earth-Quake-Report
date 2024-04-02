package com.example.reportquake

//This class is just to collect data from given JSON response into one entity
class earthQuake (
    private val mMagnitude: Double,
    private val mLocation: String,
    private val mTimeInMillisecond: Long,
    private val mUrl: String
    ){

    fun getMagnitude():Double{
        return mMagnitude
    }
    fun getLocation():String{
        return mLocation
    }

    fun getTimeInMilliseconds():Long{
        return mTimeInMillisecond
    }

    fun getUrl():String{
        return mUrl;
    }
}