package space.rodionov.rickandmorty.presentation.location.locationdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import space.rodionov.rickandmorty.R
import space.rodionov.rickandmorty.databinding.FragmentLocationDetailBinding

@AndroidEntryPoint
class LocationDetailFragment: Fragment(R.layout.fragment_location_detail) {

    private var _binding : FragmentLocationDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel : LocationDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLocationDetailBinding.bind(view)

        binding.apply {
            viewModel.location.observe(viewLifecycleOwner) {
                tvName.text = it.name
                tvType.text = getString(R.string.location_type, it.type)
                tvDimension.text = getString(R.string.location_dimension, it.dimension)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}