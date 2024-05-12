package com.example.mapas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var botonMap:Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        botonMap=findViewById(R.id.btnMap)

        botonMap.setOnClickListener{
           var intent= Intent(this, MapActivity::class.java)
           startActivity(intent)
        }
    }
}