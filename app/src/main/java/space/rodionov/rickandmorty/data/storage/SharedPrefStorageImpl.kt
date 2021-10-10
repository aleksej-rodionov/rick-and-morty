package space.rodionov.rickandmorty.data.storage

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefStorageImpl @Inject constructor(
    context: Context
) : SharedPrefStorage {

    private val sharedPref = context.getSharedPreferences("isNightMode", Context.MODE_PRIVATE)

    override fun setBoolean(key: String, value: Boolean) {
        with(sharedPref.edit()) {
            putBoolean(key, value)
        }
    }

    override fun getBoolean(key: String): Boolean {
        return sharedPref.getBoolean(key, false)
    }
}