package com.stmaktavish.linegraph

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        llGraph.setGraphs(arrayListOf(
            Graph("4PM", 20),
            Graph("5PM", 40),
            Graph("6PM", 80),
            Graph("7PM", 140),
            Graph("8PM", 50),
            Graph("9PM", 300),
            Graph("10PM", 220),
            Graph("11PM", 180),
            Graph("12PM", 90),
            Graph("1AM", 75),
            Graph("2AM", 30),
            Graph("3AM", 10)
        ))
    }

}