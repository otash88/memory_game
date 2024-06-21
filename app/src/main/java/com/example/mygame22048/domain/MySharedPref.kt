package com.example.mygame22048.domain

import android.content.Context
import android.content.SharedPreferences

class MySharedPref {
    companion object {
        private var instance: MySharedPref? = null
        private lateinit var sharedPref: SharedPreferences


        fun init(context: Context) {
            if (instance == null) {
                instance = MySharedPref()
                sharedPref = context.getSharedPreferences("SHAREDPREF", Context.MODE_PRIVATE)
            }
        }

        fun getInstance() = instance!!
    }

    fun saveLastGame(string: String) {
        sharedPref.edit().putString("SAVE", string).apply()
    }

    fun getSavedGame(): List<Int> {
        val str = sharedPref.getString("SAVE", "")
        val arr = str!!.split("#")

//            Log.d("TTT", "array size -> ${arr.size}")
//            Log.d("TTT", "array -> $arr")
        if (arr.size != 16) return emptyList()

        val ls = ArrayList<Int>(16)
        for (i in arr.indices) {
            ls.add(arr[i].toInt())
        }

        return ls
    }

    fun getMax(): Long {
        return sharedPref.getLong("MAX", 0)
    }

    fun saveMax(max: Long) {
        sharedPref.edit().putLong("MAX", max).apply()
    }
    fun saveButtonVis(boolean: Boolean){
        sharedPref.edit().putBoolean("HAS",boolean).apply()
    }
    fun getButtonVis():Boolean {
        return sharedPref.getBoolean("HAS", true)
    }

    fun saveMax2(max: Long){
        sharedPref.edit().putLong("MAX2",max).apply()
    }
    fun getMax2():Long{
        return sharedPref.getLong("MAX2",0)
    }

}