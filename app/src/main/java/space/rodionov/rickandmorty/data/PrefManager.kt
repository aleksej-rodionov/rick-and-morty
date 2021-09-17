package space.rodionov.rickandmorty.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "PrefManager LOGS"
private val Context.dataStore by preferencesDataStore("user_prefs")

@Singleton
class PrefManager @Inject constructor(@ApplicationContext context: Context) {

    private val prefsDataStore = context.dataStore

    private object PrefKeys {
        val CHAR_NEXT = intPreferencesKey("char_next")
        val LOC_NEXT = intPreferencesKey("loc_next")
        val EPI_NEXT = intPreferencesKey("epi_next")
    }

    //============================GETTERS=================================

    val charNextFlow = prefsDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {
            val charNext = it[PrefKeys.CHAR_NEXT] ?: 1
            charNext
        }

    val locNextFlow = prefsDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {
            val locNext = it[PrefKeys.LOC_NEXT] ?: ""
            locNext
        }

    val epiNextFlow = prefsDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {
            val epiNext = it[PrefKeys.EPI_NEXT] ?: ""
            epiNext
        }

    //==============================SETTERS==================================

    suspend fun updateTypeName(charNext: Int) {
        prefsDataStore.edit {
            it[PrefKeys.CHAR_NEXT] = charNext
        }
    }

    suspend fun updateFirstDate(locNext: Int) {
        prefsDataStore.edit {
            it[PrefKeys.LOC_NEXT] = locNext
        }
    }

    suspend fun updateLastDate(epiNext: Int) {
        prefsDataStore.edit {
            it[PrefKeys.EPI_NEXT] = epiNext
        }
    }
}










