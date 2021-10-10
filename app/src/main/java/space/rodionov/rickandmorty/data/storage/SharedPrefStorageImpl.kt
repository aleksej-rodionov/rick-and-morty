package space.rodionov.rickandmorty.data.storage

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject

private const val TAG = "StoreImpl LOGS"

class SharedPrefStorageImpl @Inject constructor(
    context: Context
) : SharedPrefStorage {

    private val sharedPref = context.getSharedPreferences("RamStore", Context.MODE_PRIVATE)

    override fun setBoolean(key: String, value: Boolean) {
        with(sharedPref.edit()) {
            putBoolean(key, value)
            apply()
        }
    }

    override fun getBoolean(key: String): Boolean {
        return sharedPref.getBoolean(key, true)!!
    }
}