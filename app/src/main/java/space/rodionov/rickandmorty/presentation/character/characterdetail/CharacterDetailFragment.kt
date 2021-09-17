package space.rodionov.rickandmorty.presentation.character.characterdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import space.rodionov.rickandmorty.R
import space.rodionov.rickandmorty.databinding.FragmentCharacterDetailBinding

@AndroidEntryPoint
class CharacterDetailFragment : Fragment(R.layout.fragment_character_detail) {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharacterDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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





