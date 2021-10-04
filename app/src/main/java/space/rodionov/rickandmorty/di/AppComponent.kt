package space.rodionov.rickandmorty.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import space.rodionov.rickandmorty.presentation.MainActivity
import space.rodionov.rickandmorty.presentation.character.characterdetail.CharacterDetailFragment
import space.rodionov.rickandmorty.presentation.character.characterlist.CharactersFragment
import space.rodionov.rickandmorty.presentation.episode.episodedetail.EpisodeDetailFragment
import space.rodionov.rickandmorty.presentation.episode.episodelist.EpisodesFragment
import space.rodionov.rickandmorty.presentation.location.locationdetail.LocationDetailFragment
import space.rodionov.rickandmorty.presentation.location.locationlist.LocationsFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context) : AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: CharactersFragment)
    fun inject(fragment: CharacterDetailFragment)
    fun inject(fragment: EpisodesFragment)
    fun inject(fragment: EpisodeDetailFragment)
    fun inject(fragment: LocationsFragment)
    fun inject(fragment: LocationDetailFragment)
}