package space.rodionov.rickandmorty.di

import android.content.Context
import dagger.Module
import dagger.Provides
import space.rodionov.rickandmorty.data.storage.SharedPrefStorage
import space.rodionov.rickandmorty.data.storage.SharedPrefStorageImpl

@Module
class SharedPrefStorageModule {

    @Provides
    fun provideSharedPrefStorage(context: Context): SharedPrefStorage {
        return SharedPrefStorageImpl(context)
    }
}