package space.rodionov.rickandmorty.presentation.character.characterdetail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import space.rodionov.rickandmorty.R
import space.rodionov.rickandmorty.databinding.FragmentCharacterDetailBinding
import space.rodionov.rickandmorty.presentation.MainActivity
import javax.inject.Inject

class CharacterDetailFragment : Fragment(R.layout.fragment_character_detail) {

    val args by navArgs<CharacterDetailFragmentArgs>()

//    private val viewModel: CharacterDetailViewModel by viewModels()
    @Inject
    lateinit var assistedFactory: CharacterDetailViewModelAssistedFactory
    private val viewModel : CharacterDetailViewModel by viewModels { assistedFactory.create(this, args.toBundle()) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val args by navArgs<CharacterDetailFragmentArgs>()
//        viewModel.charId = args.charId

        _binding = FragmentCharacterDetailBinding.bind(view)

        binding.apply {
            viewModel.character.observe(viewLifecycleOwner) {
                Glide.with(this@CharacterDetailFragment)
                    .load(it.image)
                    .error(R.drawable.image_placeholder)
                    .into(image)

                tvName.text = it.name
                tvStatus.text = it.status
                tvSpecies.text = getString(R.string.species, it.species)
                tvGender.text = getString(R.string.gender, it.gender)
                tvLocation.text = getString(R.string.location, it.location.name)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





