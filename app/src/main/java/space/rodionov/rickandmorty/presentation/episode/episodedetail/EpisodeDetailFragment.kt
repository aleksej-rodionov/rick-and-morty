package space.rodionov.rickandmorty.presentation.episode.episodedetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import space.rodionov.rickandmorty.R
import space.rodionov.rickandmorty.databinding.FragmentEpisodeDetailBinding

@AndroidEntryPoint
class EpisodeDetailFragment: Fragment(R.layout.fragment_episode_detail) {

    private var _binding: FragmentEpisodeDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EpisodeDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEpisodeDetailBinding.bind(view)

        binding.apply {
            viewModel.episode.observe(viewLifecycleOwner) {
                tvName.text = it.name
                tvAirDate.text = getString(R.string.episode_air_date, it.airDate)
                tvEpisode.text = getString(R.string.episode_episode, it.episode)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





