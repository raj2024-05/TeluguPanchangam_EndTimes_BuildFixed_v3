package com.example.panchang

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

data class UserSloka(val title: String, val text: String)

object UserSlokaStore {
    private const val PREFS = "user_slokas"
    private const val KEY = "items"

    fun load(context: Context): MutableList<UserSloka> {
        val result = mutableListOf<UserSloka>()
        val raw = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(KEY, null) ?: return result
        runCatching {
            val arr = JSONArray(raw)
            for (i in 0 until arr.length()) {
                val o = arr.getJSONObject(i)
                result += UserSloka(o.optString("title"), o.optString("text"))
            }
        }
        return result
    }

    fun save(context: Context, items: List<UserSloka>) {
        val arr = JSONArray()
        items.forEach {
            arr.put(JSONObject().apply {
                put("title", it.title)
                put("text", it.text)
            })
        }
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(KEY, arr.toString()).apply()
    }

    fun add(context: Context, title: String, text: String) {
        val items = load(context)
        items += UserSloka(title, text)
        save(context, items)
    }

    fun delete(context: Context, index: Int) {
        val items = load(context)
        if (index in items.indices) {
            items.removeAt(index)
            save(context, items)
        }
    }
}
