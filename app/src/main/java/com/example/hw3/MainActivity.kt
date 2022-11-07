package com.example.hw3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

//val SLICES_PER_PIZZA = 8 //getInteger(R.integer.slices_per_pizza)
val TAG = "MainActivity" //getString(R.string.tag)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}