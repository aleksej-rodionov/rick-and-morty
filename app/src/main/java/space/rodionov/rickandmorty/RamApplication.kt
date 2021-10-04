package space.rodionov.rickandmorty

import android.app.Application
import space.rodionov.rickandmorty.di.AppComponent
import space.rodionov.rickandmorty.di.DaggerAppComponent

//@HiltAndroidApp
//class RamApplication : Application()

open class RamApplication : Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }
}