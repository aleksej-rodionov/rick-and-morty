package space.rodionov.rickandmorty.presentation.episode.episodelist

import android.os.Bundle
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import space.rodionov.rickandmorty.R
import space.rodionov.rickandmorty.databinding.FragmentEpisodesBinding
import space.rodionov.rickandmorty.domain.model.Episode

@AndroidEntryPoint
class EpisodesFragment : Fragment(R.layout.fragment_episodes) {

    private var _binding: FragmentEpisodesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EpisodesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEpisodesBinding.bind(view)

        val episodeAdapter = EpisodeAdapter(
            onEpisodeClick = {
                onEpisodeClick(it)
            }
        )

        binding.apply {
            recyclerView.apply {
                adapter = episodeAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.VERTICAL
                    )
                )
            }

            viewModel.episodes.observe(viewLifecycleOwner) {
                episodeAdapter.submitList(it)
                episodeAdapter.notifyDataSetChanged()
                progressBar.visibility = View.INVISIBLE
            }

            nestedScrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
                override fun onScrollChange(
                    v: NestedScrollView,
                    scrollX: Int,
                    scrollY: Int,
                    oldScrollX: Int,
                    oldScrollY: Int
                ) {
                    if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                        if (viewModel.nextPage < 3) {
                            progressBar.visibility = View.VISIBLE
                            viewModel.nextPage++
                            viewModel.getNewPage()
                        }
                    }
                }
            })

            swipeRefreshLayout.setOnRefreshListener {
                viewModel.onRefresh()
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun onEpisodeClick(episode: Episode) {
        val action =
            EpisodesFragmentDirections.actionEpisodesFragmentToEpisodeDetailFragment(episode.id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





