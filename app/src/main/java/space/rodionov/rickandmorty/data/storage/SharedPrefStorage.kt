package space.rodionov.rickandmorty.data.storage

interface SharedPrefStorage {

    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String) : Boolean
}