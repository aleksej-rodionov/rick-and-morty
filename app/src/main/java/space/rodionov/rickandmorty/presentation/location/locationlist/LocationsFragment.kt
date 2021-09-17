package space.rodionov.rickandmorty.presentation.location.locationlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import space.rodionov.rickandmorty.R
import space.rodionov.rickandmorty.databinding.FragmentLocationsBinding

class LocationsFragment: Fragment(R.layout.fragment_locations) {

    private val viewModel: LocationsViewModel by viewModels()
    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLocationsBinding.bind(view)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}