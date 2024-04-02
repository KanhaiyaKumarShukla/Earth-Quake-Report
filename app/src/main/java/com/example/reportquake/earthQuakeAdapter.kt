package com.example.reportquake

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.ContextCompat
import org.w3c.dom.Text
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date

// customised array - adapter to populate the list view
class earthQuakeAdapter(context: Context, objects: ArrayList<earthQuake>) :
    ArrayAdapter<earthQuake>(context,0, objects) {

    // we had to the location of earthQuake in the format like - " 52 km SE of Shizunai, Japan"
    // and we had to separate this string in such a way that we would want " 52 km SE of" and " Shizunai, Japan"
    // separately. and in almost every string ,before city name like: " Shizunai, Japan" , we had "of" substring
    // so we used "of" as separator
    private val LOCATION_SEPARATOR=" of "
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView=convertView
        if(listItemView==null){
            listItemView=LayoutInflater.from(context).inflate(R.layout.earthquake_list_item, parent, false)
        }
        // we are getting current earthQuake instance that we have to set in listView
        val currentEarthquake=getItem(position)

        val magnitudeView=listItemView!!.findViewById<TextView>(R.id.magnitude_tv)
        // formatMagnitude function is bellow
        val formatedMagnitude=formatMagnitude(currentEarthquake!!.getMagnitude())
        magnitudeView.text = formatedMagnitude

        //set proper background color on magnitude circle.
        //fetch background from textView, which is a GradientDrawable
        val magnitudeCircle=magnitudeView.background as GradientDrawable
        // getMagnitudeColor function is bellow
        val magnitudeColor=getMagnitudeColor(currentEarthquake!!.getMagnitude())
        // we are setting the the proper color for different magnitude we have set in
        // getMagnitudeColor function
        magnitudeCircle.setColor(magnitudeColor)

        val originalLocation=currentEarthquake.getLocation()

        // here we are initialing it with default value, because it may be that location information
        // could not have any "of" as separator
        var primaryLocation="near the"
        var locationOffset:String=originalLocation
        //checking if it contain "of" as separator
        if(originalLocation.contains(LOCATION_SEPARATOR)){
            // split function split the string based on separator and return array of string.
            val splitedStringArray=originalLocation.split(LOCATION_SEPARATOR)
            // primaryLocation= "53km SE" + "of"
            primaryLocation=splitedStringArray[0]+LOCATION_SEPARATOR
            // locationOffset= "shizunai, japan"
            locationOffset=splitedStringArray[1]
        }
        // set the string in appropriate textview
        val primaryLocView=listItemView.findViewById<TextView>(R.id.location_primary_tv)
        primaryLocView.text=primaryLocation

        val offSetLocView=listItemView.findViewById<TextView>(R.id.location_off_set_tv)
        offSetLocView.text=locationOffset

        val timeInMilliseconds=currentEarthquake.getTimeInMilliseconds()
        // as we have time in millisecond and we need the proper date and time . so we have made Date Object
        val dateObject=Date(timeInMilliseconds)

        val dateView=listItemView.findViewById<TextView>(R.id.date_tv)
        // to format the date object in proper date format we have define formatDate() function below
        val formattedDate=formatDate(dateObject)
        dateView.text=formattedDate   //set date

        val timeView=listItemView.findViewById<TextView>(R.id.time_tv)
        // similarly, to format the date object in proper time format we have define formatTime() function below
        val formattedTime=formatTime(dateObject)
        timeView.text=formattedTime

        return listItemView
    }

    private fun formatDate(dateObject: Date): String {
        val dateFormat=SimpleDateFormat("LLL dd, yyyy")
        return dateFormat.format(dateObject)
    }
    private fun formatTime(dateObject: Date): String {
        val timeFormat=SimpleDateFormat("h:mm a")
        return timeFormat.format(dateObject)
    }
    private fun formatMagnitude(num: Double):String{
        val magnitudeFormat=DecimalFormat("0.0")
        return magnitudeFormat.format(num)
    }
    private fun getMagnitudeColor(magnitude: Double): Int {
        val magnitudeColorResourceId:Int
        val magnitudeFloorValue= magnitude.toInt()
        magnitudeColorResourceId = when(magnitudeFloorValue){
            0, 1-> R.color.magnitude1
            2 -> R.color.magnitude2
            3 -> R.color.magnitude3
            4 -> R.color.magnitude4
            5 -> R.color.magnitude5
            6 -> R.color.magnitude6
            7 -> R.color.magnitude7
            8 -> R.color.magnitude8
            9 -> R.color.magnitude9
            else -> R.color.magnitude10plus
        }
        //here we have to return color value not the address of the color .
        //till now magnitudeColorResourceId variable have value of R.color.magnitude_
        // which is the address of color not the value of color
        // below line will retrieve the color value at the provided resource id
        return ContextCompat.getColor(context, magnitudeColorResourceId)
    }
}
