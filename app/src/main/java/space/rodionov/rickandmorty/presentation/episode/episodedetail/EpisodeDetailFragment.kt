package space.rodionov.rickandmorty.presentation.episode.episodedetail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import space.rodionov.rickandmorty.R
import space.rodionov.rickandmorty.databinding.FragmentEpisodeDetailBinding
import space.rodionov.rickandmorty.presentation.MainActivity
import javax.inject.Inject

class EpisodeDetailFragment: Fragment(R.layout.fragment_episode_detail) {

    val args by navArgs<EpisodeDetailFragmentArgs>()

//    private val viewModel: EpisodeDetailViewModel by viewModels()
    @Inject
    lateinit var assistedFactory: EpisodeDetailViewModelAssistedFactory
    private val viewModel: EpisodeDetailViewModel by viewModels { assistedFactory.create(this, args.toBundle()) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }

    private var _binding: FragmentEpisodeDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEpisodeDetailBinding.bind(view)

        binding.apply {
            viewModel.episode.observe(viewLifecycleOwner) {
                tvName.text = it.name
                tvAirDate.text = getString(R.string.episode_air_date, it.airDate)
                tvEpisode.text = getString(R.string.episode_episode, it.episode )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





