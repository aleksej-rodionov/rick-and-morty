package space.rodionov.rickandmorty.presentation.location.locationdetail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import space.rodionov.rickandmorty.R
import space.rodionov.rickandmorty.databinding.FragmentLocationDetailBinding
import space.rodionov.rickandmorty.presentation.MainActivity
import javax.inject.Inject

class LocationDetailFragment : Fragment(R.layout.fragment_location_detail) {

    val args by navArgs<LocationDetailFragmentArgs>()

    //    private val viewModel : LocationDetailViewModel by viewModels()
    @Inject
    lateinit var assistedFactory: LocationDetailViewModelAssistedFactory
    private val viewModel: LocationDetailViewModel by viewModels { assistedFactory.create(this, args.toBundle()) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }

    private var _binding: FragmentLocationDetailBinding? = null
    private val binding get() = _binding!!

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