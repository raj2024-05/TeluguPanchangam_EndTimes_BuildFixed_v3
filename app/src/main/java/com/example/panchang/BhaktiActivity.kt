package com.example.panchang

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class BhaktiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bhakti)
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        findViewById<android.widget.Button>(R.id.btnAddSloka).setOnClickListener {
            showAddDialog()
        }
        renderItems()
    }

    private fun renderItems() {
        val container = findViewById<LinearLayout>(R.id.container)
        container.removeAllViews()

        BhaktiData.items.forEach { item ->
            addItemRow(container, item.title, item.text, null)
        }

        val userItems = UserSlokaStore.load(this)
        if (userItems.isNotEmpty()) {
            val heading = TextView(this).apply {
                text = "నా శ్లోకాలు"
                textSize = 18f
                setTextColor(resources.getColor(R.color.text_red, theme))
                setPadding(4, 18, 4, 10)
            }
            container.addView(heading)
            userItems.forEachIndexed { index, item ->
                addItemRow(container, item.title, item.text, index)
            }
        }
    }

    private fun addItemRow(container: LinearLayout, title: String, text: String, userIndex: Int?) {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(20, 18, 12, 18)
            setBackgroundColor(resources.getColor(R.color.gold_card, theme))
        }
        val tv = TextView(this).apply {
            this.text = "🪔  $title"
            textSize = 16f
            setTextColor(resources.getColor(R.color.text_red, theme))
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            setOnClickListener { showText(title, text) }
        }
        row.addView(tv)

        if (userIndex != null) {
            val del = android.widget.Button(this).apply {
                this.text = "తొలగించు"
                setOnClickListener {
                    UserSlokaStore.delete(this@BhaktiActivity, userIndex)
                    renderItems()
                }
            }
            row.addView(del)
        }

        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lp.bottomMargin = 8
        row.layoutParams = lp
        container.addView(row)
    }

    private fun showText(title: String, text: String) {
        val scroll = ScrollView(this)
        val tv = TextView(this).apply {
            this.text = text
            textSize = 17f
            setTextColor(resources.getColor(R.color.black, theme))
            setPadding(24, 20, 24, 30)
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
        }
        scroll.addView(tv)
        AlertDialog.Builder(this)
            .setTitle(title)
            .setView(scroll)
            .setPositiveButton("మూసివేయి", null)
            .show()
    }

    private fun showAddDialog() {
        val box = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 8, 24, 0)
        }
        val title = EditText(this).apply {
            hint = "శ్లోకం / స్తోత్రం పేరు"
        }
        val text = EditText(this).apply {
            hint = "శ్లోక పాఠ్యాన్ని ఇక్కడ నమోదు చేయండి"
            minLines = 8
            gravity = android.view.Gravity.TOP
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
        }
        box.addView(title)
        box.addView(text)

        AlertDialog.Builder(this)
            .setTitle("కొత్త శ్లోకం జోడించండి")
            .setView(box)
            .setNegativeButton("రద్దు", null)
            .setPositiveButton("సేవ్") { _, _ ->
                val t = title.text.toString().trim()
                val body = text.text.toString().trim()
                if (t.isNotEmpty() && body.isNotEmpty()) {
                    UserSlokaStore.add(this, t, body)
                    renderItems()
                }
            }
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
