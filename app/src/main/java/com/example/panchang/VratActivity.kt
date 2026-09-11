package com.example.panchang

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class VratActivity : AppCompatActivity() {

    private val vratList = listOf(
        "ఏకాదశి వ్రతం - ప్రతి పక్షంలో 11వ తిథి",
        "సంకష్టి చతుర్థి - ప్రతి మాసం కృష్ణ పక్ష చవితి",
        "వినాయక చవితి - శుక్ల పక్ష చవితి (భాద్రపదం)",
        "ప్రదోష వ్రతం - ప్రతి పక్షంలో త్రయోదశి",
        "పూర్ణిమ వ్రతం - ప్రతి మాసం పౌర్ణమి",
        "అమావాస్య - ప్రతి మాసం చివరి తిథి",
        "శ్రాద్ధ దినాలు - పితృ పక్షం"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_list)
        val tb = findViewById<Toolbar>(R.id.toolbar)
        tb.title = getString(R.string.feat_vrata)
        setSupportActionBar(tb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val container = findViewById<LinearLayout>(R.id.container)
        for (item in vratList) {
            val tv = TextView(this).apply {
                text = "🛕  $item"
                textSize = 15f
                setTextColor(resources.getColor(R.color.black, theme))
                setBackgroundColor(resources.getColor(R.color.gold_card, theme))
                setPadding(24, 24, 24, 24)
                val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                lp.bottomMargin = 8
                layoutParams = lp
            }
            container.addView(tv)
        }
    }

    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}
