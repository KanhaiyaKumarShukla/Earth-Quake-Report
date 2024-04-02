package com.example.reportquake

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class EarthQuakeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        * this app will show the list of earthQuake as from usgs.gov website:
        * magnitude    location    date and time
        * it will use JSON. JSON is a text-based data interchange format
          to maintain the structure of the data.
          It supports data structures like arrays and objects and
          JSON documents that are rapidly executed on the server.
          It is also a Language-Independent format that is derived from JavaScript.*/

        val earthQuakes :ArrayList<earthQuake> = QueryUtils().extractEarthQuakes()
        val earthQuakeListView=findViewById<ListView>(R.id.list)
        val adapter=earthQuakeAdapter(this, earthQuakes)
        earthQuakeListView.adapter=adapter
        earthQuakeListView.setOnItemClickListener { parent, view, position, id ->
            val currentEarthQuake: earthQuake? =adapter.getItem(position)
            //convert the string URL into a URI object (to pass into Intent constructor)
            val earthQuakeUri= Uri.parse(currentEarthQuake?.getUrl())
            // create a new intent to view the earthquake URI
            val websiteIntent=Intent(Intent.ACTION_VIEW, earthQuakeUri)
            startActivity(websiteIntent)
        }
    }
}