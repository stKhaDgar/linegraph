package com.stmaktavish.linegraph

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat

/**
 * Created by Andrey Berezhnoi on 15.01.2020.
 * Copyright (c) 2020 mova.io. All rights reserved.
 */


class LineGraph @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(context, attrs, defStyle) {

    private val llGraphs: LinearLayout

    private var currentMaxValue = 0

    private val startColor = ContextCompat.getColor(context, R.color.colorStart)
    private val endColor = ContextCompat.getColor(context, R.color.colorEnd)

    init {
        val layout = View.inflate(context, R.layout.graph_layout, null)
        addView(layout)

        llGraphs = layout.findViewById(R.id.llGraphs)
    }

    fun setGraphs(graphs: ArrayList<Graph>) {
        graphs.maxBy { it.value }?.value?.let { value -> currentMaxValue = value }

        for ((index, graph) in graphs.withIndex()) {
            addGraph(index, graph)
        }
    }

    private fun addGraph(index: Int, graph: Graph) {
        val graphLayout = LayoutInflater.from(context).inflate(R.layout.graph_line_item, null)
        graphLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)
        llGraphs.addView(graphLayout)

        (graphLayout.findViewById(R.id.tvTitle) as? TextView)?.text = graph.title

        (graphLayout.findViewById(R.id.llProgress) as? LinearLayout)?.let { llProgress ->
            llProgress.post {
                (graphLayout.findViewById(R.id.vProgress) as? View)?.let { vProgress ->

                    var startColor = startColor
                    val endColor = endColor

                    val dif = endColor - startColor

                    val percent = String.format("%.2f", graph.value.toFloat() / currentMaxValue).replace(",", ".").toFloat()

                    startColor += (dif * (1 - percent)).toInt()

                    val gradientBackground = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                        intArrayOf(startColor, endColor))

                    vProgress.background = gradientBackground

                    updateHeightOfGraphByAnimation(vProgress, (llProgress.height * (graph.value / currentMaxValue.toFloat())).toInt(), index * 60L)
                }
            }
        }
    }

    private fun updateHeightOfGraphByAnimation(graph: View, endHeight: Int, delay: Long) {
        val anim = ValueAnimator.ofInt(graph.height, endHeight)

        anim.startDelay = delay
        anim.duration = delay * 4

        anim.addUpdateListener { animation ->
            val value = animation.animatedValue as Int

            val params = graph.layoutParams
            params.height = value
            graph.layoutParams = params
        }

        anim.start()
    }

}